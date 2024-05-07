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
import com.boardgame.window.LoginWindow;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class JoinPanel extends JPanel {
	
	private LoginWindow			frame;
	private JLabel 			labelId, labelPwd, labelName, labelBirth, labelPhone;
	private JTextField		txtID, txtName, txtPhone;
	private JPasswordField 	txtPwd;
	private JButton			btnJoin, btnBack;
	
	private UtilDateModel	model;
	private JDatePanelImpl	datePanel;
	private JDatePickerImpl	datePicker;
	
	
	
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
		
		model = new UtilDateModel();
		datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		
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
        datePanel.setBounds(230, 135, 125, 30);
        datePicker.setBounds(230, 135, 125, 30);
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
        add(datePicker);
        add(txtPhone);
        
        add(btnJoin);
        add(btnBack);
        
        btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int month = model.getMonth() + 1;
				SQLCall sql = new SQLCall(
						"{ call pkg_member.add_member_info(?, ?, ?, ?, ?, ?) }",
						callableStatement -> {
							try {
								callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);
								callableStatement.setString(1, txtID.getText());
								callableStatement.setString(2, new String(txtPwd.getPassword()));
								callableStatement.setString(3, txtName.getText());
								callableStatement.setString(4, txtPhone.getText());
								callableStatement.setString(5, model.getYear() + "-" + (month < 10 ? "0" + month : month) + "-" + model.getDay());
								callableStatement.execute();
								if (callableStatement.getInt(6) == 1) {
									String title = "BoardGameRental Login Alert";
									String message = "회원 가입이 되었습니다. 로그인 화면으로 넘어갑니다.";
							        int messageType = JOptionPane.INFORMATION_MESSAGE;
							        JOptionPane.showMessageDialog(null, message, title, messageType);
							        btnBack.doClick();
								}
								if (callableStatement.getInt(6) == 0) {
									String title = "BoardGameRental Login Alert";
							        String message = "회원 가입에 실패 했습니다. 다시 시도해 주세요.";
							        int messageType = JOptionPane.INFORMATION_MESSAGE;
							        JOptionPane.showMessageDialog(null, message, title, messageType);
								}
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
