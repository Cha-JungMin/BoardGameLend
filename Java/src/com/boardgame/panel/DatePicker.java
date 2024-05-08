package com.boardgame.panel;

import javax.swing.JPanel;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class DatePicker extends JPanel {

	private UtilDateModel	model;
	private JDatePanelImpl	datePanel;
	private JDatePickerImpl	datePicker;
	private int				w, h;
	
	public DatePicker(int w, int h) {
		this.w = w;
		this.h = h;
		model = new UtilDateModel();
		datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		
		initialization();
	}
	
	private void initialization() {
		setLayout(null);
		
		datePanel.setBounds(0, 0, w, h);
        datePicker.setBounds(0, 0, w, h);
        
        add(datePicker);
	}
	
	public String getDatafomat() {
		int month = this.model.getMonth() + 1;
		return this.model.getYear() + "-" + (month < 10 ? "0" + month : month) + "-" + this.model.getDay();
	}
	
	public static String getDatafomat(UtilDateModel _model) {
		int month = _model.getMonth() + 1;
		return _model.getYear() + "-" + (month < 10 ? "0" + month : month) + "-" + _model.getDay();
	}
	
}
