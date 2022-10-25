package project.server;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerGUI extends JFrame {

	private JTextArea mainArea = new JTextArea();
	private JTextField inputPortNumber = new JTextField();
	private JLabel portNumberName = new JLabel("포트번호 : ");
	private JButton ServerStart = new JButton("서버 입장");
	private JButton ServerStop = new JButton(" 서버 종료");

	public ServerGUI() {
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		setSize(500, 600);
		setTitle("서버창");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void setInitLayout() {
		setVisible(true);
		setLayout(null);

	}

	private void addEventListener() {

	}
	
	public static void main(String[] args) {
		new ServerGUI();
	}
}
