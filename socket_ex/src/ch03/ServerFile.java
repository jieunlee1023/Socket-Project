package ch03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFile {

	// 클라이언트 연결 받은 소켓

	ServerSocket serverSocket;
	Socket socket;

	BufferedReader bufferedReader;

	// 서버에서 클라이언트 소켓에게 데이터를 보낼 버퍼
	BufferedWriter bufferedWriter;
	// 서버측에서 키보드로 부터 글자를 입력 받는 버퍼
	BufferedReader serverKeyboardReader;

	// 서버자의 경우 호스트 IP와 무관하고 (이미 자기 자신을 갖고 있음)
	// 클라이언트가 접속 할 때 IP를 통해 들어오는 것임!
	static final String HOST_IP = "localhost";
	static final int HOST_PORT = 20000;

	public ServerFile() {
		initData();
	}

	private void initData() {
		try {
			serverSocket = new ServerSocket(HOST_PORT);
			socket = serverSocket.accept();
			System.out.println(">>>클라이언트 연결 완료<<<");

			connectClientSoketWriterReader();
			connectKeyboard();

			WriteThread writeThread = new WriteThread();
			Thread thread = new Thread(writeThread);
			thread.start();

			while (true) {
				String msg = bufferedReader.readLine();
				System.out.println("클라이언트 msg : " + msg);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
				bufferedReader.close();
				serverKeyboardReader.close();
				socket.close();
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private void connectKeyboard() {
		serverKeyboardReader = new BufferedReader(new InputStreamReader(System.in));
	}

	private void connectClientSoketWriterReader() {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 내부 클래스 생성
	class WriteThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					String serverKeyboardMsg = serverKeyboardReader.readLine();
					bufferedWriter.write(serverKeyboardMsg + "\n");
					bufferedWriter.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		new ServerFile();
	}

}// end of class
