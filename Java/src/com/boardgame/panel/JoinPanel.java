package com.boardgame.panel;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.boardgame.db.SQLCall;
import com.boardgame.window.Alert;
import com.boardgame.window.LoginWindow;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class JoinPanel extends JPanel {
	
	private LoginWindow		frame;
	private JLabel 			labelId, labelPwd, labelName, labelBirth, labelPhone;
	private JTextField		txtID, txtName, txtPhone;
	private JPasswordField 	txtPwd;
	private JButton			btnJoin, btnBack;
	
	private DatePicker		datePickerPanel;
	
	public JoinPanel(LoginWindow _frame) {

		frame = _frame;
        labelId = new JLabel("ID:");
		labelPwd = new JLabel("PWD:");
		labelName = new JLabel("Name:");
		labelBirth = new JLabel("Birth:");
		labelPhone = new JLabel("Phone Number:");
		txtID = new JTextField();
		txtName = new JTextField();
		txtPhone = new JTextField();
		txtPwd = new JPasswordField();
		btnJoin = new JButton("Join");
		btnBack = new JButton("Back");
		
		datePickerPanel = new DatePicker(125, 30);
		
		initialization();
        
    }
	
	
	private void initialization() {
		setLayout(null);
		
		labelId.setBounds(90, 50, 50, 15);
        labelPwd.setBounds(90, 80, 50, 15);
        labelName.setBounds(90, 110, 50, 15);
        labelBirth.setBounds(90, 140, 50, 15);
        labelPhone.setBounds(90, 170, 180, 15);
        
        txtID.setBounds(230, 50, 125, 15);
        txtPwd.setBounds(230, 80, 125, 15);
        txtName.setBounds(230, 110, 125, 15);
        datePickerPanel.setBounds(230, 135, 125, 30);
        txtPhone.setBounds(230, 170, 125, 15);
        
        btnJoin.setBounds(90, 200, 120, 30);
        btnBack.setBounds(235, 200, 120, 30);
        
        add(labelId);
        add(labelPwd);
        add(labelName);
        add(labelBirth);
        add(labelPhone);
        
        add(txtID);
        add(txtPwd);
        add(txtName);
        add(datePickerPanel);
        add(txtPhone);
        
        add(btnJoin);
        add(btnBack);
        
        btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SQLCall(
						"{ call pkg_member.add_member_info(?, ?, ?, ?, ?, ?) }",
						cs -> {
							try {
								cs.registerOutParameter(6, java.sql.Types.INTEGER);
								cs.setString(1, txtID.getText());
								cs.setString(2, new String(txtPwd.getPassword()));
								cs.setString(3, txtName.getText());
								cs.setString(4, txtPhone.getText());
								
								cs.setString(5, datePickerPanel.getDatafomat());
								cs.execute();
								if (cs.getInt(6) == 1) {
									new Alert("회원 가입이 되었습니다. 로그인 화면으로 넘어갑니다.");
							        btnBack.doClick();
								}
								if (cs.getInt(6) == 0) new Alert("회원 가입에 실패 했습니다. 다시 시도해 주세요.");
							} catch (SQLException err) {
								if (err.getErrorCode() == -20100) {
									System.err.format("SQL State -20100: %s\n%s", err.getSQLState(), err.getMessage());
								} else {
									System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
									err.printStackTrace();
								}
							}
						}
						);
			}
		});
        
        btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Container panel = getParent();
				LoginPanel login = new LoginPanel(frame);
				panel.remove(JoinPanel.this);
				panel.add(login);
				panel.revalidate();
				panel.repaint();
			}
		});
        
	}
	
}
