package chatting.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.Vector;

import chatting.data.Data;

public class FTPServer {
		private DataInputStream dis;
		private DataOutputStream dos;
		private boolean exit;
		
		
		public FTPServer() {
			try {
				ServerSocket server = new ServerSocket(8888);
				
				System.out.println("ftp Server Start!");
				
				while( true )
				{
					Socket client = server.accept();
					System.out.println(client.getInetAddress().getHostAddress() + "유저가 접속 했습니다.");
					dis = new DataInputStream(client.getInputStream());
					dos = new DataOutputStream(client.getOutputStream());
					
					FTPthread cst = new FTPthread(dis, dos);
					Thread t = new Thread(cst);
					t.start();
				
				} // while
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		/*
		@Override
		public void run() {
			while( !exit )
			{
				try {
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
			
//			for( Map.Entry<String, ObjectOutputStream> s : userList.entrySet() )
//			{
//				ObjectOutputStream oos = s.getValue();
//				oos.writeObject(data);		
//			}
			
		}
		*/
		
		public static void main(String[] args) {
			new FTPServer();
		}

}
