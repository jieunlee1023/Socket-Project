package ch04.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

// 클라이언트 소켓과 대응하는 서브측 소켓 -- Socket
public class UserSocketThread extends Thread {
	// 클라이언트와 연결 스트림 처리
	// 동작 --> 동작을 해야하므로 Thread 처리해야함

	int id;
	private boolean isRun = true;

	// 의존성 컴포지션 관계
	// ServerUserSoket이 태어날 때 Socket이 생성되어야함

	ServerFile mContext;
	Socket socket;

	BufferedReader bufferedReader;
	BufferedWriter bufferedWriter;

	public UserSocketThread(int id, Socket socket, ServerFile mContext) {
		this.id = id;
		this.socket = socket;
		this.mContext = mContext;
		System.out.println(id + "유저 연결됨 ");
		initData();
	}

	private void initData() {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String msg) {
		try {
			// 중요! : 마지막 구분값이 있어야 한다. 엔터 (줄바꿈)
			bufferedWriter.write(msg + "\n");
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 클라이언트에서 보낸 메세지를 계속 받아야 한다.
	@Override
	public void run() {
		// UserSocketThread 도 다른 작업을 해야 한다면,
		// 이부분은 따로 또 새로운 스레드로 만들어야한다.
		new Thread(() -> {
			while (isRun) {
				try {
					String msg = bufferedReader.readLine();
					System.out.println(id + " 가 넘겨 받은 메세지 : " + msg);
					// TODO 추가 코드 부분 !!!
					mContext.broadcast(msg);

				} catch (IOException e) {
					e.printStackTrace();
					isRun = false;
					try {
						bufferedReader.close();
						bufferedWriter.close();
						socket.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}

				}
			}
		}).start();
	}

}
