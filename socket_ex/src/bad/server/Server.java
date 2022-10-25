//package bad.server;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.StringTokenizer;
//import java.util.Vector;
//
//import javax.swing.JOptionPane;
//
//public class Server {
//
//	private ServerSocket serverSocket;
//	private Socket socket;
//	private int port;
//
//	private Vector<UserSocket> vectorSocket = new Vector<UserSocket>();
//	// private Vector<Room> vectorRoom = new Vector<Room>();
//
//	private void startNetwork() {
//
//		try {
//			serverSocket = new ServerSocket(port);
//			// textArea.append("서버를 시작 하겠습니다.\n");
//			connect();
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(null, "이미 사용중인 포트입니다.", "알림", JOptionPane.ERROR_MESSAGE);
//			// btnServerStart.setEnabled(true);
//			// btnServerStop.setEnabled(false);
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, "잘못입력하였습니다.", "알림", JOptionPane.ERROR_MESSAGE);
//		}
//	}
//
//	private void connect() {
//		Thread th = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				while (true) {
//					try {
//						// textArea.append("사용자의 접속을 기다립니다.\n");
//						socket = serverSocket.accept();
//
//						UserSocket useInfo = new UserSocket(socket);
//						// 각각의 스레드를 등록시켜준다.
//						useInfo.start();
//					} catch (IOException e) {
//						// textArea.append("서버가 중지됨! 다시 스타트 버튼을 눌러주세요\n");
//						break;
//					}
//				}
//			}
//		});
//		th.start();
//	}
//
//	// 전체 사용자에게 메세지를 보내는 부분
//	public void broadCast(String msg) {
//		for (int i = 0; i < vectorSocket.size(); i++) {
////			vectorSocket.get(i)
//			// 여기서 프로토콜의 개념을 사용
//		}
//	}
//
//	// 내부클래스
//	class UserSocket extends Thread {
//		private BufferedReader bufferedReader;
//		private BufferedWriter bufferedWriter;
//		// 버퍼드로 사용 !!!
//		String nickName;
//		String myCurrentRoomName;
//		private Socket socket;
//
//		private boolean roomCheck = true;
//
//		public UserSocket(Socket soc) {
//			this.socket = soc;
//			network();
//		}
//
//		private void network() {
//			try {
//				// 처음 접속시 유저의 id를 입력받는다.
//				nickName = bufferedReader.readLine();
//				// textArea.append("[[" + nickName + "]] 입장\n");
//
//				// 기존사용자들에게 신규 유저의 접속을 알린다.
//				broadCast("NewUser/" + nickName);
//
//				// 자신에게 기존 사용자들을 알린다.
//				for (int i = 0; i < vc.size(); i++) {
//					UserSocket uinf = vc.elementAt(i);
//					sendmessage("OldUser/" + uinf.nickName);
//
//				}
//				for (int i = 0; i < vectorRoom.size(); i++) {
//					Room room = vectorRoom.elementAt(i);
//					sendmessage("OldRoom/" + room.roomName);
//				}
//
//				// 사용자에게 자신을 알린후 벡터에 자신을 추가한다.
//				vc.add(this);
//
//			} catch (IOException e) {
//				JOptionPane.showMessageDialog(null, "Stream설정에러!", "알림", JOptionPane.ERROR_MESSAGE);
//			}
//		}
//
//		// 브로드캐스트
//		@Override
//		public void run() {
//			while (true) {
//				try {
//					String msg = bufferedReader.readLine();
//					textArea.append("[[" + nickName + "]]" + msg + "\n");
//					inmessage(msg);
//				} catch (IOException e) {
//					try {
//						textArea.append(nickName + " : 사용자접속끊어짐\n");
//						bufferedWriter.close();
//						bufferedReader.close();
//						user_socket.close();
//						vc.remove(this);
//						vectorRoom.remove(this);
//						broadCast("UserOut/" + nickName);
//						broadCast("ErrorOutRoom/" + myCurrentRoomName);
//						broadCast("UserData_Updata/ok");
//						break;
//					} catch (IOException e1) {
//						break;
//					}
//				}
//			}
//		}
//
//	}
//
//} // end of class
