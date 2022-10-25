package bad.server;

import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerGUI extends JFrame implements ActionListener {

	private JTextField tfPort;
	private JTextArea textArea;
	private JLabel lblPortNum;
	private JButton btnServerStart;
	private JButton btnServerStop;

	public ServerGUI() {
		initData();
		setInitLayout();
		addEventListener();
		tfPort.requestFocus(); // 키 이벤트를 받을 컴포넌트를 강제로 지정
	}

	private void initData() {
		setSize(500, 600);
		setTitle("서버창");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//tfPort.requestFocus(); // 키 이벤트를 받을 컴포넌트를 강제로 지

	}

	private void setInitLayout() {
		setVisible(true);
		setLayout(null);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(10, 10, 309, 229);
		textArea = new JTextArea();
		textArea.setBounds(12, 11, 310, 230);
		scrollPane.add(textArea);
		add(scrollPane);
		// textArea.setEditable(false); --> textArea 글 못쓰게 막음

		lblPortNum = new JLabel("포트번호 :");
		lblPortNum.setBounds(12, 273, 82, 15);
		add(lblPortNum);

		tfPort = new JTextField();
		tfPort.setBounds(98, 270, 224, 21);
		add(tfPort);
		tfPort.setColumns(20);

		btnServerStart = new JButton("서버실행");
		btnServerStart.setBounds(12, 315, 154, 23);
		add(btnServerStart);

		btnServerStop = new JButton("서버중지");
		btnServerStop.setBounds(168, 315, 154, 23);
		add(btnServerStop);
		//btnServerStop.setEnabled(false);

	}

	private void addEventListener() {
		tfPort.addActionListener(this);
		btnServerStart.addActionListener(this);
		btnServerStop.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnServerStart) {
			if (tfPort.getText().length() == 0) {
					// 아무런 값이 없을 때, 서버 실행 X
				JOptionPane.showMessageDialog(null, 
						"포트 번호를 입력하세요.", "알림", JOptionPane.ERROR_MESSAGE);
			} else if (tfPort.getText().length() != 0) {

				System.out.println("1111111111111111111111");
				// 값을 가져와서 port변수에 저장시키기
				//port = Integer.parseInt(tfPort.getText());
				//startNetwork();
//				tfPort.setEditable(false);
//				btnServerStart.setEnabled(false);
//				btnServerStop.setEnabled(true); // 버튼 닫아주기!!
			}

		} else if (e.getSource() == btnServerStop) {
			System.out.println("222222222222222");
//			try {
//				//serverSocket.close();
//				//vc.removeAllElements();
//				//vc_room.removeAllElements();
//				tfPort.setEditable(true);
//				btnServerStart.setEnabled(true);
//				btnServerStop.setEnabled(false);
//			} catch (IOException e1) {
//
//			}
		}

	}
	
	

	public static void main(String[] args) {
		new ServerGUI();
	}
}
