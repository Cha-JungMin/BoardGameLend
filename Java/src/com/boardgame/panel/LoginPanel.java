package com.boardgame.panel;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.boardgame.application.App;
import com.boardgame.db.DBConnection;
import com.boardgame.db.SQLCall;
import com.boardgame.window.Alert;
import com.boardgame.window.LoginWindow;
import com.boardgame.window.RentalWindow;

public class LoginPanel extends JPanel {

	private	LoginWindow			frame;
	private JLabel 			labelId, labelPwd;
	private JTextField 		txtId;
	private JPasswordField 	txtPwd;
	private JButton			btnLogin, btnJoin;
	
	public LoginPanel(LoginWindow _frame) {
		frame = _frame;
		labelId = new JLabel("ID:");
		labelPwd = new JLabel("PWD:");
		txtId = new JTextField();
		txtPwd = new JPasswordField();
		btnLogin = new JButton("Login");
		btnJoin = new JButton("Sign up");
		initialization();
	}
	
	private void initialization() {
		setLayout(null);
		
        labelId.setBounds(130, 93, 50, 15);
        labelPwd.setBounds(130, 123, 50, 15);
        txtId.setBounds(190, 93, 125, 15);
        txtPwd.setBounds(190, 123, 125, 15);
        btnLogin.setBounds(130, 153, 80, 30);
        btnJoin.setBounds(235, 153, 80, 30);
        
        txtId.setColumns(0);
        txtPwd.setColumns(0);
        
        add(labelId);
        add(labelPwd);
        add(txtId);
        add(txtPwd);
        add(btnLogin);
        add(btnJoin);
        
        btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SQLCall(
						"{ call pkg_member.get_login_member_no(?, ?, ?) }",
						cs -> {
							try {
								cs.setString(1, txtId.getText());
								cs.setString(2, new String(txtPwd.getPassword()));
								cs.registerOutParameter(3, java.sql.Types.INTEGER);
								cs.execute();
								cs.getInt(3);
								if (cs.getInt(3) == -1) {
									new Alert("로그인에 실패했습니다. 다시 시도해 주세요.");
								} else {
									SQLCall gradeCall = new SQLCall(
											"{? = call pkg_member.get_user_grade(?) }",
											csGrade -> {
												try {
													csGrade.registerOutParameter(1, Types.INTEGER);
													csGrade.setInt(2, cs.getInt(3));
													csGrade.execute();
													if (csGrade.getInt(1) == 1) {
														//관리자
														new App(csGrade.getConnection());
														
													}
													if (csGrade.getInt(1) == 0) new RentalWindow(cs.getInt(3));
													frame.dispose();
												} catch (SQLException err) {
													System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
													err.printStackTrace();
												}
											}
											);
								}
							} catch (SQLException err) {
								System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
								err.printStackTrace();
							}
						}
						);
			}
		});
        
        btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Container panel = getParent();
				JoinPanel join = new JoinPanel(frame);
				panel.remove(LoginPanel.this);
				panel.add(join);
				panel.revalidate();
				panel.repaint();
			}
		});

	}
}