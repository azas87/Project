package chatting.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import chatting.data.Data;

public class FileServer implements Runnable {
	
	private int port;
	
	public FileServer(int port) {
		this.port = port;
	}
	
	@Override
	public void run() {
		ServerSocket serverSocket = null;
		Socket fileClient = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		
		try {
			serverSocket = new ServerSocket(port);
			
			System.out.println("File Server Start!");
			
			while( true )
			{
				fileClient = serverSocket.accept();
				dis = new DataInputStream(fileClient.getInputStream());
				dos = new DataOutputStream(fileClient.getOutputStream());
				Thread fileThread = new Thread(new FileServerThread(dis, dos));
				fileThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
