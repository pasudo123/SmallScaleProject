package com.pasudo.database;

public enum EnumUserAccount {
	
	// [ORACLE]
	ORACLE{
		@Override
		public void setClassForName(){
			classForName = "oracle.jdbc.driver.OracleDriver";
		}
	},
	ORACLE_LOCAL{
		@Override
		public void setUserAccount(){
			id = "double";
			pw = "doublepass";
			url = "jdbc:oracle:thin:@localhost:1521:xe";
		}
	},
	ORACLE_REMOTE {
		@Override
		public void setUserAccount(){
			id = "scott";
			pw = "tiger2016";
			url = "jdbc:oracle:thin:@//10.1.51.33:1521/ASPDB3";
		}
	},
	
	
	// [MYSQL]
	MYSQL{
		@Override
		public void setClassForName(){
			classForName = "com.mysql.jdbc.Driver";
		}
	},
	MYSQL_LOCAL {
		@Override
		public void setUserAccount(){
			id = "doubler";
			pw = "doublerpass";
			db = "daum";
			url = "jdbc:mysql://localhost:3306/" + getDB();
			mysqlBatchUrl = "jdbc:mysql://localhost:3306/" + getDB() + "?" + getMysqlBatchProperties();
		}
		
		/**
		 * 
		 * [ MySQL 배치 처리 관련 참고 URL ]
		 * 
		 * reference : https://www.journaldev.com/2494/jdbc-batch-insert-update-mysql-oracle
		 * 
		 * **/
	};
	
	private static String id;
	private static String pw;
	private static String db;
	private static String url;
	private static String mysqlBatchUrl;
	private static String classForName;
	private static String mysqlBatchProperties = "rewriteBatchedStatements=true";
	
	/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	 * 
	 * abstract 로 선언하지 않는다.
	 * abstract 로 선언하게 된다면, 해당 열거형 모두 오버라이딩 해야하는 불편함 발생
	 * 일반 메소드 선언하고 이후에 필요한 메소드들만 해당 열거형에 내부에 오버라이딩
	 * 
	 *ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ**/
	public void setUserAccount(){}
	public void setClassForName(){}
	
	// 열거형 내부에 오버라이딩 되었고 이후에 다른 클래스에서 이용하기 위해서 public 선언
	public String getID(){ return id; }
	public String getPW(){ return pw; }
	public String getURL(){ return url; }
	public String getMysqlBatchUrl() { return mysqlBatchUrl; }
	public String getDB() { return db; }
	public String getMysqlBatchProperties() { return mysqlBatchProperties; }
	
	// private 인 이유는, 해당 열거형 내부 메소드에서만 이용한다.
	public String getClassForName() { return classForName; }
}
