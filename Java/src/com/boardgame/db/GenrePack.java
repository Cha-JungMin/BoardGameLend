package com.boardgame.db;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.internal.OracleTypes;

public class GenrePack {
	
    public static ResultSet viewGenre(Connection con) {
    	String procedure = "{ call genre_pack.view_genre(?) }";
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
    
    public static ResultSet searchGenre(Connection con, String name) {
    	String procedure = "{ call genre_pack.search_genre(?, ?) }";
        ResultSet resultSet = null;

        try {
            CallableStatement callableStatement = con.prepareCall(procedure);
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
    
    public static void addGenre(Connection con, int board_id, String genre) {
    	String procedure = "{ call genre_pack.add_genre(?, ?) }";

        try {
            CallableStatement callableStatement = con.prepareCall(procedure);
            callableStatement.setInt(2, board_id);
            callableStatement.setString(1, genre);
            callableStatement.execute();
        } catch (SQLException e) {
            System.out.println("프로시저에서 에러 발생!");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        
    }
    
    public static void createGenre(Connection con, String genre) {
    	String procedure = "{ call genre_pack.create_genre(?) }";

        try {
            CallableStatement callableStatement = con.prepareCall(procedure);
            callableStatement.setString(1, genre);
            callableStatement.execute();
        } catch (SQLException e) {
            System.out.println("프로시저에서 에러 발생!");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
    
    public static void deleteGenre(Connection con, String genre) {
    	String procedure = "{ call genre_pack.delete_genre(?) }";

        try {
            CallableStatement callableStatement = con.prepareCall(procedure);
            callableStatement.setString(1, genre);
            callableStatement.execute();
        } catch (SQLException e) {
            System.out.println("프로시저에서 에러 발생!");
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
 }

