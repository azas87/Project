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
			upDownStatus = disSocket.readInt();		// Status 값 받고
			
			if( upDownStatus == Data.FILE_DOWN )
			{
				path = disSocket.readUTF();			// 파일 경로 받고
				file = new File(path);
				fileLength = file.length();			
				dosSocket.writeLong(fileLength);	// 파일 크기 보내고
				if( file.exists() )
				{
					dosSocket.writeUTF("Y");		// 파일 있으면 Y 값 보내고
					if( disSocket.readUTF().equals("Y") )	// 확인 값 받으면
						fileDown();					// 파일 전송 메서드 호출
				}
				else
					dosSocket.writeUTF("N");		// 파일 없으면 N 값 보내고 끝
			}
			else
			{
				fileLength = disSocket.readLong();	// 서버 용량 확인하고 (구현은 안함)
				dosSocket.writeUTF("Y");			// 확인 값 보내고
				path = disSocket.readUTF();			// 파일 경로 받고
				file = new File(path);
				if( file.exists() ) {				// 디렉터리가 중간에 삭제 되거나 이름 변경됐을 경우를 대비
					dosSocket.writeUTF("Y");		
					fileUp();						// 파일 업로드 메서드 호출
					dosSocket.writeUTF("Y");		// 업로드가 완료되면 확인 메시지 전송
				}
				else
					dosSocket.writeUTF("N");		// 디렉터리가 없거나 이름 변경 시 
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
