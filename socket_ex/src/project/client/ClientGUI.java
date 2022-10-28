package project.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import lombok.Getter;
import project.server.ServerGUI;
import project.util.Define;

@Getter
public class ClientGUI extends JFrame implements ActionListener {

	private ClientGUI mContext = this;
	private Client client;

	private JPanel loginPanel = new JPanel();
	private JLabel loginBgImg = new JLabel(new ImageIcon(Define.IMAGE_PATH + "login" + Define.IMAGE_PNG_TYPE));
	private JLabel hostIp = new JLabel("HOST IP : ");
	private JTextField hostIpTF = new JTextField("127.0.0.1");
	private JLabel serverPort = new JLabel("SERVER PORT : ");
	private JTextField serverPortTF = new JTextField("20000");
	private JLabel nickName = new JLabel("NICK NAME : ");
	private JTextField nickNameTF = new JTextField("이지은");
	private JButton logIn = new JButton("로그인");
	private JButton exit = new JButton("나가기");

	private JPanel watingRoomPanel = new JPanel();
	private JLabel watingRoomBgImg = new JLabel(new ImageIcon(Define.IMAGE_PATH + "waiting" + Define.IMAGE_PNG_TYPE));
//	private JLabel watingRoomGif = new JLabel(new ImageIcon(Define.IMAGE_PATH + "gifimg.gif"));
	private JButton userTotal = new JButton("접속자");
	private JList<String> userTotalList = new JList<>();
	private JButton roomTotal = new JButton("채팅방");
	private JList<String> roomTotalList = new JList<>();
	private JButton sendWisper = new JButton("귓말 보내기");
	private JButton chattingRoomCreate = new JButton("채팅방 개설");
	private JButton chattingRoomStart = new JButton("채팅방 입장");

	private JPanel chattingPanel = new JPanel();
	private JLabel chattingBgImg = new JLabel(new ImageIcon(Define.IMAGE_PATH + "chatting" + Define.IMAGE_PNG_TYPE));
	private JLabel chattingRoomTotal = new JLabel("[ 방 접속자 ]");
	private JList<String> chattingRoomTotalList = new JList<>();
	private JLabel chat = new JLabel("[ 대 화 창 ]");
	private JTextArea viewChat = new JTextArea();
	private JTextArea chatting = new JTextArea();
	private JButton sendButton = new JButton("보내기");
	private JButton chatExit = new JButton("나가기");
	private JButton chattingRoomDelete = new JButton("채팅방 삭제");

	private JTabbedPane jtab = new JTabbedPane(JTabbedPane.TOP);

	private StringTokenizer st;
	private Vector<String> userList = new Vector<String>();
	private Vector<String> roomList = new Vector<String>();
	private Vector<String> chattingList = new Vector<String>();
	private String myRoomName;

