package project.server;

import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.print.attribute.IntegerSyntax;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import lombok.Getter;
import project.util.Define;

@Getter
public class ServerGUI extends JFrame implements ActionListener {

	ServerGUI mContext = this;
	public Server server;
	public UserSocket userSocket;

	private JPanel mainPanel;
	private JLabel logo;
	private JTextArea mainArea;
	private JLabel portNumberName;
	private JTextField inputPortNumber;
	private JButton serverStart;
	private JButton serverStop;

	public ServerGUI() {
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		setSize(500, 650);
		setTitle("Server Page");
		setResizable(false);
		setLocation(100, 187);

		mainPanel = new JPanel();
		logo = new JLabel(new ImageIcon
				(Define.IMAGE_PATH + "logo" + Define.IMAGE_PNG_TYPE));
		mainArea = new JTextArea();
		portNumberName = new JLabel("PORT NUMBER : ");
		inputPortNumber = new JTextField("20000");
		serverStart = new JButton("서버 실행");
		serverStop = new JButton(" 서버 종료");
	}

	private void setInitLayout() {

		setVisible(true);
		setLayout(null);

		setContentPane(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.setBackground(new Color(217, 232, 249));

		logo.setBounds(200, 20, 100, 100);
		add(logo);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(43, 129, 400, 350);
		mainArea.setBounds(45, 130, 400, 350);
		scrollPane.add(mainArea);
		mainPanel.add(scrollPane);
		mainArea.setEditable(false);

		portNumberName.setBounds(90, 500, 120, 20);
		portNumberName.setForeground(new Color(142, 190, 219));
		mainPanel.add(portNumberName);
		inputPortNumber.setBounds(205, 503, 180, 20);
		mainPanel.add(inputPortNumber);

		serverStart.setBounds(120, 540, 120, 30);
		serverStart.setBackground(Color.white);
		serverStart.setFocusPainted(false);
		mainPanel.add(serverStart);

		serverStop.setBounds(250, 540, 120, 30);
		serverStop.setBackground(Color.white);
		serverStop.setFocusPainted(false);
		mainPanel.add(serverStop);
		serverStop.setEnabled(false);

	}

	private void addEventListener() {
		serverStart.addActionListener(this);
		serverStop.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(serverStart)) {
			if (inputPortNumber.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "※ 포트 번호를 입력하여 주세요!", "알림", JOptionPane.ERROR_MESSAGE);
			} else if (inputPortNumber.getText().length() != 0) {
				try {
					int port = Integer.parseInt(inputPortNumber.getText());
					server = new Server(port, this);
					mainArea.append("※ 포트번호 : [ " + server.getPort() + " ] 로 서버를 시작합니다. \n");
					mainArea.append("사용자의 접속을 기다립니다 ‧  ‧  ‧ \n");

					serverStart.setEnabled(false);
					serverStop.setEnabled(true);
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "※ PORT는 숫자만 입력 가능합니다.", "알림", JOptionPane.ERROR_MESSAGE);
				}

			}
		} else if (e.getSource().equals(serverStop)) {
			mainArea.append("※ 서버가 중지되었습니다. \n" + "다시 스타트 버튼을 눌러주세요 \n");
			try {
				server.getServerSocket().close();
				server.userVector.removeAllElements();
				server.roomVector.removeAllElements();
				serverStart.setEnabled(true);
				serverStop.setEnabled(false);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

}
