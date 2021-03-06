package edu.doubler.preprocess.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnector {
	public static Connection getConnection() {
		Connection conn = null;

		try {
			String user = "doubler";
			String pw = "doublerpass";
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";

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
}
