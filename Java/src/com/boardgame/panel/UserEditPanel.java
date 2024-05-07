package com.boardgame.panel;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import com.boardgame.menubar.UserMenuBar;
import com.boardgame.window.RentalWindow;

public class UserEditPanel extends JPanel {
	
	private	RentalWindow	frame;
	private JMenuBar		menuBar;
	private JLabel			labelId, labelPwd, labelName, labelBirth, labelPhone, labelJoinDate;

	public UserEditPanel(RentalWindow _frame) {
		frame = _frame;
		menuBar = new UserMenuBar(frame);
		labelId = new JLabel("ID:");
		labelPwd = new JLabel("PWD:");
		labelName = new JLabel("Name:");
		labelBirth = new JLabel("Birth:");
		labelPhone = new JLabel("Phone Number:");
		labelJoinDate = new JLabel("Join Date:");
		initialization();
	}
	
	private void initialization() {
		setLayout(null);
		frame.setJMenuBar(menuBar);
		
		labelId.setBounds(90, 50, 50, 15);
        labelPwd.setBounds(90, 80, 50, 15);
        labelName.setBounds(90, 110, 50, 15);
        labelBirth.setBounds(90, 140, 50, 15);
        labelPhone.setBounds(90, 170, 180, 15);
        labelJoinDate.setBounds(90, 200, 80, 15);
        
        
        add(labelId);
        add(labelPwd);
        add(labelName);
        add(labelBirth);
        add(labelPhone);
        add(labelJoinDate);
        
	}
	
}
