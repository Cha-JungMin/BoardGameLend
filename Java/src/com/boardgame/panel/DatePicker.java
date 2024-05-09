package com.boardgame.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

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
	
	public void setNowDate() {
		LocalDate now = LocalDate.now();
		model.setDate(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());
		model.setSelected(true);
	}
	
	public String getDatafomat() {
		int month = this.model.getMonth() + 1;
		return this.model.getYear() + "-" + (month < 10 ? "0" + month : month) + "-" + this.model.getDay();
	}
	
	public void setChangeEvent(ChangeListener callback) {
		datePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				callback.onChange();
			}
		});
	}
	
	public LocalDate getLocalDate() {
		return LocalDate.of(this.model.getYear(), this.model.getMonth() + 1, this.model.getDay());
	}
	
	public static String getDatafomat(UtilDateModel _model) {
		int month = _model.getMonth() + 1;
		return _model.getYear() + "-" + (month < 10 ? "0" + month : month) + "-" + _model.getDay();
	}
	
	public void setDateToday() {
		LocalDate now = LocalDate.now();
		model.setDate(now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());
		model.setSelected(true);
	}
}
