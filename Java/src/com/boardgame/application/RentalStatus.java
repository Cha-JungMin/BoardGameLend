package com.boardgame.application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.boardgame.panel.RentalPanel;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import javax.swing.JRadioButtonMenuItem;

public class RentalStatus extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RentalStatus frame = new RentalStatus();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RentalStatus() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 614, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JRadioButtonMenuItem rdbtnmntmNewRadioItem = new JRadioButtonMenuItem("New radio item");
		rdbtnmntmNewRadioItem.setBounds(120, 82, 181, 114);
		contentPane.add(rdbtnmntmNewRadioItem);

//		private UtilDateModel	model;
//		private JDatePanelImpl	datePanel;
//		private JDatePickerImpl	datePicker;
		
		
//		model = new UtilDateModel();
//		datePanel = new JDatePanelImpl(model);
//		datePicker = new JDatePickerImpl(datePanel);
		
//		 datePanel.setBounds(230, 135, 125, 30);  //달력창
//	     datePicker.setBounds(230, 135, 125, 30);  // 버튼
//	     
//	     add(datePicker);
	
		
		
//		int month = model.getMonth() + 1;
//		 cs.setString(5, model.getYear() + "-" + (month < 10 ? "0" + month : month) + "-" + model.getDay());
	}
}
