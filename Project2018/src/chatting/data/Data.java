package chatting.data;


import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTree;

public class Data implements Serializable 
{
	private String id; 		 //사용자 로그인 
	private String message;  //각종 메세지(대화내용, 로그인/로그아웃 메세지 등)
	private String targetId; //귓말 수신자 ID
	private int status; 	 //Data 객체 처리 방식을 구분할 값
	private Vector<String> userList; //서버에 접속해 있는 사용자 ID목록
	private JTree jtree;
	private ArrayList<Log> log;
	private File [] file;
	
	public static final int CHAT_LOGIN = 1;   //로그인
	public static final int CHAT_MESSAGE = 2; //일반 대화
	public static final int CHAT_WHISPER = 3; //귓말 대화
	public static final int CHAT_LOGOUT = 4;  //로그아웃
	public static final int CHAT_TREE = 5;		// 로그아웃
	public static final int FILE_DELETE = 6;		// 로그아웃
	public static final int FILE_CREATE = 7;		// 로그아웃
	public static final int Log_ALL = 8;
	public static final int FILE_REQ = 9; //파일요청
	public static final int FILE_ACCEPT = 10; //파일받기
	public static final int FILE_ACCESS = 11; //파일시작-처음파일경로를 받기위해
	public static final int FILE_DOWN = 12;
	public static final int FILE_UP=13;
	
	public Data(String id, String message, String targetId, int status) 
	{
		super();
		this.id = id;
		this.message = message;
		this.targetId = targetId;
		this.status = status;
	}
	
	public JTree getJtree() {
		return jtree;
	}

	public void setJtree(JTree jtree) {
		this.jtree = jtree;
	}

	public ArrayList<Log> getLog() {
		return log;
	}

	public void setLog(ArrayList<Log> log) {
		this.log = log;
	}

	public Data(String id, String message, int status) 
	{
		super();
		this.id = id;
		this.message = message;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Vector<String> getUserList() {
		return userList;
	}

	public void setUserList(Vector<String> userList) {
		this.userList = userList;
	}

	public File[] getFile() {
		return file;
	}

	public void setFile(File[] file) {
		this.file = file;
	}
	
	
	
	
	
	

}	
