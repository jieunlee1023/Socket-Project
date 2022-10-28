package project.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import lombok.Data;

@Data
public class Client {

	private ClientGUI mContext;
	private Socket socket;

	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;

	private String clientIp;
	private int clientPort;
	private String clientNickName;

	private String userNickName;

	private boolean flag = true;
	private boolean loginConnect = true;

	public Client(String clientIp, int clientPort, String clientNickName, ClientGUI mContext) {
		this.mContext = mContext;
		this.clientIp = clientIp;
		this.clientPort = clientPort;
		this.clientNickName = clientNickName;
		initData();
	}

	private void initData() {
		try {
			socket = new Socket(clientIp, clientPort);
			JOptionPane.showMessageDialog
			(null, clientNickName + " 님 안녕하세요! \n 접속 완료 되었습니다. ☺ ", "알림",
					JOptionPane.INFORMATION_MESSAGE);

			connectSocketReaderWriter();

			userNickName = clientNickName;
			sendmessage(userNickName.trim());

			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							String msg = bufferedReader.readLine();
							mContext.inmessage(msg);
						} catch (IOException e) {
							try {
								mContext.getUserList().removeAll(mContext.getUserList());
								mContext.getUserTotalList().setListData(mContext.getUserList());
								mContext.getRoomList().removeAll(mContext.getRoomList());
								mContext.getRoomTotalList().setListData(mContext.getRoomList());
								mContext.getViewChat().setText("\n");
								bufferedReader.close();
								bufferedWriter.close();
								socket.close();
								JOptionPane.showMessageDialog
								(null, "※ 서버가 종료되었습니다.", "알림", JOptionPane.ERROR_MESSAGE);
								break;
							} catch (Exception e1) {
								return;
							}
						}
					}
				}
			});
			thread.start();

		} catch (IOException e) {
			loginConnect = false;
			JOptionPane.showMessageDialog(null, 
					"연결에 실패했습니다.", "알림", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			loginConnect = false;
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, 
					"연결에 실패했습니다.", "알림", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void connectSocketReaderWriter() {

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendmessage(String msg) {
		try {
			bufferedWriter.write(msg + "\n");
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
