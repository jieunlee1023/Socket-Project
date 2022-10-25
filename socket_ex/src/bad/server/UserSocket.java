//package bad.server;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.Socket;
//import java.util.StringTokenizer;
//
//import bad.server.Server.RoomInfomation;
//import bad.server.Server.UserSocket;
//import ch04.server.ServerFile;
//
//public class UserSocket extends Thread {
//
//	private String name;
//	private String roomName;
//
//	private boolean roomCheck = true;
//	private boolean isRun = true;
//
//	// 의존성 컴포지션 관계
//	// ServerUserSoket이 태어날 때 Socket이 생성되어야함
//
//	private ServerFile mContext;
//	private Socket socket;
//
//	private BufferedReader bufferedReader;
//	private BufferedWriter bufferedWriter;
//
//	public UserSocket(String name, Socket socket, ServerFile mContext) {
//		this.name = name;
//		this.socket = socket;
//		this.mContext = mContext;
//		System.out.println(name + " 유저 연결됨 ");
//		initData();
//	}
//
//	private void initData() {
//		try {
//			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
//	private void inmessage(String str) {
//		StringTokenizer st = new StringTokenizer(str, "/");
//
//		String protocol = st.nextToken();
//		String message = st.nextToken();
//
//		System.out.println("protocol : " + protocol);
//		System.out.println("message : " + message);
//
//		if (protocol.equals("Note")) {
//			System.out.println(message);
//			st = new StringTokenizer(message, "@");
//			String user = st.nextToken();
//			String note = st.nextToken();
//			// 백터에서 해당 사용자를 찾아서 쪽지를 전송한다.
//			for (int i = 0; i < vc.size(); i++) {
//				UserSocket u = vc.elementAt(i);
//				// 쪽지는 반드시 찾은 사용자에게 메세지를 보내줘어야 한다.
//				if (u.nickName.equals(user)) {
//					u.sendmessage("Note/" + nickName + "@" + note);
//				}
//			}
//		} else if (protocol.equals("CreateRoom")) {
//			// 1.현재같은방이 존재하는지 확인한다.
//			for (int i = 0; i < vc_room.size(); i++) {
//				Room room = vc_room.elementAt(i);
//				if (room.roomName.equals(message)) { // 만들고자하는방이름을 확인한다
//					sendmessage("CreateRoomFail/ok");
//					roomCheck = false;
//					break;
//				} else {
//					roomCheck = true;
//				}
//			} // end for
//			if (roomCheck == true) {
//				// 1.방을 생성한다.
//				Room new_room = new Room(message, this);
//				// 2. 전체 방 벡터에 생성된 방을 저장한다.
//				vc_room.add(new_room);
//				// 3.사용자들에게 방과 방이름을 생성되었다고 알려준다.
//				sendmessage("CreateRoom/" + message); // 자신에게 방 성공 메세지를 보낸다.
//				broadCast("new_Room/" + message);
//			}
//		} else if (protocol.equals("Chatting")) {
//			String msg = st.nextToken();
//			for (int i = 0; i < vc_room.size(); i++) {
//				Room r = vc_room.elementAt(i);
//				if (r.roomName.equals(message)) {
//					r.roomBroadcast("Chatting/" + nickName + "/" + msg);
//				}
//			}
//		} else if (protocol.equals("JoinRoom")) {
//			for (int i = 0; i < vc_room.size(); i++) {
//				Room r = vc_room.elementAt(i);
//				if (r.roomName.equals(message)) {
//					// 신규접속자를 알린다.
//					r.roomBroadcast("Chatting/[[알림]]/(((" + nickName + " 입장))) ");
//					r.addUser(this); // 해당 룸 객체에 자신을 추가시킨다.
//					sendmessage("JoinRoom/" + message);
//				}
//			}
//		} else if (protocol.equals("OutRoom")) {
//			for (int i = 0; i < vc_room.size(); i++) {
//				Room r = vc_room.elementAt(i);
//				if (r.roomName.equals(message)) {
//					r.removeRoom(this);
//					sendmessage("OutRoom/ok");
//					break;
//				}
//			}
//		}
//	}
//
//	private void sendmessage(String msg) {
//		try {
//			bufferedWriter.write(msg);
//			bufferedWriter.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//}
