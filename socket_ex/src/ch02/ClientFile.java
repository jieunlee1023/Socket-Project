package ch02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientFile {

	Socket socket;
	BufferedWriter bufferedWriter;
	BufferedReader keyboardReader;

	public ClientFile() {
		initData();
	}

	private void initData() {
		try {
			socket = new Socket(ServerFile.HOST_IP, ServerFile.HOST_PORT);
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			// 키보드 연결
			keyboardReader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println(">>>>> 키보드 입력 대기 <<<<<");
				String keybordMsg = keyboardReader.readLine();
				
				//★ 중요! 메세지 끝을 알려줘야 한다.
				bufferedWriter.write(keybordMsg + "\n");
				bufferedWriter.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ClientFile();
	}
}
