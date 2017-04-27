package com.wipro.hms.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil 
{
	public static Connection getDBConnection()
	{
		Connection conn = null;
		try
		{
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
			String username = "B44641345065";
			String password = "B44641345065";
			
			conn = DriverManager.getConnection(url,username,password);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}
}
