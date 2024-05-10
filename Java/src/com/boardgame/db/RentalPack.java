package com.boardgame.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.internal.OracleTypes;

public class RentalPack {
	private static Connection con = DBConnection.getConnection();
	
	public static ResultSet getRentalHistory(String start_date, String end_date, String username, String title) {
    	
		
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
	
	public static void updateRentalStatement(int rental_id) {
	    	
		String procedure = "{ call rental_pack.update_rental_statement(?) }";
        try {
            CallableStatement callableStatement = con.prepareCall(procedure);
            callableStatement.setInt(1, rental_id);
            callableStatement.execute();
        } catch (SQLException e) {
            System.out.println("프로시저에서 에러 발생!");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
	
	public static void loadRentalStatement() {
    	
		String procedure = "{ call rental_pack.load_rental_statement() }";
        try {
            CallableStatement callableStatement = con.prepareCall(procedure);
            callableStatement.execute();
        } catch (SQLException e) {
            System.out.println("프로시저에서 에러 발생!");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
	
	public static ResultSet getRentalStatisticByGame(String start_date, String end_date) {
    	
		String procedure = "{ call rental_pack.get_rental_statistic_by_game(?, ?, ?) }";
        ResultSet resultSet = null;
        try {
            CallableStatement callableStatement = con.prepareCall(procedure);
            callableStatement.setString(1, start_date);
            callableStatement.setString(2, end_date);
            callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(3);
        } catch (SQLException e) {
            System.out.println("프로시저에서 에러 발생!");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return resultSet;
    }
	
	public static ResultSet getRentalStatisticByMember(String start_date, String end_date) {
    	
		String procedure = "{ call rental_pack.get_rental_statistic_by_member(?, ?, ?) }";
        ResultSet resultSet = null;
        try {
            CallableStatement callableStatement = con.prepareCall(procedure);
            callableStatement.setString(1, start_date);
            callableStatement.setString(2, end_date);
            callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(3);
        } catch (SQLException e) {
            System.out.println("프로시저에서 에러 발생!");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return resultSet;
    }
}
