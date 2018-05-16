package chatting.server;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class LoginServerUI extends JFrame implements ActionListener {
	private JTextField tf_port;
	private JButton btn_start;
	private int port;
	public LoginServerUI() {
		
		super("Server Login");
		setPreferredSize(new Dimension(340, 65));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lbl_port = new JLabel("\uD3EC\uD2B8 \uBC88\uD638");
		getContentPane().add(lbl_port);
		
		tf_port = new JTextField();
		getContentPane().add(tf_port);
		tf_port.setColumns(10);
		
		btn_start = new JButton("\uD655\uC778");
		btn_start.addActionListener(this);
		getContentPane().add(btn_start);
		
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		
		if( source == btn_start )
		{
			if( btn_start.getText().equals("확인") ) {
				this.setTitle("서버 실행 중..");
				btn_start.setText("종료");
				port = Integer.parseInt(tf_port.getText());
				Thread serverStart = new Thread(new ChattingServer(port));
				serverStart.start();
			}
			else
			{
				ChattingServer.exit();
				this.dispose();
			}
		}
	}
	
	public static void main(String[] args) {
		new LoginServerUI();
	}

}
