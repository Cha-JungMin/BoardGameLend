package com.boardgame.db;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.internal.OracleTypes;

public class BoardPack {
	
    public static ResultSet getBoardGameStatement(Connection con) {
        String procedure = "{ call board_pack.board_game_statement(?) }";
        ResultSet resultSet = null;

        try {
            CallableStatement callableStatement = con.prepareCall(procedure);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(1);
        } catch (SQLException e) {
            System.out.println("프로시저에서 에러 발생!");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

        return resultSet;
    }
    
    public static void createBoardGame(Connection con, String title, String description, 
    		int min_people, int max_people, int min_playtime, int max_playtime, int rental_fee, int copy) {
    	
    	String procedure = "{ call board_pack.create_boardgame(?, ?, ?, ?, ?, ?, ?, ?)}";
    	
    	 try {
             CallableStatement callableStatement = con.prepareCall(procedure);
             callableStatement.setString(1, title);
             callableStatement.setString(2, description);
             callableStatement.setInt(3, min_people);
             callableStatement.setInt(4, max_people);
             callableStatement.setInt(5, min_playtime);
             callableStatement.setInt(6, max_playtime);
             callableStatement.setInt(7, rental_fee);
             callableStatement.setInt(8, copy);
             callableStatement.execute();
         } catch (SQLException e) {
             System.out.println("프로시저에서 에러 발생!");
             System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
         }
    }
    
    public static void updateBoardGame(Connection con, int board_id, String title, String description, 
    		int min_people, int max_people, int min_playtime, int max_playtime, int rental_fee, int copy) {
    	
    	String procedure = "{ call board_pack.update_boardgame(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    	 try {
             CallableStatement callableStatement = con.prepareCall(procedure);
             callableStatement.setInt(1, board_id);
             callableStatement.setString(2, title);
             callableStatement.setString(3, description);
             callableStatement.setInt(4, min_people);
             callableStatement.setInt(5, max_people);
             callableStatement.setInt(6, min_playtime);
             callableStatement.setInt(7, max_playtime);
             callableStatement.setInt(8, rental_fee);
             callableStatement.setInt(9, copy);
             callableStatement.execute();
         } catch (SQLException e) {
             System.out.println("프로시저에서 에러 발생!");
             System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
         }
    }
    
    public static void deleteBoardGame(Connection con, int board_id) {
    	
    	String procedure = "{ call board_pack.delete_boardgame(?)}";
    	
    	 try {
             CallableStatement callableStatement = con.prepareCall(procedure);
             callableStatement.setInt(1, board_id);
             callableStatement.execute();
         } catch (SQLException e) {
             System.out.println("프로시저에서 에러 발생!");
             System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
         }
    }
    
    public static ResultSet SearchBoardGame(Connection con, String title, String genre, 
    		int min_people, int max_people, int min_rental_fee, int max_rental_fee) {
    	ResultSet resultSet = null;
    	String procedure = "{ call board_pack.search_boardgame(?, ?, ?, ?, ?, ?, ?)}";
    	
    	 try {
             CallableStatement callableStatement = con.prepareCall(procedure);
             callableStatement.setString(1, title);
             callableStatement.setString(2, genre);
             callableStatement.setInt(3, min_people);
             callableStatement.setInt(4, max_people);
             callableStatement.setInt(5, min_rental_fee);
             callableStatement.setInt(6, max_rental_fee);
             callableStatement.registerOutParameter(7, OracleTypes.CURSOR);

             callableStatement.execute();
             resultSet = (ResultSet) callableStatement.getObject(7);
         } catch (SQLException e) {
             System.out.println("프로시저에서 에러 발생!");
             System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
         }
    	 
    	 return resultSet;
    }
 }

