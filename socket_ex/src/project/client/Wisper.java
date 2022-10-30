package project.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import lombok.Data;
import project.util.Define;

@Data
public class Wisper extends JFrame implements ActionListener {

	private ClientGUI clientGUIContext;
	private JLabel wisperImg;
	private JLabel wisperUser;
	private JTextField wisperField;
	private JTextArea wisperArea;
	private JButton send;

	public Wisper(ClientGUI clientGUIContext) {
		this.clientGUIContext = clientGUIContext;
		initData();
		setInitLayout();
		addEventLisner();
	}

	private void initData() {
		setSize(350, 300);
		setTitle("귓속말 보내기");
		setResizable(false);
		setLocationRelativeTo(null);
		wisperImg = new JLabel
				(new ImageIcon(Define.IMAGE_PATH 
						+ "message" + Define.IMAGE_PNG_TYPE));
		wisperUser = new JLabel("받는 사람 : ");
		wisperField = new JTextField();
		wisperArea = new JTextArea("※ 귓속말로 보낼 메시지를 적어주세요!");
		send = new JButton("보내기");
	}

	private void setInitLayout() {
		setVisible(true);
		setLayout(null);

		send.setBounds(275, 30, 50, 23);
		send.setBorder(new LineBorder(new Color(142, 190, 219)));
		send.setForeground(new Color(27, 135, 196));
		send.setFocusPainted(false); 
		add(send);

		wisperUser.setBounds(10, 30, 100, 23);
		add(wisperUser);

		wisperField.setBounds(85, 30, 180, 23);
		wisperField.setEditable(false);
		add(wisperField);

		wisperArea.setBounds(10, 65, 315, 180);
		wisperArea.setBorder(new LineBorder(Color.gray));
		add(wisperArea);

		wisperImg.setBounds(0, 0, 350, 300);
		add(wisperImg);

	}

	private void addEventLisner() {
		send.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(send)) {
			String message = 
					"Wisper/" + wisperField.getText() 
					+ "@" + clientGUIContext.getClient().getClientNickName()
					+ "@" + wisperArea.getText();
			System.out.println("메시지>>>" + message);
			clientGUIContext.getClient().sendmessage(message);
			JOptionPane.showMessageDialog
			(null, "전송 완료!", "♥", JOptionPane.CLOSED_OPTION, null);
			this.dispose();
		}
	}
}
