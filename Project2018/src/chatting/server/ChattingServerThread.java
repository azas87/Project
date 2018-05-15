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
					case Data.CHAT_TREE:
						JTree jt = new JTree(getNode(new File(ShareFolderPath)));
						data.setJtree(jt);
						userList.get(data.getId()).writeObject(data);
						break;
					case Data.FILE_CREATE:
						file = new File(ShareFolderPath+data.getMessage());
						if(!file.exists())
						{
							file.mkdirs();
						}
						break;
					case Data.FILE_DELETE:
						file = new File(ShareFolderPath+data.getMessage());
						if(file.exists())
						{
							if(file.isDirectory())
							{
								delteAll(file);
							}
							
							file.delete();
						}
					case Data.Log_ALL:
						ChattingDAO dao = new ChattingDAO();
						data.setLog(dao.listLogs());
						userList.get(data.getId()).writeObject(data);
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
	
	public void delteAll(File file)
	{
		File list[] = file.listFiles();
		for(File f : list)
		{
			if(f.isDirectory())
			{
				delteAll(f);
			}
			
			f.delete();
		}
	}
	
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
		
//		for( Map.Entry<String, ObjectOutputStream> s : userList.entrySet() )
//		{
//			ObjectOutputStream oos = s.getValue();
//			oos.writeObject(data);		
//		}
		
	}
	
	private DefaultMutableTreeNode getNode(File file)
	{
		DefaultMutableTreeNode sub_root = new DefaultMutableTreeNode(file.getName());
		
		File list[] = file.listFiles();
		DefaultMutableTreeNode temp = null;

		// programfiles//windowsapps 같은 애들이 null 나옴.
		// 관리자 권한이 없어서 못 보는 폴더네...
		if(list == null || list.length == 0)	
		{
			temp = new DefaultMutableTreeNode("");
			sub_root.add(temp);
		}
		else
		{
			ArrayList<File> folderList = new ArrayList<File>();
			ArrayList<File> fileList = new ArrayList<File>();
			
			for(File f : list)
			{
				if(f.isDirectory())
				{
					folderList.add(f);
				}
				else if(f.isFile())
				{
					fileList.add(f);
				}
			}
			
			/*Collections.sort(folderList);
			Collections.sort(fileList);*/
			
			for (int i = 0; i < folderList.size(); ++i) 
			{
				temp = getNode(folderList.get(i));
				sub_root.add(temp);
			}
			
			File tempFile;
			for (int i = 0; i < fileList.size(); ++i) 
			{
				tempFile = fileList.get(i) ;
				temp = new DefaultMutableTreeNode(tempFile.getName());
				sub_root.add(temp);
			}
		}
		
		return sub_root;
	}
	
	public void closeAll(){
		System.out.println("모든 자원 종료");
		try { if(ois != null) {oos.close(); }} catch (IOException e) {}
		try { if(oos != null) {ois.close();}} catch (IOException e) {}
		
	}
}
