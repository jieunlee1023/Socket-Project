package project.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import lombok.Data;
import project.Room;

@Data
public class UserSocket extends Thread {

	private String nickName;
	private String myCurrentRoomName;

	private boolean isRun = true;
	private boolean roomCheck = true;

	private ServerGUI mContext;
	private Socket socket;

	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;

	public UserSocket(Socket socket, ServerGUI mContext) {
		this.socket = socket;
		this.mContext = mContext;
		System.out.println("유저 입장");
		initData();
	}

	private void initData() {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			network();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void network() {
		try {
			nickName = bufferedReader.readLine();
			mContext.getMainArea().append("※ [ " + nickName + " ] 이 입장하였습니다.\n");
			mContext.getMainArea().append("사용자의 접속을 기다립니다.\n");

			mContext.server.broadCast("NewUser/" + nickName);

			for (int i = 0; i < mContext.server.getUserVector().size(); i++) {
				UserSocket userSocket = mContext.server.getUserVector().elementAt(i);
				sendMessage("OldUser/" + userSocket.nickName);
			}
			for (int i = 0; i < mContext.server.getRoomVector().size(); i++) {
				Room room = mContext.server.getRoomVector().elementAt(i);
				sendMessage("OldRoom/" + room.getRoomName());
			}
			mContext.server.getUserVector().add(this);

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "user 네트워크!", "알림", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void run() {
		while (isRun) {
			try {
				String msg = bufferedReader.readLine();
				mContext.getMainArea().append("[ " + nickName + " ] : " + msg + "\n");
				inmessage(msg);
			} catch (IOException e) {
				try {
					isRun = false;
					mContext.getMainArea().append(" [ " + nickName + " ] : 사용자 접속 끊어짐\n");
					bufferedReader.close();
					bufferedWriter.close();
					socket.close();
					mContext.server.userVector.remove(this);
					mContext.server.roomVector.remove(this);
					mContext.server.broadCast("UserLogOut/" + nickName);
					mContext.server.broadCast("ErrorOutRoom/" + myCurrentRoomName);
					mContext.server.broadCast("UpdateExitUserData/ok");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				break;
			}
		}

	}

	public void inmessage(String msg) {

		// 프로토콜의 개념 사용
		StringTokenizer st = new StringTokenizer(msg, "/");

		String protocol = st.nextToken();
		String message = st.nextToken();

		System.out.println("user 프로토콜 : " + protocol);
		System.out.println("user 메시지 : " + message);

		if (protocol.equals("CreateRoom")) {
			// 같은 방이 존재하는지 확인
			for (int i = 0; i < mContext.server.getRoomVector().size(); i++) {
				Room room = mContext.server.getRoomVector().elementAt(i);
				if (room.getRoomName().equals(message)) {
					sendMessage("CreateRoomFail/ok");
					roomCheck = false;
					break;
				} else {
					roomCheck = true;
				}
			} // end of for
			if (roomCheck == true) {
				Room newRoom = new Room(message, this, mContext);
				mContext.server.getRoomVector().add(newRoom);
				sendMessage("CreateRoom/" + message);
				mContext.server.broadCast("NewRoom/" + message);
				mContext.server.roomBroadCast("NewChatUser/" + message + "@" + nickName);

			}

		} else if (protocol.equals("Chatting")) {
			try {
				String chattingMsg = st.nextToken();
				for (int i = 0; i < mContext.server.getRoomVector().size(); i++) {
					Room room = mContext.server.getRoomVector().elementAt(i);
					if (room.getRoomName().equals(message)) {
						room.roomBroadcast("Chatting/" + nickName + "/" + chattingMsg);
					}
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "※ 보내실 메시지를 입력하세요.", "알림", JOptionPane.ERROR_MESSAGE);

			}

		} else if (protocol.equals("JoinRoom")) {
			for (int i = 0; i < mContext.server.getRoomVector().size(); i++) {
				Room room = mContext.server.getRoomVector().elementAt(i);
				if (room.getRoomName().equals(message)) {
					room.roomBroadcast("Chatting/[알림]/ [ " + nickName + " ] 님이 입장 하였습니다. ");
					sendMessage("JoinRoom/" + message);

					room.checkRoomUser(this);
					room.addUser(this);
					mContext.server.roomBroadCast("NewChatUser/" + message + "@" + nickName);

				}
			}

		} else if (protocol.equals("DeleteRoom")) {
			for (int i = 0; i < mContext.server.getUserVector().size(); i++) {
				UserSocket userSocket = mContext.server.getUserVector().elementAt(i);
				if (userSocket.getNickName().equals(message)) {
					sendMessage("DeleteRoom/ok");
					break;
				}
			}
			mContext.server.broadCast("UserAllOut/" + nickName);

			for (int i = 0; i < mContext.server.getRoomVector().size(); i++) {
				Room room = mContext.server.getRoomVector().elementAt(i);
				if (room.getRoomName().equals(message)) {
					room.removeRoom(this);
					sendMessage("DeleteRoom/ok");
					break;
				}
			}
		} else if (protocol.equals("ExitRoom")) {
			mContext.server.broadCast("UserOut/" + nickName);

			for (int i = 0; i < mContext.server.getRoomVector().size(); i++) {
				System.out.println("Room out i:" + i);
				Room room = mContext.server.getRoomVector().elementAt(i);
				if (room.getRoomName().equals(message)) {
					room.roomBroadcast("Chatting/[알림]/ [ " + nickName + " ] 님이 퇴장 하였습니다. ");
					room.exitRoom(this);
					break;
				}
			}
		} else if (protocol.equals("Wisper")) { // 귓속말
			StringTokenizer wisperTokenizer = new StringTokenizer(message, "@");
			System.out.println("유저소켓메세지:" + message);
			String toUser = wisperTokenizer.nextToken();
			String fromUser = wisperTokenizer.nextToken();
			String wisperMessage = wisperTokenizer.nextToken();

			System.out.println("받는 사람 : " + toUser);
			System.out.println("보낸 사람 : " + fromUser);
			System.out.println("메시지 : " + wisperMessage);

			for (int i = 0; i < mContext.server.getUserVector().size(); i++) {
				UserSocket userSocket = mContext.server.getUserVector().elementAt(i);
				if (userSocket.getNickName().equals(toUser)) {
					userSocket.sendMessage("Wisper/" + fromUser + "@" + wisperMessage);
				}
			}
		}
	}

	public void sendMessage(String msg) {
		try {
			// ★ 중요! : 마지막 구분값이 있어야 한다. 엔터 (줄바꿈)
			bufferedWriter.write(msg + "\n");
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ServerGUI();
	}
}
