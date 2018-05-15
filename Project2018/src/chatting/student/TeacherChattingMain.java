package chatting.student;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import chatting.data.Data;
import chatting.data.Log;

import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Label;
import javax.swing.border.LineBorder;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Button;
import javax.swing.BoxLayout;

public class TeacherChattingMain extends JFrame implements ActionListener, Runnable, MouseListener {

	private JPanel contentPane;
	private JScrollPane sp_chatOutput;
	private JTextArea ta_chatOutput;
	private JPanel p_south;
	private JTextField tf_chatInput;
	private JButton btn_send;
	private JPanel p_east;
	private JLabel lbl_count;
	private JScrollPane scrollPane_1;
	private JList li_userList;
	private String name;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Data data;
	private JPanel panel;
	private JLabel lable_file;
	private JScrollPane scrollPane;
	private JList file_list;
	private JButton btnNewButton;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_9;
	private Button b_upload;
	private Button b_download;
	private DefaultListModel content = new DefaultListModel();
	private ArrayList<String> as = new ArrayList<String>();
	private int count;
	private JPanel panel_3;
	private JPanel panel_5;
	private JLabel lblNewLabel;
	private JButton b_log;
	private JButton b_serch;
	private JTextField textField_1;
	private JScrollPane scrollPane_2;

	private JList list;
	private DefaultListModel logContent = new DefaultListModel();
	
	private String str = "D:\\IT_MASTER";
	private JTree tree;
	private JButton btn_create_folder;
	private JButton btn_delete_folder;
	private JButton btn_download;
	DefaultMutableTreeNode node;
	public static JProgressBar progressbar;
	public static JLabel lbl_per;
	private String SEVER_IP = "127.0.0.1";
//	private String SEVER_IP = "203.233.196.50";
//	private String SEVER_IP = "203.233.196.48";
	
	private FtpClientThread cst;
	private JButton btn_cancel;
	private boolean exit;
	private JButton btn_logs;
	private DataInputStream dis;
	private DataOutputStream dos;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	private JScrollPane sp_userList;
	


	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public TeacherChattingMain(String name) {
		
		this.name = name;
		setTitle("SCIT\uCC44\uD305(\uAD00\uB9AC\uC790)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 616, 424);
		JTabbedPane main = new JTabbedPane();
		main.setToolTipText("main");
		contentPane = new JPanel();
		main.add(contentPane);
		main.setTitleAt(0, "Main");
		contentPane.setPreferredSize(new Dimension(20, 20));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(main);
		
		sp_chatOutput = new JScrollPane();
		sp_chatOutput.setEnabled(false);
		contentPane.add(sp_chatOutput, BorderLayout.WEST);
		
		ta_chatOutput = new JTextArea();
		ta_chatOutput.setPreferredSize(new Dimension(24, 24));
		ta_chatOutput.setEditable(false);
		ta_chatOutput.setRows(10);
		ta_chatOutput.setColumns(20);
		sp_chatOutput.setViewportView(ta_chatOutput);
		
		p_south = new JPanel();
		contentPane.add(p_south, BorderLayout.SOUTH);
		p_south.setLayout(new BorderLayout(0, 0));
		
		btn_send = new JButton("\uC804\uC1A1");
		btn_send.addActionListener(this);
		p_south.add(btn_send, BorderLayout.EAST);
		
		tf_chatInput = new JTextField();
		p_south.add(tf_chatInput, BorderLayout.CENTER);
		tf_chatInput.addActionListener(this);
		tf_chatInput.setColumns(25);
		
		p_east = new JPanel();
		contentPane.add(p_east, BorderLayout.CENTER);
		p_east.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		p_east.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		lbl_count = new JLabel("\uC811\uC18D \uC778\uC6D0 : 00\uBA85");
		panel_1.add(lbl_count, BorderLayout.CENTER);
		lbl_count.setSize(new Dimension(50, 15));
		lbl_count.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl_count.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnNewButton = new JButton("\uC804\uCCB4\uC120\uD0DD");
		panel_1.add(btnNewButton, BorderLayout.EAST);
		
		scrollPane_1 = new JScrollPane();
		p_east.add(scrollPane_1, BorderLayout.CENTER);
		
		li_userList = new JList<String>();
		
		li_userList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		li_userList.setPreferredSize(new Dimension(150, 100));
		scrollPane_1.setViewportView(li_userList);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		file_list = new JList();
		file_list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		file_list.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		file_list.setForeground(Color.LIGHT_GRAY);
		file_list.addMouseListener(this);
		//file_list.setDropMode(DropMode.INSERT_ROWS);
		scrollPane.setViewportView(file_list);
		
		panel_9 = new JPanel();
		panel.add(panel_9, BorderLayout.NORTH);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		lable_file = new JLabel("\uD30C\uC77C\uBAA9\uB85D");
		panel_9.add(lable_file, BorderLayout.WEST);
		lable_file.setHorizontalAlignment(SwingConstants.CENTER);
		lable_file.setPreferredSize(new Dimension(120, 14));
		
		b_upload = new Button("\uC5C5\uB85C\uB4DC");
		panel_9.add(b_upload, BorderLayout.CENTER);
		
		b_download = new Button("\uB2E4\uC6B4\uB85C\uB4DC");
		panel_9.add(b_download, BorderLayout.EAST);
		
		panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(100, 170));
		main.addTab("Management", null, panel_2, null);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.NORTH);
		
