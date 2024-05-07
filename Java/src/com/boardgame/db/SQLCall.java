package com.boardgame.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.internal.OracleTypes;

public class SQLCall {
	
	private String 				callSQL;
	private Connection			con;
	private CallableStatement 	callableStatement;

	public SQLCall(String call) {
		this.callSQL = call;
	}
	
	public void setCallback(ResultSetCallback callback) {
		try {
			this.con = DBConnection.getConnection();
			this.callableStatement = this.con.prepareCall(this.callSQL);
			this.callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			
			try {
				this.callableStatement.execute();
				ResultSet resultSet = (ResultSet) this.callableStatement.getObject(1);
				callback.processResultSet(resultSet);
			} catch (SQLException e) {
				System.out.println("프로시저에서 에러 발생!");
				System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			}
			
		} catch (Exception e) {
		}
	}

}
