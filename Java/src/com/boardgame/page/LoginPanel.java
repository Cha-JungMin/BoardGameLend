package com.boardgame.page;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.boardgame.db.SQLCall;

import oracle.jdbc.internal.OracleTypes;

public class LoginPanel extends JPanel {

	private JLabel 			labelId, labelPwd;
	private JTextField 		txtId;
	private JPasswordField 	txtPwd;
	private JButton			btnLogin, btnJoin;
	
	public LoginPanel() {
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
						"{ call pkg_member.get_member_info(?, ?, ?) }",
						callableStatement -> {
							try {
								callableStatement.setString(1, txtId.getText());
								callableStatement.setString(2, new String(txtPwd.getPassword()));
								callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
								callableStatement.execute();
								ResultSet resultSet = (ResultSet) callableStatement.getObject(3);
								boolean isLoginInfo = false;
								while (resultSet.next()) {
									isLoginInfo = true;
									System.out.println(resultSet.getString(1));
									System.out.println(resultSet.getString(2));
									System.out.println(resultSet.getString(3));
									System.out.println(resultSet.getString(4));
									System.out.println(resultSet.getString(5));
									System.out.println(resultSet.getString(6));
									System.out.println(resultSet.getString(7));
									System.out.println(resultSet.getString(8));
								}
								if (isLoginInfo) {
									// 로그인 성공으로 페널 이동
									if (resultSet.getInt(8) == 1) {
										//관리자
									}
									if (resultSet.getInt(8) == 0) {
										//유저
									}
								}
								if (!isLoginInfo) {
									String title = "BoardGameLend Login Alert";
							        String message = "로그인에 실패했습니다. 다시 시도해 주세요.";
							        int messageType = JOptionPane.INFORMATION_MESSAGE;
							        JOptionPane.showMessageDialog(null, message, title, messageType);
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
				JoinPanel join = new JoinPanel();
				panel.remove(LoginPanel.this);
				panel.add(join);
				panel.revalidate();
				panel.repaint();
			}
		});

	}
}