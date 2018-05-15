package chatting.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FTPthread implements Runnable{
	private DataInputStream dis;
	private DataOutputStream dos;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	private String uufName;
	private String FILE_SAVED_PATH;
	private String BASE_PATH = "D:\\IT_MASTER\\";
	
	public FTPthread(DataInputStream dis, DataOutputStream dos) {
		this.dis = dis;
		this.dos = dos;
	}
	
	@Override
	public void run() {
		try {
			
		} finally
		{
			closeAll();
		}		
		System.out.println("ftp server 스레드 종료");
	} // run()
	
	public void closeAll(){
		System.out.println("모든 자원 종료");
		try { if(fos != null) {fos.close(); }} catch (IOException e) {}
		try { if(fis != null) {fis.close(); }} catch (IOException e) {}
		try { if(dis != null) {dis.close();}} catch (IOException e) {}
		try { if(dos != null) {dos.close(); }} catch (IOException e) {}
	}

}
