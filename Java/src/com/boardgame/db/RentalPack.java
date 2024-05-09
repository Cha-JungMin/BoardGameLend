package com.boardgame.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.internal.OracleTypes;

public class RentalPack {
	private static Connection con = DBConnection.getConnection();
	
	public static ResultSet getRentalHistory(String start_date, String end_date, String username, String title) {
    	
		if (start_date == null) {start_date = "2000/01/01";}
		if (end_date == null) {end_date = "3000/01/01";}
		
		String procedure = "{ call rental_pack.get_rental_history(?, ?, ?, ?, ?) }";
        ResultSet resultSet = null;

        try {
            CallableStatement callableStatement = con.prepareCall(procedure);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.setString(2, start_date);
            callableStatement.setString(3, end_date);
            callableStatement.setString(4, username);
            callableStatement.setString(5, title);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(1);
        } catch (SQLException e) {
            System.out.println("프로시저에서 에러 발생!");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        
        return resultSet;
    }
}
