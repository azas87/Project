package chatting.student;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class Myframe10 extends JFrame implements ActionListener
{
	JButton b1, b2;
	JTextField j2, j3, j4, j5; 
	
	public Myframe10()
	{
		setTitle("Login Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); // 크기변경X
		setLayout(new BorderLayout());
		
		JPanel p1 = new JPanel();
		JLabel l = new JLabel("SCIT CHATTING", JLabel.CENTER);
		l.setFont(new Font("Tahoma", Font.BOLD, 30));
		
		
		p1.add(l);
		add(p1, BorderLayout.NORTH);
		
		
		JPanel p2 = new JPanel(new FlowLayout());
		JLabel l2 = new JLabel("이름");
		l2.setPreferredSize(new Dimension(50, 20)); // 정렬해주는 메서드
		j2 = new JTextField(5);
		JLabel l4 = new JLabel("포트");
		l4.setPreferredSize(new Dimension(30, 20));
		j5 = new JTextField(5);
		p2.add(l2);
		p2.add(j2);
		p2.add(l4);
		p2.add(j5);

		JPanel p3 = new JPanel();
		JLabel l3 = new JLabel("IP주소");
		l3.setPreferredSize(new Dimension(60, 20));
		j4 = new JTextField(15);
		j4.setText("127.0.0.1");
		j4.setEditable(true);
		p3.add(l3);
		p3.add(j4);
		
		JPanel p4 = new JPanel(new GridLayout(2, 1));
		p4.add(p2);
		p4.add(p3);
		add(p4, BorderLayout.CENTER);
		
		JPanel p5 = new JPanel();
		b1 = new JButton("OK");
		b1.addActionListener(this);
		b2 = new JButton("Cancel");
		b2.addActionListener(this);		
		p5.add(b1);
		p5.add(b2);
		add(p5, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JButton source = (JButton)e.getSource();
		
		if(source==b1)
		{
			String name = j2.getText();
			this.dispose(); //화면에서 사라짐과 동시에 객체를 소멸시키는 메서드
			new StudentChattingMain(name);
			//new TeacherChattingMain(name);
		}
		else if(source==b2)
		{	
			System.exit(0);
		}
	}

	
}

	public class LoginUI 
	{
		public static void main(String[] args) 
		{
			Myframe10 f = new Myframe10();
		}
	
	}
