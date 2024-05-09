package com.boardgame.db;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.internal.OracleTypes;

public class GenrePack {
	
    public static ResultSet viewGenre() {
    	String procedure = "{ call genre_pack.view_genre(?) }";
        ResultSet resultSet = null;

        try {
            CallableStatement callableStatement = DBConnection.getConnection().prepareCall(procedure);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(1);
        } catch (SQLException e) {
            System.out.println("프로시저에서 에러 발생!");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        
        return resultSet;
    }
    
    public static ResultSet searchGenre(String name) {
    	String procedure = "{ call genre_pack.search_genre(?, ?) }";
        ResultSet resultSet = null;

        try {
            CallableStatement callableStatement = DBConnection.getConnection().prepareCall(procedure);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.setString(2, name);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(1);
        } catch (SQLException e) {
            System.out.println("프로시저에서 에러 발생!");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        
        return resultSet;
    }
    
    public static void addGenre(int board_id, String genre) {
    	String procedure = "{ call genre_pack.add_genre(?, ?) }";

        try {
            CallableStatement callableStatement = DBConnection.getConnection().prepareCall(procedure);
            callableStatement.setInt(2, board_id);
            callableStatement.setString(1, genre);
            callableStatement.execute();
        } catch (SQLException e) {
            System.out.println("프로시저에서 에러 발생!");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        
    }
    
    public static void createGenre(String genre) {
    	String procedure = "{ call genre_pack.create_genre(?) }";

        try {
            CallableStatement callableStatement = DBConnection.getConnection().prepareCall(procedure);
            callableStatement.setString(1, genre);
            callableStatement.execute();
        } catch (SQLException e) {
            System.out.println("프로시저에서 에러 발생!");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
    
    public static void deleteGenre(String genre) {
    	String procedure = "{ call genre_pack.delete_genre(?) }";

        try {
            CallableStatement callableStatement = DBConnection.getConnection().prepareCall(procedure);
            callableStatement.setString(1, genre);
            callableStatement.execute();
        } catch (SQLException e) {
            System.out.println("프로시저에서 에러 발생!");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
    
    public static ResultSet getGenreByGame(int board_id) {
    	String procedure = "{ call genre_pack.get_genre_by_game(?, ?) }";
    	 ResultSet resultSet = null;
        try {
            CallableStatement callableStatement = DBConnection.getConnection().prepareCall(procedure);
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.setInt(2, board_id);
            callableStatement.execute();
            resultSet = (ResultSet) callableStatement.getObject(1);
        } catch (SQLException e) {
            System.out.println("프로시저에서 에러 발생!");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return resultSet;
    }
    
    public static void deleteGenreByGame(int board_id, String genre) {
    	String procedure = "{ call genre_pack.delete_genre_by_game(?, ?) }";

        try {
            CallableStatement callableStatement = DBConnection.getConnection().prepareCall(procedure);
            callableStatement.setInt(1, board_id);
            callableStatement.setString(2, genre);
            callableStatement.execute();
        } catch (SQLException e) {
            System.out.println("프로시저에서 에러 발생!");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
 }

