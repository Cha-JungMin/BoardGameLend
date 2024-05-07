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
	
	public SQLCall(String call, CalStatementSetCallback callback) {
		try {
			this.callSQL = call;
			this.con = DBConnection.getConnection();
			this.callableStatement = this.con.prepareCall(this.callSQL);
			callback.processCalStatementSet(this.callableStatement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
