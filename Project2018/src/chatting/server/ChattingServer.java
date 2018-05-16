package chatting.server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChattingServer implements Runnable {
	
	private int port;
	
	public ChattingServer(int port) {
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(port);
			
			System.out.println("Chatting Server Start!");
			
			Thread fileServer = new Thread(new FileServer(port+1));
			fileServer.start();
			
			while( true )
			{
				Socket client = server.accept();
				System.out.println(client.getInetAddress().getHostAddress() + "유저가 접속 했습니다.");
				ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
				ChattingServerThread cst = new ChattingServerThread(ois, oos, port+1);
				Thread t = new Thread(cst);
				t.start();
			} // while
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void exit() {
		System.exit(0);
	}
	
}
