package com.boardgame.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.boardgame.db.SQLCall;
import com.boardgame.menubar.UserMenuBar;
import com.boardgame.window.Alert;
import com.boardgame.window.LoginWindow;
import com.boardgame.window.RentalWindow;

import oracle.jdbc.internal.OracleTypes;

public class UserEditPanel extends JPanel {
	
	private	RentalWindow	frame;
	private JMenuBar		menuBar;
	private JLabel			labelId, labelPwd, labelNewPwd, labelName, labelBirth, labelPhone, labelJoinDate;
	private JLabel			labelUserId, labelUserName, labelUserBirth, labelUserPhone, labelUserJoinDate;
	private JPasswordField	txtPwd, txtNewPwd;
	private JButton			btnUpdate, btnResig;
	private int				userId;

	public UserEditPanel(RentalWindow _frame, int user_id) {
		frame = _frame;
		userId = user_id;
		menuBar = new UserMenuBar(frame, userId);
		labelId = new JLabel("ID:");
		labelPwd = new JLabel("PWD:");
		labelNewPwd = new JLabel("New PWD:");
		labelName = new JLabel("Name:");
		labelBirth = new JLabel("Birth:");
		labelPhone = new JLabel("Phone Number:");
		labelJoinDate = new JLabel("Join Date:");
		btnUpdate = new JButton("Update");
		btnResig = new JButton("Resignation");
		initialization();
	}
	
	private void initialization() {
		setLayout(null);
		frame.setJMenuBar(menuBar);
		
		labelId.setBounds(90, 50, 50, 15);
        labelPwd.setBounds(90, 80, 50, 15);
        labelNewPwd.setBounds(90, 110, 80, 15);
        labelName.setBounds(90, 140, 50, 15);
        labelBirth.setBounds(90, 170, 50, 15);
        labelPhone.setBounds(90, 200, 150, 15);
        labelJoinDate.setBounds(90, 230, 80, 15);
        
        btnUpdate.setBounds(550, 138, 130, 30);
		btnResig.setBounds(550, 170, 130, 30);
        
        add(labelId);
        add(labelPwd);
        add(labelNewPwd);
        add(labelName);
        add(labelBirth);
        add(labelPhone);
        add(labelJoinDate);
        
        add(btnUpdate);
        add(btnResig);
        
        getUserInfo();
        setButtons();
	}
	
	private void getUserInfo() {
		new SQLCall(
				"{ call pkg_member.get_member_info(?, ?) }",
				cs -> {
					try {
						cs.setInt(1, userId);
						cs.registerOutParameter(2, OracleTypes.CURSOR);
						cs.execute();
						ResultSet resultSet = (ResultSet) cs.getObject(2);
						while (resultSet.next()) {
							setUserInfo(
									resultSet.getString(2),
									resultSet.getString(4),
									resultSet.getString(5),
									resultSet.getString(6),
									resultSet.getString(7)
									);
						}
					}  catch (SQLException err) {
						System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
						err.printStackTrace();
					}
				});
	}
	
	private void setUserInfo(String userID, String userName, String userPhone, String userBirth, String userJoinDate) {
		labelUserId = new JLabel(userID);
		labelUserName = new JLabel(userName);
		labelUserBirth = new JLabel(userBirth);
		labelUserPhone = new JLabel(userPhone);
		labelUserJoinDate = new JLabel(userJoinDate);
		txtPwd = new JPasswordField();
		txtNewPwd = new JPasswordField();
		labelUserId.setBounds(110, 50, 180, 15);
		txtPwd.setBounds(130, 82, 100, 15);
		txtNewPwd.setBounds(160, 112, 100, 15);
        labelUserName.setBounds(135, 140, 100, 15);
        labelUserBirth.setBounds(130, 170, 130, 15);
        labelUserPhone.setBounds(190, 200, 130, 15);
        labelUserJoinDate.setBounds(160, 230, 130, 15);
        add(labelUserId);
        add(txtPwd);
        add(txtNewPwd);
        add(labelUserName);
        add(labelUserBirth);
        add(labelUserPhone);
        add(labelUserJoinDate);
	}
	
	private void setButtons() {
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String pwd = new String(txtPwd.getPassword());
				String newPwd = new String(txtNewPwd.getPassword());
				if (pwd.length() < 1 || newPwd.length() < 1) {
					new Alert("비밀번호 혹은 새로운 비밀번호를 입력해 주세요.");
					return;
				}
				new SQLCall(
						"{ call pkg_member.update_member_info(?, ?, ?, ?) }",
						cs -> {
							try {
								cs.setInt(1, userId);
								cs.setString(2, pwd);
								cs.setString(3, newPwd);
								cs.registerOutParameter(4, java.sql.Types.INTEGER);
								cs.execute();
								if (cs.getInt(4) == -1) new Alert("비밀번호가 틀렸습니다.");
								if (cs.getInt(4) == 1) {
									new Alert("비밀번호가 수정 되었습니다. 다시 로그인해 주세요");
									frame.dispose();
									new LoginWindow();
								}
							} catch (SQLException _err) {
								System.err.format("SQL State: %s\n%s", _err.getSQLState(), _err.getMessage());
								_err.printStackTrace();
							}
						});
			}
		});
		btnResig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String pwd = new String(txtPwd.getPassword());
				if (pwd.length() < 1) {
					new Alert("사용 중인 비밀번호를 입력해 주세요.");
					return;
				}
				new SQLCall(
						"{ call pkg_member.delete_member_info(?, ?, ?) }",
						cs -> {
							try {
								cs.setInt(1, userId);
								cs.setString(2, pwd);
								cs.registerOutParameter(3, java.sql.Types.INTEGER);
								cs.execute();
								if (cs.getInt(3) == -1) new Alert("회원 탈퇴에 실패했습니다. 비밀번호를 다시 입력해서 시도해 주세요.");
								if (cs.getInt(3) == 1) {
									new Alert("회원 탈퇴가 되었습니다.\n이용해 주셔서 감사합니다.");
									frame.dispose();
									new LoginWindow();
								}
							} catch (SQLException _err) {
								System.err.format("SQL State: %s\n%s", _err.getSQLState(), _err.getMessage());
								_err.printStackTrace();
							}
						});
			}
		});
	}
	
}
