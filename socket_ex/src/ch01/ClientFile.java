package ch01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientFile {

	Socket socket;
	BufferedWriter bufferedWriter;
	// 키보드와 연결하는 스트림 필요
	BufferedReader bufferedReader; // 키보드용

	public ClientFile() {
		System.out.println(">>>>> 1. 클라이언트 소켓 시작 <<<<<");

		try {
			// 클라이언트 쪽은 포트번호를 지정해주어야 한다.
			socket = new Socket(ServerFile.IP, ServerFile.HOST_PORT);
			System.out.println(">>>>> 2. 버퍼 연결 <<<<<");
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			System.out.println(">>>>> 3. 키보드 연결 <<<<<");
			// 키보드 연결
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println(">>>>> 4. 키보드 입력 대기<<<<<");
			String keyboardMsg = bufferedReader.readLine();

			// ★ 중요 !!!! 메세지 끝을 알려주어야 한다. \n
			bufferedWriter.write(keyboardMsg + "\n");
			bufferedWriter.flush();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new ClientFile();
	}
}
