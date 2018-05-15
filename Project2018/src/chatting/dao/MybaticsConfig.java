package chatting.dao;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import chatting.data.Log;

public class MybaticsConfig {
	private static SqlSessionFactory sqlSessionFactory;
	
	static {
		String resource = "mybatics-config.xml";
		
		try {
			Reader reader = Resources.getResourceAsReader(resource);
			
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			reader.close();
			
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static SqlSessionFactory getSqlSessionFactory()
	{
		return sqlSessionFactory;
	}
}