	public ClientGUI() {
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		setSize(500, 650);
		setTitle("Client Page");
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void setInitLayout() {

		setVisible(true);
		setLayout(null);

		jtab.setBounds(0, 0, 500, 650);
		this.add(jtab);

		jtab.addTab("로그인", loginPanel);
		loginPanel.setLayout(null);

		hostIp.setBounds(60, 250, 100, 20);
		hostIp.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		hostIp.setForeground(new Color(142, 190, 219));
		loginPanel.add(hostIp);

		hostIpTF.setBounds(180, 250, 230, 30);
		loginPanel.add(hostIpTF);

		serverPort.setBounds(50, 320, 150, 20);
		serverPort.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		serverPort.setForeground(new Color(142, 190, 219));
		loginPanel.add(serverPort);

		serverPortTF.setBounds(180, 320, 230, 30);
		loginPanel.add(serverPortTF);

		nickName.setBounds(50, 390, 150, 20);
		nickName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		nickName.setForeground(new Color(142, 190, 219));
		loginPanel.add(nickName);

		nickNameTF.setBounds(180, 390, 230, 30);
		loginPanel.add(nickNameTF);

		logIn.setBounds(120, 480, 120, 30);
		logIn.setBackground(Color.white);
		logIn.setFocusPainted(false);
		loginPanel.add(logIn);

		exit.setBounds(250, 480, 120, 30);
		exit.setBackground(Color.white);
		exit.setFocusPainted(false);
		exit.setEnabled(false);
		loginPanel.add(exit);

		loginBgImg.setBounds(0, 0, 485, 650);
		loginPanel.add(loginBgImg);

		// 대기실
		jtab.addTab("대기실", watingRoomPanel);
		watingRoomPanel.setLayout(null);

		userTotal.setBounds(40, 70, 200, 30);
		userTotal.setBorder(new LineBorder(new Color(142, 190, 219)));
		userTotal.setForeground(new Color(27, 135, 196));
		userTotal.setBackground(Color.white);
		userTotal.setFocusPainted(false); // 버튼 포커스
		watingRoomPanel.add(userTotal);

		userTotalList.setBounds(40, 100, 200, 400);
		userTotalList.setBorder(new LineBorder(new Color(142, 190, 219)));
		watingRoomPanel.add(userTotalList);

		roomTotal.setBounds(250, 70, 200, 30);
		roomTotal.setBorder(new LineBorder(new Color(142, 190, 219)));
		roomTotal.setForeground(new Color(27, 135, 196));
		roomTotal.setBackground(Color.white);
		roomTotal.setFocusPainted(false); // 버튼 포커스
		watingRoomPanel.add(roomTotal);

		roomTotalList.setBounds(250, 100, 200, 400);
		roomTotalList.setBorder(new LineBorder(new Color(142, 190, 219)));
		watingRoomPanel.add(roomTotalList);

		sendWisper.setBounds(160, 505, 80, 30);
		sendWisper.setBorder(new LineBorder(new Color(142, 190, 219)));
		sendWisper.setForeground(new Color(27, 135, 196));
		sendWisper.setBackground(Color.white);
		sendWisper.setFocusPainted(false); // 버튼 포커스
		sendWisper.setEnabled(false);
		watingRoomPanel.add(sendWisper);

		chattingRoomCreate.setBounds(280, 505, 80, 30);
		chattingRoomCreate.setBorder(new LineBorder(new Color(142, 190, 219)));
		chattingRoomCreate.setForeground(new Color(27, 135, 196));
		chattingRoomCreate.setBackground(Color.white);
		chattingRoomCreate.setFocusPainted(false); // 버튼 포커스
		chattingRoomCreate.setEnabled(false);
		watingRoomPanel.add(chattingRoomCreate);

		chattingRoomStart.setBounds(370, 505, 80, 30);
		chattingRoomStart.setBorder(new LineBorder(new Color(142, 190, 219)));
		chattingRoomStart.setForeground(new Color(27, 135, 196));
		chattingRoomStart.setBackground(Color.white);
		chattingRoomStart.setFocusPainted(false); // 버튼 포커스
		chattingRoomStart.setEnabled(false);
		watingRoomPanel.add(chattingRoomStart);

		watingRoomBgImg.setBounds(0, 0, 500, 650);
		watingRoomPanel.add(watingRoomBgImg);

		// 채팅방
		jtab.addTab("채팅방", chattingPanel);
		chattingPanel.setLayout(null);

		chattingRoomTotal.setBounds(15, 50, 100, 30);
		chattingRoomTotal.setForeground(new Color(27, 135, 196));
		chattingPanel.add(chattingRoomTotal);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(113, 74, 355, 420);
		viewChat.setBorder(new LineBorder(new Color(142, 190, 219)));
		viewChat.setBounds(115, 75, 355, 420);
		scrollPane.add(viewChat);
		chattingPanel.add(scrollPane);
		viewChat.setEditable(false);

		chat.setBounds(120, 50, 100, 30);
		chat.setForeground(new Color(27, 135, 196));
		chattingPanel.add(chat);

		chattingRoomTotalList.setBounds(12, 75, 90, 250);
		chattingRoomTotalList.setBorder(new LineBorder(new Color(142, 190, 219)));
		chattingRoomTotalList.setEnabled(false);
		chattingPanel.add(chattingRoomTotalList);

		chatting.setBounds(12, 520, 370, 50);
		chatting.setBorder(new LineBorder(new Color(142, 190, 219)));
		chattingPanel.add(chatting);

		sendButton.setBounds(390, 520, 80, 50);
		sendButton.setBorder(new LineBorder(new Color(142, 190, 219)));
		sendButton.setForeground(new Color(27, 135, 196));
		sendButton.setBackground(Color.white);
		sendButton.setFocusPainted(false);
		sendButton.setEnabled(false);
		chattingPanel.add(sendButton);

		chattingRoomDelete.setBounds(300, 10, 80, 30);
		chattingRoomDelete.setBackground(Color.white);
		chattingRoomDelete.setForeground(new Color(27, 135, 196));
		chattingRoomDelete.setFocusPainted(false);
		chattingRoomDelete.setBorder(new LineBorder(new Color(142, 190, 219)));
		chattingRoomDelete.setEnabled(false);
		chattingPanel.add(chattingRoomDelete);

		chatExit.setBounds(390, 10, 80, 30);
		chatExit.setBackground(Color.white);
		chatExit.setForeground(new Color(27, 135, 196));
		chatExit.setFocusPainted(false);
		chatExit.setBorder(new LineBorder(new Color(142, 190, 219)));
		chatExit.setEnabled(false);
		chattingPanel.add(chatExit);

		chattingBgImg.setBounds(0, 0, 500, 650);
		chattingPanel.add(chattingBgImg);

	}

	private void addEventListener() {
		logIn.addActionListener(this);
		exit.addActionListener(this);
		userTotal.addActionListener(this);
		roomTotal.addActionListener(this);
		sendWisper.addActionListener(this);
		chattingRoomCreate.addActionListener(this);
		chattingRoomStart.addActionListener(this);
		chattingRoomDelete.addActionListener(this);
		sendButton.addActionListener(this);
		chatExit.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(logIn)) {
			if (hostIpTF.getText().length() == 0) {
				hostIpTF.setText("※ IP를 입력하세요.");
				hostIpTF.requestFocus();
			} else if (serverPortTF.getText().length() == 0) {
				serverPortTF.setText("※ PORT 번호를 입력하세요.");
				serverPortTF.requestFocus();
			} else if (nickNameTF.getText().length() == 0) {
				nickNameTF.setText("※ 유저 아이디를 입력하세요.");
				nickNameTF.requestFocus();
			} else {
				loginConnet();
				userList.add(client.getUserNickName());
				userTotalList.setListData(userList);

				if (client.isLoginConnect() == true) {
					hostIpTF.setEnabled(false);
					serverPortTF.setEnabled(false);
					nickNameTF.setEnabled(false);
					logIn.setEnabled(false);
					exit.setEnabled(true);
					userTotal.setEnabled(true);
					roomTotal.setEnabled(true);
					sendWisper.setEnabled(true);
					chattingRoomCreate.setEnabled(true);
					chattingRoomStart.setEnabled(true);
					chattingRoomDelete.setEnabled(false);
					sendButton.setEnabled(false);
					chatExit.setEnabled(false);

					jtab.setSelectedIndex(1);
				}

			}

		} else if (e.getSource().equals(sendWisper)) {
			String user = userTotalList.getSelectedValue();
			if (user == null) {
				JOptionPane.showMessageDialog(null, "※ 대상을 선택하세요", "알림", JOptionPane.ERROR_MESSAGE);
			} else {
				Wisper wisperFrame = new Wisper(mContext);
				wisperFrame.getWisperField().setText(user);

			}
		} else if (e.getSource().equals(chattingRoomCreate)) {
			System.out.println(" 채팅방 개설 ");
			String roomName = JOptionPane.showInputDialog("방 이름을 입력하세요");
			if (roomName != null) {
				client.sendmessage("CreateRoom/" + roomName);
				jtab.setSelectedIndex(2);

				client.sendmessage("NewChatUser/ [ " + client.getClientNickName() + " ] 님 입장 ");

				sendWisper.setEnabled(false);
				chattingRoomCreate.setEnabled(false);
				chattingRoomStart.setEnabled(false);
				chattingRoomDelete.setEnabled(true);
				sendButton.setEnabled(true);
				chatExit.setEnabled(true);
			}

		} else if (e.getSource().equals(chattingRoomStart)) {
			System.out.println(" 채팅방 입장 ");
			viewChat.setText(" ");
			String joinRoom = roomTotalList.getSelectedValue();
			if (joinRoom == null) {
				JOptionPane.showMessageDialog(null, "※ 현재 선택된 채팅방이 없습니다.", "알림", JOptionPane.ERROR_MESSAGE);

			} else if (joinRoom != null && roomTotalList != null) {
				client.sendmessage("JoinRoom/" + joinRoom);
				jtab.setSelectedIndex(2);

				client.sendmessage(
						"NewChatUser/ [ " + client.getClientNickName() + " ] 님 [ " + myRoomName + " ] 에 입장 ");

				sendWisper.setEnabled(false);
				chattingRoomCreate.setEnabled(false);
				chattingRoomStart.setEnabled(false);

				chattingRoomDelete.setEnabled(true);
				sendButton.setEnabled(true);
				chatExit.setEnabled(true);
			}
		} else if (e.getSource().equals(sendButton)) {
			System.out.println("메시지 보내기 ");
			if (chatting.getText() != null) {
				client.sendmessage("Chatting/" + myRoomName + "/" + chatting.getText().trim());
			}
		} else if (e.getSource().equals(chattingRoomDelete)) {
			System.out.println(" 채팅방삭제 ");
			client.sendmessage("DeleteRoom/" + myRoomName);
			JOptionPane.showMessageDialog(null, "※ 방이 삭제 되었습니다!", "알림", JOptionPane.ERROR_MESSAGE);
			jtab.setSelectedIndex(1);
			viewChat.setText(" ");

			sendWisper.setEnabled(true);
			chattingRoomCreate.setEnabled(true);
			chattingRoomStart.setEnabled(true);
			chattingRoomDelete.setEnabled(false);
			sendButton.setEnabled(false);
			chatExit.setEnabled(false);

			jtab.setSelectedIndex(1);

		} else if (e.getSource().equals(chatExit)) {
			JOptionPane.showMessageDialog(null, "채팅방을 나갑니다", "알림", JOptionPane.CANCEL_OPTION);
			client.sendmessage("ExitRoom/" + myRoomName);
			jtab.setSelectedIndex(1);
			viewChat.setText(" ");

			sendWisper.setEnabled(true);
			chattingRoomCreate.setEnabled(true);
			chattingRoomStart.setEnabled(true);
			chattingRoomDelete.setEnabled(false);
			sendButton.setEnabled(false);
			chatExit.setEnabled(false);
		} else if (e.getSource().equals(exit)) {
			System.exit(0);
		}
		chatting.setText("");
	}

