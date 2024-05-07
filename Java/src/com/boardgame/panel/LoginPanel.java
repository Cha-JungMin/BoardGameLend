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

import com.boardgame.db.DBConnection;
import com.boardgame.db.SQLCall;
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
				SQLCall sql = new SQLCall(
						"{ call pkg_member.login_member(?, ?, ?) }",
						callableStatement -> {
							try {
								callableStatement.setString(1, txtId.getText());
								callableStatement.setString(2, new String(txtPwd.getPassword()));
								callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
								callableStatement.execute();
								callableStatement.getInt(3);
								if (callableStatement.getInt(3) == -1) {
									String title = "BoardGameRental Login Alert";
							        String message = "로그인에 실패했습니다. 다시 시도해 주세요.";
							        int messageType = JOptionPane.INFORMATION_MESSAGE;
							        JOptionPane.showMessageDialog(null, message, title, messageType);
								} else {
									// 로그인 성공으로 페널 이동
									frame.dispose();
									RentalWindow window = new RentalWindow();
									
									SQLCall _sql = new SQLCall(
											"{? = call pkg_member.get_user_grade(?) }",
											_callableStatement -> {
												try {
													System.out.println(callableStatement.getInt(3));
													_callableStatement.registerOutParameter(1, Types.INTEGER);
													_callableStatement.setInt(2, callableStatement.getInt(3));
													_callableStatement.execute();
													System.out.println(_callableStatement.getInt(1));
												} catch (SQLException _err) {
													System.err.format("SQL State: %s\n%s", _err.getSQLState(), _err.getMessage());
													_err.printStackTrace();
												}
											}
											);
//									if (resultSet.getInt(8) == 1) {
//										//관리자
//									}
//									if (resultSet.getInt(8) == 0) {
//										//유저
//										frame.dispose();
//									}
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