package chatting.server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import chatting.dao.ChattingDAO;
import chatting.data.Data;

public class ChattingServerThread implements Runnable {

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private boolean exit;
	private Data data;
	//private String ShareFolderPath = "C:\\Program Files";
	private String ShareFolderPath = "D:\\IT_MASTER\\";
	
	private static HashMap<String, ObjectOutputStream> userList = new HashMap<>();
	private File file;
	
	public ChattingServerThread(ObjectInputStream ois, ObjectOutputStream oos) {
		this.ois = ois;
		this.oos = oos;
	}

	@Override
	public void run() {
		while( !exit )
		{
			try 
			{
				data = (Data) ois.readObject();
				int status = data.getStatus();
				
				switch( status )
				{
					case Data.CHAT_LOGIN:
						userList.put(data.getId(), oos);
						Set<String> idList = userList.keySet();
						Vector<String> list = new Vector<>();
						for( String id : idList ) {
							list.add(id);
						}
						data.setUserList(list);
						broadCasting();
						break;
					case Data.CHAT_MESSAGE:
						broadCasting();
						break;
					case Data.CHAT_WHISPER:
						String targetId = data.getTargetId();
						userList.get(targetId).writeObject(data);
						break;
					case Data.CHAT_LOGOUT:
						userList.remove(data.getId());
						Set<String> idList2 = userList.keySet();
						Vector<String> list2 = new Vector<>();
						for( String id : idList2 ) {
							list2.add(id);
						}
						data.setUserList(list2);
						broadCasting();
						break;
					
				}
			} catch (ClassNotFoundException e) {
				System.out.println("000111100111");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("00000000000");
				//e.printStackTrace();
				userList.remove(data.getId());
				exit = true;
			}  
		}// while
		closeAll();
		System.out.println("chattingserver 스레드 종료");
	} // run()
	

	
	// 서버에 전달된 Data 객체를 접속한 모든 사용자에게 전파한다.
	public void broadCasting() {
		Collection<ObjectOutputStream> oosList = userList.values();
		
		for( ObjectOutputStream oos : oosList )
		{
			try {
				oos.writeObject(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public void closeAll(){
		System.out.println("모든 자원 종료");
		try { if(ois != null) {oos.close(); }} catch (IOException e) {}
		try { if(oos != null) {ois.close();}} catch (IOException e) {}
		
	}
}
