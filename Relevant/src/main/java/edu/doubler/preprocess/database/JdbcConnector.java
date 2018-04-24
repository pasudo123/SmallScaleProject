package edu.doubler.preprocess.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import edu.doubler.preprocess.person.MusicMember;

public class JdbcConnector {
	private Connection conn;
	
	private Connection getConnection() {
		Connection conn = null;

		try {
			String user = "userName";
			String pw = "userPassword";
			String url = "URL";

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);
			System.out.println("Database에 연결되었습니다.\n");
		}

		catch (ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패 :" + cnfe.toString());
		}

		catch (SQLException sqle) {
			System.out.println("DB 접속실패 : " + sqle.toString());
		}

		catch (Exception e) {
			System.out.println("Unkonwn error");
			e.printStackTrace();
		}
		return conn;
	}
	
	public void initConnection(){
		this.conn = getConnection();
	}
	
	public void insertDB(MusicMember musicMember){
		
	}
}
