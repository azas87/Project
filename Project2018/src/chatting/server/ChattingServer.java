package chatting.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChattingServer 
{
	public ChattingServer()
	{
		try 
		{
			
			ServerSocket server = new ServerSocket(7777);
			System.out.println("Chatting Server Start!!");
			while(true)
			{
	
				Socket client = server.accept();
				System.out.println(client.getInetAddress().getHostAddress() + "유저가 접속 했습니다.");
			
				ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
				
				
				ChattingServerThread cst = new ChattingServerThread(ois, oos);//기다리려고 
				Thread t = new Thread(cst);
				t.start();
				
			}	
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void main(String[] args) 
	{
		new ChattingServer();
	}
	
}
