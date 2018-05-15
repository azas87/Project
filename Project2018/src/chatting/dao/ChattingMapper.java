package chatting.dao;

import java.util.ArrayList;

import chatting.data.Log;

public interface ChattingMapper {
	
	public ArrayList<Log> listLog();
	public int insertLog(Log l);
}

