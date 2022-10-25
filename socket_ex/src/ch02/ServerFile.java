package ch02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFile {

	ServerSocket serverSocket;
	Socket socket;
	BufferedReader bufferedReader;

	static final String HOST_IP = "localhost";
	static final int HOST_PORT = 22000;

	public ServerFile() {
		initData();
	}

	private void initData() {
		try {
			serverSocket = new ServerSocket(HOST_PORT);
			socket = serverSocket.accept();
			System.out.println(">>>>> 클라이언트 연결 완료! <<<<<");

			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while (true) {
				String msg = bufferedReader.readLine();
				System.out.println("받은 메시지 : " + msg);
			}

		} catch (IOException e) {
			System.out.println("예외 발생 !");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ServerFile();
	}
}
