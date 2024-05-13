package com.boardgame.module;

import javax.swing.table.DefaultTableModel;

public class ImmutableTableModel extends DefaultTableModel {

	public ImmutableTableModel(Object[] columnNames, Object[][] data) {
        super(data, columnNames);
    }
	
	@Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
	
}
