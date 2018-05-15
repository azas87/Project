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
		
		public static void main(String[] args) {
			new FTPServer();
		}

}
