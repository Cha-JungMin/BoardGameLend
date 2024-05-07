package com.boardgame.db;

import java.sql.CallableStatement;

public interface CalStatementSetCallback {
	void processCalStatementSet(CallableStatement resultSet);
}
