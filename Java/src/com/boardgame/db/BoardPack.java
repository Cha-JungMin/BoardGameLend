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
    
    public static ResultSet getGenre(Connection con) {
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
 }

