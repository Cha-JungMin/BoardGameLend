package com.boardgame.panel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import com.boardgame.module.ImmutableTableModel;

public class TablePanel extends JScrollPane {

	private JTable			tb;
	private TableModel 		tbModel;
    private JTableHeader	tbHeader;
    
    private String[]		headNames;
    private Object[][]		datas;
    private int				x, y, w, h;
    
    public TablePanel(int _x, int _y, int _w, int _h, String[] _headNames, Object[][] _datas) {
    	this.x = _x;
    	this.y = _y;
    	this.w = _w;
    	this.h = _h;
    	this.headNames = _headNames;
    	this.datas = _datas;
    	createObjects();
    }
    
    private void createObjects() {
    	tbModel = new ImmutableTableModel(headNames, this.datas);
    	tb = new JTable(tbModel);
        tbHeader = tb.getTableHeader();
        this.setBounds(x, y, w, h);
        tbHeader.setReorderingAllowed(false);
        setViewportView(tb);
    }
    
    public void setTableData(Object[][] _datas) {
    	this.datas = _datas;
    	createObjects();
    }
    
}
