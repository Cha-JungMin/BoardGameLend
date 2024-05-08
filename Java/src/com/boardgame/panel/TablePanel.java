package com.boardgame.panel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import com.boardgame.module.ImmutableTableModel;

public class TablePanel extends JScrollPane {

	private JTable			tb;
	private TableModel 		tbModel;
    private JTableHeader	tbHeader;
    
    public TablePanel(int x, int y, int w, int h, String[] headNames, Object[][] datas) {
    	tbModel = new ImmutableTableModel(headNames, datas);
    	tb = new JTable(tbModel);
        tbHeader = tb.getTableHeader();
        this.setBounds(x, y, w, h);
        tbHeader.setReorderingAllowed(false);
        setViewportView(tb);
        DefaultTableModel test = (DefaultTableModel) tbModel;
        test.setDataVector(datas, headNames);
    }
}
