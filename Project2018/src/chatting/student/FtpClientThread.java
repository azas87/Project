package chatting.student;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;



public class FtpClientThread extends JFrame implements Runnable{
	
	private DataInputStream dis;
	private DataOutputStream dos;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	private String uufName;
	private String SourceFielPath;
	private int mode;
	private String msg;
	public static boolean isCancel;
	private File file;
	
	public FtpClientThread(DataInputStream dis, DataOutputStream dos, int mode, String SourceFielPath) {
		this.dis = dis;
		this.dos = dos;
		this.mode = mode;
		this.SourceFielPath = SourceFielPath;
	}
	
	@Override
	public void run() {
		try 
		{
			if(mode == 0)
			{
				dos.writeUTF("STC");
				dos.flush();
				dos.writeUTF(SourceFielPath);
				dos.flush();
				
				msg = dis.readUTF();
				if(msg.equals("YES"))
				{
					Long size = Long.parseLong(dis.readUTF());
					String[] directoryName = SourceFielPath.split("\\\\");
					System.out.println(directoryName[directoryName.length -1]);
					uufName = directoryName[directoryName.length -1];
					
					JFileChooser save = new JFileChooser();
					save.setSelectedFile(new File(uufName));
					
					// 원래는 selectfile이 null 나오겠지만.. 내가 파일명 때문에 setSelectedFile 해서 구별이 안 됨
					if(save.showSaveDialog(this) == JFileChooser.APPROVE_OPTION )
					{
						/*File selectFile = save.getSelectedFile();
						file = new File(selectFile.getAbsolutePath());*/

						file = save.getSelectedFile();
						
						fos = new FileOutputStream(file);
						System.out.println("Sever to Client. Start");
						dos.writeUTF("START");
						isCancel = false;
						//ChattingMain.progressbar.setValue(0);
						byte[] buf = new byte[4096];
						long total = 0;
						int read = 0, per = 0;
						while((read = dis.read(buf)) != -1)
						{
							total += read;
							per = (int)(total / (size/100L));
							//ChattingMain.progressbar.setValue(per);
							//ChattingMain.lbl_per.setText(per+"%");
							
							fos.write(buf, 0, read);
							fos.flush();
							
							if(isCancel) break;
						}
						
						if(isCancel)
						{
							System.out.println("다운로드가 취소 되었습니다.");
						}
						else
						{
							System.out.println("[System] 다운로드를 완료했습니다.");
						}
					}
					else
					{
						dos.writeUTF("GIVE UP");
						System.out.println("저장경로를 지정하지 않고 종료 함.");
					}
				}
				else
				{
					System.out.println("서버에 해당 파일이 파일이 존재하지 않음. 트리 갱신 필요.");
				}
			}
			else
			{
				System.out.println(SourceFielPath);
				String paths[] = SourceFielPath.split("\\|");
					
				file= new File(paths[1]);
				fis = new FileInputStream(file);
				
				dos.writeUTF("CTS");
				
				dos.writeUTF(paths[0]+"\\"+file.getName());
				
				msg = dis.readUTF();
				if(msg.equals("YES"))
				{
					
					byte[] buf = new byte[4096];
					int read = 0;
					long size = file.length();
					BufferedInputStream bis = new BufferedInputStream(fis);
					long total = 0;
					int per = 0;
					isCancel = false;
					//ChattingMain.progressbar.setValue(0);
					while((read = bis.read(buf)) != -1)
					{
						total += read;
						per = (int)(total / (size/100L));
						//ChattingMain.progressbar.setValue(per);
						//ChattingMain.lbl_per.setText(per+"%");
						dos.write(buf, 0, read);
						if(isCancel) break;
					}
					dos.flush();
					System.out.println("파일 전송 완료");
				}
				else
				{
					//용량 부족이나 동일 이름이나 기타 이유로 인한 예외
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally 
		{
			
			//ChattingMain.progressbar.setValue(0);
			//ChattingMain.lbl_per.setText(0+"%");
			
			closeAll();
			
			if(isCancel)
			{
				if(file.exists())
				{
					file.delete();
				}
			}
		}
		System.out.println("ftpclient 스레드 종료");
	}
	
	public void closeAll(){
		System.out.println("모든 자원 종료");
		try { if(fos != null) {fos.close(); }} catch (IOException e) {}
		try { if(fis != null) {fis.close(); }} catch (IOException e) {}
		try { if(dis != null) {dis.close();}} catch (IOException e) {}
		try { if(dos != null) {dos.close(); }} catch (IOException e) {}
	}
}