		lblNewLabel = new JLabel("Memory");
		panel_3.add(lblNewLabel);
		
		panel_5 = new JPanel();
		panel_5.setOpaque(false);
		panel_5.setRequestFocusEnabled(false);
		
		panel_2.add(panel_5, BorderLayout.SOUTH);
		
		b_serch = new JButton("\uAC80\uC0C9");
		b_serch.addActionListener(this);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textField_1 = new JTextField();
		panel_5.add(textField_1);
		textField_1.setColumns(10);
		
		b_log = new JButton("\uB85C\uADF8");
		b_log.addActionListener(this);
		panel_5.add(b_log);
		panel_5.add(b_serch);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setSize(new Dimension(350, 180));
		scrollPane_2.setPreferredSize(new Dimension(11, 2));
		scrollPane_2.setAutoscrolls(true);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_2.add(scrollPane_2, BorderLayout.CENTER);
		list = new JList<String>();

		
		
		list.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		scrollPane_2.setViewportView(list);
		setVisible(true);
		connectServer();
	
		//li_userList.setListData(new String [] {id});
		
		addWindowListener(new WindowAdapter() 
		{
			public void windowOpened(WindowEvent e) 
			{
				tf_chatInput.requestFocus();
			}
			
		});
			
	}
	
	private void connectServer() {
		Socket client = null;
		try {
			client = new Socket(SEVER_IP, 7777);
			oos = new ObjectOutputStream(client.getOutputStream());
			ois = new ObjectInputStream(client.getInputStream());
			data = new Data(name, "님이 접속했습니다.", Data.CHAT_LOGIN);
			Thread t = new Thread(this);
			t.start();

		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("접속 실패1");
		} catch (IOException e) {
			e.printStackTrace();
			// 잘못 된 주소 넣으면 하~~~~~안참 있다가 여기 수행함. UnknownHostException는 언제 타나?
			// 서버가 동작중이지 않는 경우.
			System.out.println("접속 실패2");
		}

	}
	
	private void sendData(Data data)
	{
		try 
		{
			oos.writeObject(data);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		
		BufferedWriter out = null;
		BufferedReader in = null;
		
		String message = ta_chatOutput.getText();
		//data = new Data(id, message, Data.CHAT_MESSAGE);
		//sendData(data);
		
		
		StringTokenizer st = new StringTokenizer(message, "\n");
		
		if(source == tf_chatInput)
		{	
		String message1 = tf_chatInput.getText();
		//ta_chatOutput.append("["+id+"]" + message1 +"\n");
		data = new Data(name, message1, Data.CHAT_MESSAGE);
		sendData(data);
		tf_chatInput.setText("");
		}
		else if(source == b_upload)
		{
			
		}
		else if(source == b_download)
		{
			//str = "D:\\IT_MASTER";
			str = "C:\\Users\\azas8\\Desktop\\매\\프로젝트_찬주\\Project2018_1";
			System.out.println("getlist before");
			getList();
		}	
		
		/*else if(source == mi_exit)
		{
			int choice = JOptionPane.showConfirmDialog(this, "정말로 종료 하겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
			if(choice == JOptionPane.YES_OPTION)
				
			data = new Data(id, "님이 종료했습니다.", Data.CHAT_LOGOUT);
			sendData(data);
		
			{
				System.exit(0);
			}
		}
		else if(source==mi_save)
		{
			JFileChooser save = new JFileChooser();
			save.showSaveDialog(this);
			File file = save.getSelectedFile();
			
			try 
			{
				out = new BufferedWriter(new FileWriter(file));
				while(st.hasMoreTokens())
				{
					String s = st.nextToken();
					out.write(s);
					out.newLine();
				}
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
			finally
			{
				try 
				{
					if(out!=null)
					{	
						out.close();
					}
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
				
			}		
			
		}	
		else if(source==mi_text)
		{
			JFileChooser save = new JFileChooser();
			save.showOpenDialog(this);
			File file = save.getSelectedFile();
			try 
			{
				in = new BufferedReader(new FileReader(file));
			} 
			catch (FileNotFoundException e1) 
			{
				e1.printStackTrace();
			}
		}	*/
		else if(source == btn_send)
		{
			
			String targetId = (String)li_userList.getSelectedValue();
			if(targetId == null)
			{
				JOptionPane.showConfirmDialog(this, "귓말을 보낼 대상을 선택하시오","ERROR", JOptionPane.WARNING_MESSAGE);
				
			}	
			else
			{
				String message1 = tf_chatInput.getText();
				data = new Data(name, message1, targetId, Data.CHAT_WHISPER); /****************/
				sendData(data);
			}	
			tf_chatInput.requestFocus();
		}
		else if(source == b_log)
		{
			data = new Data(name, null, null, Data.Log_ALL); 
			System.out.println(data.getId());
			sendData(data);
		}
		else if(source == b_serch)
		{
			
		}	
		/*
		else if( source == btn_create_folder )
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			
			if(node != null)
			{
				TreeNode[] tn = node.getPath();
				
				int index = tn.length;
				if(node.getChildCount() == 0)
				{
					index -= 2;
				}
				else
				{
					index--;
				}
				
				
				int choice = JOptionPane.showConfirmDialog(this, tn[index]+"폴더에 새로운 폴더를 생성하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
				if( choice == JOptionPane.YES_OPTION )
				{
					String path="";				
					for(int i = 1; i <= index; i++)
					{
						path += tn[i]+"\\";
					}
					
					System.out.println(path);
					
					data = new Data(name, path+"test", Data.FILE_CREATE);
					sendData(data);
					
					data = new Data(name, null, Data.CHAT_TREE);
					sendData(data);
				}
			}
			else
			{
				System.out.println("폴더를 생성할 경로를 선택하세요.");
			}
		}
		else if( source == btn_delete_folder )
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			
			if(node != null)
			{
				TreeNode[] tn = node.getPath();
				
				int index = tn.length;
				if(node.getChildCount() == 0)
				{
					index -= 2;
				}
				else
				{
					index--;
				}
				
				
				int choice = JOptionPane.showConfirmDialog(this, tn[index]+"폴더를 삭제 하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
				if( choice == JOptionPane.YES_OPTION )
				{
					String path="";				
					// 여긴 마지막 노드까지 나와야 해서 길이만큼 뽑어야 함.
					for(int i = 1; i < tn.length; i++)
					{
						path += tn[i]+"\\";
					}
					
					System.out.println(path);
					
					data = new Data(name, path, Data.FILE_DELETE);
					sendData(data);
					
					data = new Data(name, null, Data.CHAT_TREE);
					sendData(data);
				}
			}
			else
			{
				System.out.println("폴더를 생성할 경로를 선택하세요.");
			}
			
		}
		else if( source == btn_download)
		{
			node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			if (node != null && (node.getChildCount() == 0) ) 
			{
				String path="";
				TreeNode[] tn = node.getPath();			
				for(int i = 1; i < tn.length; i++)
				{
					path += tn[i]+"\\";
				}
				
				System.out.println(path.substring(0, path.length()-1));
	    		ftpconnect(path.substring(0, path.length()-1),0);
			}
		}
		else if( source == btn_cancel)
		{
			FtpClientThread.isCancel = true;
		}
		else if( source == btn_logs)
		{
			System.out.println("1111");
			data = new Data(name, null, Data.Log_ALL);
			sendData(data);
		}
		*/
	}

	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2)
		{
			if(file_list.getSelectedValue().equals(".."))
			{
				
				str = new File(str).getParent();
			}
			else 
			{

				File file = new File(str+"\\"+file_list.getSelectedValue());
				if(file.isDirectory())
				{
					str += "\\"+file_list.getSelectedValue();
				}	
			}
			getList();
		}
		
	}
	public void fileName(File f, int count)
	{
		
		File [] ff = f.listFiles();
		
		
		for(File file : ff)
		{
			if(file.isDirectory())
			{
				for(int i = 0 ; i < count ; i++)
				{
					as.add("\t");
				}	
				as.add("+"+file.getName());
				as.add("\n");
				fileName(file,count+1);
				
			}
			else
			{
				for(int i = 0 ; i < count ; i++)
				{
					as.add("\t");
				}	
				as.add("-"+file.getName());
				as.add("\n");
			}
			
		}
		count--;
		
	}
	
	
	public void getList()
	{
		File file = new File(str);
		if(file.isDirectory())
		{	
			
			String [] str = file.list();
			File [] f = file.listFiles();
			ArrayList<String> af = new ArrayList<String>();
			ArrayList<String> ad = new ArrayList<String>();
			
			for(int i = 0 ; i < f.length ; i++)
			{
				if(f[i].isFile())
				{
					af.add(f[i].getName());
				}
				else
				{
					ad.add(f[i].getName());
				}	
			}
			Collections.sort(af);
			Collections.sort(ad);
			
			content.clear();
			if(!file.getAbsolutePath().equals("D:\\IT_MASTER"))
			{
				String back = "..";
    			content.addElement(back);
			}
			
			for(String ss : ad)
			{
				content.addElement(ss);
			}
			
			for(String stt : af)
			{
				content.addElement(stt);
			}	
			file_list.setModel(content);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
	
		
	}
	
	public void ftpconnect(String path, int mode)
	{
		Socket ftpclient;
		
		try {
			ftpclient = new Socket(SEVER_IP, 8888);
			dos = new DataOutputStream(ftpclient.getOutputStream());
			dis = new DataInputStream(ftpclient.getInputStream());
			FtpClientThread cst = new FtpClientThread(dis, dos, mode, path);
			Thread t1 = new Thread(cst);
			t1.start();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("IOException 2222");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("서버가 켜지 있지 않을 때 나왔음. IOException 1111");
		} 
	}	
	
	public void closeAll(){
		System.out.println("모든 자원 종료");
		try { if(ois != null) {oos.close(); }} catch (IOException e) {}
		try { if(oos != null) {ois.close();}} catch (IOException e) {}
		
	}

	

	@Override
	public void run() 
	{
		try {
			System.out.println("11111");
			oos.writeObject(data);
			System.out.println("222222");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true)
		{
			try 
			{
				data = (Data)ois.readObject();
				switch(data.getStatus())
				{
					case Data.CHAT_LOGIN :
					case Data.CHAT_LOGOUT : 
											ta_chatOutput.append("[" + data.getId() + "]" + data.getMessage() + "\n");
											li_userList.setListData(data.getUserList());
											break;
											
				   case Data.CHAT_MESSAGE : 
										    ta_chatOutput.append("["+data.getId()+"]"+data.getMessage()+"\n");
										    tf_chatInput.setText("");
										    
											break;
					case Data.CHAT_WHISPER : 
											ta_chatOutput.append("["+data.getId()+"](귓말)"+data.getMessage()+"\n");
											break;
					case Data.Log_ALL : 
										content.clear();
										for(Log l : data.getLog())
										{
											/*logContent.addElement(String.format
											 * ("%c반   %s   %s   %-20s   %s   %c   %-30s   %ty\n", 
													l.getClass_name(), l.getName(), l.getAdmin(), 
													l.getGrants(), l.getAction(), l.getResult(), 
													l.getLogs(), l.getDates()));*/
											logContent.addElement(l.toString());
										}	
										list.setModel(logContent);
										break;
										
				}	
			} 
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
		}	
			
	}	
}
