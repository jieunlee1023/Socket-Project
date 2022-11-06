package project.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;

import lombok.Data;
import project.Room;

@Data
public class Server {

	private ServerGUI mContext;
	private ServerSocket serverSocket;

	public String hostIP;
	public int port;

	Socket socket;
	Vector<UserSocket> userVector = new Vector<>();
	Vector<Room> roomVector = new Vector<>();

	public Server(int port, ServerGUI mContext) {
		this.mContext = mContext;
		this.port = port;
		initData();

	}

	private void initData() {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println(port + "로 서버를 시작합니다.");

			new Thread(() -> {
				while (true) {
					try {
						socket = serverSocket.accept();
						System.out.println("사용자의 접속을 기다립니다.");

						UserSocket userSocket = new UserSocket(socket, mContext);
						userSocket.start();
					} catch (IOException e) {
						System.out.println("서버가 중지되었습니다.");
						break;
					}
				}

			}).start();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "※ 이미 사용중인 포트입니다.", "알림", JOptionPane.ERROR_MESSAGE);
			mContext.getMainArea().setText("※ 다른 포트를 입력하세요! \n");
			try {
				socket.close();
				serverSocket.close();
				mContext.getServerStart().setEnabled(true);
				mContext.getServerStop().setEnabled(false);
			} catch (IOException e1) {
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "※ 잘못 입력 하였습니다.", "알림", JOptionPane.ERROR_MESSAGE);
			mContext.getMainArea().setText("※ 다른 포트를 입력하세요! \n");
			mContext.getMainArea().setText(" ");
		}
	}

	public void roomBroadCast(String msg) {
		// roomName @ nickName
		System.out.println("------------------" + msg);
		StringTokenizer st = new StringTokenizer(msg, "/");

		String protocol = st.nextToken();
		String message = st.nextToken();

		StringTokenizer stringTokenizer = new StringTokenizer(message, "@");
		String enterRoomName = stringTokenizer.nextToken();
		String enterNickName = stringTokenizer.nextToken();

		for (int i = 0; i < roomVector.size(); i++) {
			String roomName = roomVector.get(i).getRoomName();
			String targetRoom = enterRoomName;
			String targetName = enterNickName;


			if (roomName.equals(targetRoom)) {
				System.out.println("같은방 찾음!!!!!!!!!!!!!!!!!!!!!");
				Room room = roomVector.get(i);
				for (int j = 0; j < room.getUserSocketRoom().size(); j++) {
					UserSocket userSocket = room.getUserSocketRoom().elementAt(j);
					userSocket.sendMessage(msg);
				}
			}
		}
	}

	public void broadCast(String msg) {
		// NewChatUser/ roomName @ nickName
		StringTokenizer st = new StringTokenizer(msg, "/");
		String pt = st.nextToken();

		for (int i = 0; i < userVector.size(); i++) {
			UserSocket userSocket = userVector.elementAt(i);
			// 여기서 프로토콜의 개념을 사용
			userSocket.sendMessage(msg);
		}
	}

}