	private void loginConnet() {

		try {
			String ip = hostIpTF.getText();
			int port = Integer.parseInt(serverPortTF.getText());
			String nickName = nickNameTF.getText();
			client = new Client(ip, port, nickName, this);
			System.out.println("아이피 : " + ip);
			System.out.println("포트번호 : " + port);
			System.out.println("닉네임 : " + nickName);

			if (client.isLoginConnect() != false) {
				logIn.setEnabled(false);
				setTitle("[ " + client.getClientNickName() + " ] 님의 네이트온 ♥");
			}

		} catch (Exception e2) {
			serverPortTF.setText("※ 숫자만 입력 가능합니다.");
		}

	}

	public void inmessage(String msg) {
		st = new StringTokenizer(msg, "/");

		String protocol = st.nextToken();
		String message = st.nextToken();

		System.out.println("client 프로토콜 : " + protocol);
		System.out.println("client 메세지 : " + message);
		if (protocol.equals("NewUser")) {
			userList.add(message);
			userTotalList.setListData(userList);
		} else if (protocol.equals("OldUser")) {
			userList.add(message);
			userTotalList.setListData(userList);
		} else if (protocol.equals("NewChatUser")) {
			chattingList.add(message);
			chattingRoomTotalList.setListData(chattingList);
		} else if (protocol.equals("Wisper")) {

			System.out.println("귓말 들어옴! " + message);
			st = new StringTokenizer(message, "@");
			String fromUser = st.nextToken();
			String wisperMessage = st.nextToken();

			Wisper wisper = new Wisper(this);
			wisper.getSend().setVisible(false);
			wisper.setTitle(fromUser + "에게 온 귓속말 ♥");
			wisper.getWisperUser().setText("보낸 사람 : ");
			wisper.getWisperField().setText(fromUser);
			wisper.getWisperArea().setText(wisperMessage);

		} else if (protocol.equals("CreateRoom")) {
			myRoomName = message;
		} else if (protocol.equals("CreateRoomFail")) {
			JOptionPane.showMessageDialog(null, "※ 같은 방 이름이 존재합니다.!", "알림", JOptionPane.ERROR_MESSAGE);
		} else if (protocol.equals("NewRoom")) {
			roomList.add(message);
			roomTotalList.setListData(roomList);
		} else if (protocol.equals("Chatting")) {
			String chattingMsg = st.nextToken();
			viewChat.append(message + " : " + chattingMsg + "\n");
		} else if (protocol.equals("OldRoom")) {
			roomList.add(message);
			roomTotalList.setListData(roomList);
		} else if (protocol.equals("JoinRoom")) {
			myRoomName = message;
			JOptionPane.showMessageDialog(null, "채팅방 [ " + myRoomName + " ] 에 입장완료", "알림",
					JOptionPane.INFORMATION_MESSAGE);
			viewChat.setText(" ");
		} else if (protocol.equals("UserOut")) {
			chattingList.remove(message);
			chattingRoomTotalList.setListData(chattingList);
			viewChat.append("[ ※ < " + client.getClientNickName() + " > 가 < " + myRoomName + " > 에서 퇴장하였습니다. ] \n ");
		} else if (protocol.equals("UserAllOut")) {
			chattingList.removeAllElements();
			viewChat.append("※ [ " + myRoomName + " ] 방이 삭제 되었습니다. \n");
			sendWisper.setEnabled(true);
			chattingRoomCreate.setEnabled(true);
			chattingRoomStart.setEnabled(true);
			chattingRoomDelete.setEnabled(false);
			sendButton.setEnabled(false);
			chatExit.setEnabled(false);
			chattingRoomTotalList.setListData(chattingList);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			viewChat.setText(" ");
		} else if (protocol.equals("UpdateDeleteUserData")) {
			userTotalList.setListData(userList);
			roomTotalList.setListData(roomList);
		} else if (protocol.equals("UpdateExitUserData")) {
			userTotalList.setListData(userList);
			roomTotalList.setListData(roomList);
		} else if (protocol.equals("ExitRoom")) {
			myRoomName = null;
			viewChat.append("[ " + myRoomName + " ] 에서 [ " + client.getClientNickName() + " ] 님이 퇴장했습니다.\n");
		} else if (protocol.equals("DeleteRoom")) {
			myRoomName = null;
			chattingList.remove(message);
			chattingRoomTotalList.setListData(chattingList);
			jtab.setSelectedIndex(1);
		} else if (protocol.equals("EmptyRoom")) {
			roomList.remove(message);
		} else if (protocol.equals("ErrorOutRoom")) {
			roomList.remove(message);
		}
	}

	public static void main(String[] args) {
		new ClientGUI();
		new ClientGUI();
		new ServerGUI();
	}

}
