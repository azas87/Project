package chatting.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import chatting.data.Data;

public class FileServerThread implements Runnable {

	private BufferedInputStream bisFile;
	private BufferedOutputStream bosFile;
	private DataInputStream disSocket;
	private DataOutputStream dosSocket;
	private int upDownStatus;
	private String path;
	private File file;
	private long fileLength;

	
	public FileServerThread(DataInputStream disSocket, DataOutputStream dosSocket) {
		this.disSocket = disSocket;
		this.dosSocket = dosSocket;
	}

	@Override
	public void run() {
		try {
			upDownStatus = disSocket.readInt();		// Status �� �ް�
			
			if( upDownStatus == Data.FILE_DOWN )
			{
				path = disSocket.readUTF();			// ���� ��� �ް�
				file = new File(path);
				fileLength = file.length();			
				dosSocket.writeLong(fileLength);	// ���� ũ�� ������
				if( file.exists() )
				{
					dosSocket.writeUTF("Y");		// ���� ������ Y �� ������
					if( disSocket.readUTF().equals("Y") )	// Ȯ�� �� ������
						fileDown();					// ���� ���� �޼��� ȣ��
				}
				else
					dosSocket.writeUTF("N");		// ���� ������ N �� ������ ��
			}
			else
			{
				fileLength = disSocket.readLong();	// ���� �뷮 Ȯ���ϰ� (������ ����)
				dosSocket.writeUTF("Y");			// Ȯ�� �� ������
				path = disSocket.readUTF();			// ���� ��� �ް�
				file = new File(path);
				if( file.exists() ) {				// ���͸��� �߰��� ���� �ǰų� �̸� ������� ��츦 ���
					dosSocket.writeUTF("Y");		
					fileUp();						// ���� ���ε� �޼��� ȣ��
					dosSocket.writeUTF("Y");		// ���ε尡 �Ϸ�Ǹ� Ȯ�� �޽��� ����
				}
				else
					dosSocket.writeUTF("N");		// ���͸��� ���ų� �̸� ���� �� 
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void fileUp() {
		try {
			bosFile = new BufferedOutputStream(new FileOutputStream(file));
			
			byte[] b = new byte[4096];
			int c = 0;
			while( (c=disSocket.read(b)) != -1 )
				bosFile.write(b, 0, c);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
	}
	
	public void fileDown() {
		try {
			bisFile = new BufferedInputStream(new FileInputStream(file));
			
			byte[] b = new byte[4096];
			int c = 0;
			while( (c=bisFile.read(b)) != -1 )
				dosSocket.write(b, 0, c);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
	}
	
	public void close() {
		try {
			if( bosFile != null ) bosFile.close();
			if( bisFile != null ) bisFile.close();
			if( disSocket != null ) disSocket.close();
			if( dosSocket != null ) dosSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
