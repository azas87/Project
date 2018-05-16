package chatting.server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import chatting.dao.ChattingDAO;
import chatting.data.Data;

public class ChattingServerThread implements Runnable {

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private boolean exit;
	private Data data;
	private int port;
	private static HashMap<String, ObjectOutputStream> userList = new HashMap<>();
	private File path;
	private String base_Path = "D:\\IT_Master";
//	private File basePath = new File(base_Path);
	
	public ChattingServerThread(ObjectInputStream ois, ObjectOutputStream oos, int port) {
		this.ois = ois;
		this.oos = oos;
		this.port = port;
	}

	@Override
	public void run() {
		while( !exit )
		{
			try {
				data = (Data) ois.readObject();
				int status = data.getStatus();
				String targetId;
				
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
						targetId = data.getTargetId();
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
					
					case Data.Log_ALL:
						ChattingDAO d = new ChattingDAO();
						data.setLog(d.listLogs());
						broadCasting();
						
					case Data.FILE_UP:
						data.setTargetId(port+"");
						targetId = data.getId();
						userList.get(targetId).writeObject(data);
						break;
						
					case Data.FILE_DOWN:
						path = new File(data.getMessage());
						if( path.exists() )
						{
							data.setMessage("Y");
							data.setTargetId(port+"");
						}
						targetId = data.getId();
						userList.get(targetId).writeObject(data);
						break;
						
					case Data.FILE_ACCESS:
						path = new File(base_Path);
						data.setFile(path.listFiles());
						data.setMessage(base_Path);
						broadCasting();
						break;
						
					case Data.FILE_REQ:
						path = new File(data.getMessage());
						data.setStatus(Data.FILE_ACCEPT);
						System.out.println("1111");
						data.setFile(path.listFiles());
						broadCasting();
						//targetId = data.getId();
						//userList.get(targetId).writeObject(data);
						break;
						
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				exit = true;
			}
		} // while
		
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

	
}
