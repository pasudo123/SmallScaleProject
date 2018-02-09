package doubler.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
	Connection conn = null;
	
	public static void main(String[]args){
		Main main = new Main();
		
		main.conn = main.getConnection();
		main.setDB(main.conn);
	}
	
	public Connection getConnection(){
		Connection conn = null;
		
		try {
			String user = "pasudo";
			String pw = "pasudopass";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";

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
	
	public void insertDB(Connection conn, int date, String weekDay, int treatment, double lowestTemperature, double diurnalRange, double moisture, int twitter, int news, int sumSource){
		
		String query = "INSERT INTO COLD_TB VALUES(SEQ_ID.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Connection connection = conn;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, date);
			pstmt.setString(2, weekDay);
			pstmt.setInt(3, treatment);
			pstmt.setDouble(4, lowestTemperature);
			pstmt.setDouble(5, diurnalRange);
			pstmt.setDouble(6, moisture);
			pstmt.setInt(7, twitter);
			pstmt.setInt(8, news);
			pstmt.setInt(9, sumSource);
			
			// 데이터 업데이트
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setDB(Connection conn){
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		
		try {
			fis= new FileInputStream("C:\\Users\\Daumsoft\\Desktop\\다음소프트 과제\\3주차 과제\\감기_전국.xlsx");
			workbook = new XSSFWorkbook(fis);
			
			int rowIndex = 0;
			
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			
			for(rowIndex = 1; rowIndex < rows; rowIndex++){
				
				// 행 읽기
				XSSFRow row = sheet.getRow(rowIndex);
				
				// 행 데이터 존재
				if(row != null){
					int date = new Integer(row.getCell(0).getRawValue());					// 일자
					String weekDay = row.getCell(1).getStringCellValue();					// 요일구분
					int treatment = new Integer(row.getCell(2).getRawValue());				// 진료건수
					double lowestTemperature = new Double(row.getCell(3).getRawValue());	// 최저기온
					double diurnalRange = new Double(row.getCell(4).getRawValue());			// 일교차
					double moisture = new Double(row.getCell(5).getRawValue());				// 습도
					int twitter = new Integer(row.getCell(6).getRawValue());				// 트위터
					int news = new Integer(row.getCell(7).getRawValue());					// 뉴스
					int sumSource = new Integer(row.getCell(8).getRawValue());				// 언급량 합계
					
					insertDB(conn, date, weekDay, treatment, lowestTemperature, diurnalRange, moisture, twitter, news, sumSource);
				}
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
