package ch03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientFile {

	Socket socket;

	BufferedWriter bufferedWriter;
	BufferedReader bufferedReader;
	BufferedReader keyboardReader;

	boolean flag = true;

	public ClientFile() {
		initData();
	}

	private void initData() {
		System.out.println(" [ 클라이언트 소켓 시작 ] ");
		try {
			socket = new Socket(ServerFile.HOST_IP, ServerFile.HOST_PORT);

			connectSocketReaderWriter();
			connectKeyboardReader();

			// 람다식
			// new Thread( ()-> {}).start();

			ReadThread readThread = new ReadThread();
			Thread thread = new Thread(readThread);
			thread.start();

			while (flag) {
				String keyboardMsg = keyboardReader.readLine();
				bufferedWriter.write(keyboardMsg + "\n");
				bufferedWriter.flush();
			}

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			flag = false;
			try {
				bufferedWriter.close();
				bufferedReader.close();
				keyboardReader.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private void connectSocketReaderWriter() {

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 서버에서 넘어온 데이터를 담는 녀석이다
	private void connectKeyboardReader() {
		keyboardReader = new BufferedReader(new InputStreamReader(System.in));
	}

	private class ReadThread implements Runnable {

		@Override
		public void run() {
			while (flag) {
				try {
					String serverMsg = bufferedReader.readLine();
					System.out.println("서버로 부터 온 msg : " + serverMsg);
				} catch (IOException e) {
					flag = false;
					e.printStackTrace();
				}
			}
		}
	} // end of inner class

	public static void main(String[] args) {
		new ClientFile();
	}

}// end of class
