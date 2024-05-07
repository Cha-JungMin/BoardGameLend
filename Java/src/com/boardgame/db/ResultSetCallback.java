package com.boardgame.db;

import java.sql.ResultSet;

public interface ResultSetCallback {
	void processResultSet(ResultSet resultSet);
}
