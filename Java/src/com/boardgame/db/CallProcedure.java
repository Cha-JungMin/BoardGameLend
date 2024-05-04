package com.boardgame.db;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.internal.OracleTypes;

public class CallProcedure {
	public static void main(String[] args) {
		
		String procedure =  "{ call board_pack.board_game_statement(?) }";
		
		try {
			Connection con = DBConnection.getConnection();
			CallableStatement callableStatement = con.prepareCall(procedure);
			callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
			
			try {
				callableStatement.execute();
				ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
				
				while (resultSet.next()) {
					// 보드게임 목록 출력 예시
					String board_title = resultSet.getString(1);
					String description = resultSet.getString(2);
					int copy = resultSet.getInt(3);
					String genre = resultSet.getString(4);
					int min_people = resultSet.getInt(5);
					int max_people = resultSet.getInt(6);
					int min_play_time = resultSet.getInt(7);
					int max_play_time = resultSet.getInt(8);
					int rental_fee = resultSet.getInt(9);
					
					System.out.print(board_title);
					System.out.print(description);
					System.out.print(copy);
					System.out.print(genre);
					System.out.print(min_people);
					System.out.print(max_people);
					System.out.print(min_play_time);
					System.out.print(max_play_time);
					System.out.println(rental_fee);
				}
			} catch (SQLException e) {
				System.out.println("프로시저에서 에러 발생!");
				// System.err.format("SQL State: %s", e.getSQLState());
				System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			}
		
		} catch (Exception e) {
			
		}
		
	}
}

