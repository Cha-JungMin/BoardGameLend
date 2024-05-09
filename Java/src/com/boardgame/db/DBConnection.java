package com.boardgame.db;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	private static Connection conn;
	
	private DBConnection() {}
	
	static {
		Properties properties = new Properties();
		Reader reader;
		
		try {
			reader = new FileReader("lib/oracle.properties");
			properties.load(reader);
			
		} catch (FileNotFoundException e) {
			System.out.println("예외: 해당 경로에 파일을 찾을 수 없습니다.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String driverName = properties.getProperty("driver");
		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("연결됨");
			
		} catch (ClassNotFoundException e) {
			System.out.println("예외: 드라이버 로드 실패: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e ) {
			System.out.println("예외: 연결 실패: " + e.getMessage());
			e.printStackTrace();
		}
	
	}
	
	public static Connection getConnection() {
		return conn;
	}
}
