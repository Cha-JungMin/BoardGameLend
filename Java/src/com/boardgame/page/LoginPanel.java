package com.boardgame.page;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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
				System.out.println("login click");
			}
		});
        
        btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sign up click");
				
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