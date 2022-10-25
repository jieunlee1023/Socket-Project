package ch04.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServerFile {

	ServerFile mContext = this;

	ServerSocket serverSocket;
	public static final String HOST_IP = "localhost";
	public static final int HOST_PORT = 20000;

	Vector<UserSocketThread> vectors = new Vector<>();
	// 벡터 : 자동으로 동기화 처리를 해주기 때문에 멀티 스레드 환경에서 안전하게 프로그래밍 할 수 있다.

	public ServerFile() {
		initData();
	}

	private void initData() {
		try {
			System.out.println(">>>>> 서버 시작 <<<<<");
			serverSocket = new ServerSocket(HOST_PORT);

			new Thread(() -> {
				while (true) {
					try {
						Socket socket = serverSocket.accept();
						// 여기서 UserSocketThread 생성 해야 한다.
						UserSocketThread userSocketThread = new UserSocketThread(vectors.size() + 1, socket, mContext);
						userSocketThread.start();
						// 벡터에 연결된 유저 정보 소켁을 담아서 관리 !
						vectors.add(userSocketThread);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void broadcast(String msg) {
		for (int i = 0; i < vectors.size(); i++) {
			vectors.get(i).sendMessage(msg);
		}
	}

	public static void main(String[] args) {
		new ServerFile();
	}
}
