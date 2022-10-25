package ch01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

// 서버 역할을 하는 소켓통신 프로그램을 만들 예정
// 필요한 도구는 ?? 
public class ServerFile {

	ServerSocket serverSocket;
	Socket socket;

	// Byte Stream ---> 0101로 하기엔 불편함으로
	// 문자열을 처리 하기 위해서 버퍼 달기
	BufferedReader bufferedReader;
	static final String IP = "127.0.0.1"; // =localhost
	static final int HOST_PORT = 20000;

	public ServerFile() {
		System.out.println(">>>>> 1. 서버 소켓 시작 <<<<<");
		// 서버 소켓 : 접속을 받기 위해서 대기만 하는 녀석
		try {
			// 서버 파일 소켓에 포트번호 생성
			serverSocket = new ServerSocket(HOST_PORT);
			System.out.println(">>>>> 2. 서버 소켓 생성 <<<<<");
			// 클라이언트가 접속을 하면 새로운 소켓을 생성 (accept() 새로운 소켓 반환)
			socket = serverSocket.accept(); // 클라이언트 접속을 기다리고 있다. 시작
			System.out.println(">>>>> 3. 클라이언트 연결 완료! <<<<<");

			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String msg = bufferedReader.readLine();
			System.out.println("클라이언트로 부터 받은 메시지 : " + msg);

		} catch (IOException e) {
			System.out.println(">>>>> 예외 발생 <<<<<");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ServerFile();

	}

}
