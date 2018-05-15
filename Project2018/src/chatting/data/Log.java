package chatting.data;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Log implements Serializable{
	private String name;
	private char class_name;
	private char admin;
	private int grants;
	private int action;
	private int stdNo;
	private int logNo;
	private char result;
	private String logs;
	private Date dates;
	private Date time;
	
	private String grantList[] = {"ACC","UP","DN","NEW", "DEL"};
	private String actionList[] = {"해제","접속","생성","삭제", "업로드", "다운로드"};
	@Override
	public String toString() 
	{
		String admin = (this.admin=='o') ? "관리자" : "사용자"; 
		String grant_list="";
		int mask = 1;
		for(int i = 0; i < 5; i++ )
		{
			if( (grants & mask) == mask )
			{
				grant_list+=grantList[i]+"|";
				
			}
			mask <<= 1;
		}
		
		return String.format("%c반      %-7s     %-4s     %-20s    %s     %c    %-30s    y\n",
				class_name, name, admin, grant_list, actionList[action], result, logs, dates );
		

	}
	

	/*public static void main(String[] args) 
	{
		while(true)
		{	
			HashMap<Character, String> hm = new HashMap<Character, String>();
			String grantList[] = {"ACC","UP","DN","NEW", "DEL"};
			Character [] c = {'1', '2', '3', '4', '5'};
			for(int i = 0 ; i < 5 ; i++)
			{
				hm.put(c[i], grantList[i]);
			}	
			
			
			for(int i = 0 ; i < al.size() ; i++)
			{
				str += hm.get(al.get(i)) + "|";
			}
			
			/*
			Scanner scan = new Scanner(System.in);
			System.out.println("ACC:1, UP:2, DN:3, NEW:4, DEL:5");
			System.out.print("입력 : ");
			String user = scan.next();
			
			ArrayList<Character> al = new ArrayList<Character>();
			
			for(int i = 0 ; i < user.length() ; i++)
			{
				al.add(user.charAt(i));
			}
			
			
			String str = "";
			for(int i = 0 ; i < al.size() ; i++)
			{
				str += hm.get(al.get(i)) + "|";
			}
			System.out.println(str);
			
		}
	}*/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getClass_name() {
		return class_name;
	}
	public void setClass_name(char class_name) {
		this.class_name = class_name;
	}
	public char getAdmin() {
		return admin;
	}
	public void setAdmin(char admin) {
		this.admin = admin;
	}
	public int getGrants() {
		return grants;
	}
	public void setGrants(int grants) {
		this.grants = grants;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public int getStdNo() {
		return stdNo;
	}
	public void setStdNo(int stdNo) {
		this.stdNo = stdNo;
	}
	public int getLogNo() {
		return logNo;
	}
	public void setLogNo(int logNo) {
		this.logNo = logNo;
	}
	public char getResult() {
		return result;
	}
	public void setResult(char result) {
		this.result = result;
	}
	public String getLogs() {
		return logs;
	}
	public void setLogs(String logs) {
		this.logs = logs;
	}
	public Date getDates() {
		return dates;
	}
	public void setDates(Date dates) {
		this.dates = dates;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String[] getGrantList() {
		return grantList;
	}
	public void setGrantList(String[] grantList) {
		this.grantList = grantList;
	}
	public String[] getActionList() {
		return actionList;
	}
	public void setActionList(String[] actionList) {
		this.actionList = actionList;
	}
	
	
	

	
	
	
}
