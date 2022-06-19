package com.treefactory.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {

	private static String driver = "oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String uid = "treefactory";
	private static String upw = "treefactory";

	private static boolean checkDriver = false;

	// static 블록에서는 throws 메서드가 없어서 못함
	// db클래스가 호출되면 driver 확인 바로한다
	static {

		// oracle Driver 클래스 확인
		try {
			//드라이버 없으면 catch로 바로 넘어가서 if문 안써도 된다
			Class.forName(driver);
			checkDriver = true;
			System.out.println("DB.static 블록 - 드라이버 확인 성공 완료-----");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

	}

	public static Connection getConnection() throws Exception {
		if (checkDriver)
			return DriverManager.getConnection(url, uid, upw);

		throw new Exception("DB를 처리할 프로그램(드라이버)이 존재하지 않습니다.");
	}
	
	public static void close(Connection con, PreparedStatement pstmt) throws Exception {
		if(con != null) con.close();
		if(pstmt != null) pstmt.close();
	}
	public static void close(Connection con, PreparedStatement pstmt, ResultSet rs) throws Exception {
		close(con, pstmt);
		if(rs != null) rs.close();
	}
		
		
	}
