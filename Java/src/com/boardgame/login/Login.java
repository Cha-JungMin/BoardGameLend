package com.boardgame.login;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame {

	private JFrame			frame;
	private Container 		pane;
	private JLabel 			labelId;
	private JLabel 			labelPwd;
	private JTextField 		txtId;
	private JPasswordField 	txtPwd;
	
	public Login() {
		initialization();
	}
	
	private void initialization() {
		frame = new JFrame();
		pane = getContentPane();
		labelId = new JLabel("ID:");
		labelPwd = new JLabel("PWD:");
		txtId = new JTextField();
		txtPwd = new JPasswordField();
		
		setTitle("BoardGameLend Login"); // 프레임 제목 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(450, 300); // 프레임 크기 설정
        
        labelId.setBounds(130, 103, 50, 15);
        labelPwd.setBounds(130, 133, 50, 15);
        txtId.setBounds(190, 103, 125, 15);
        txtPwd.setBounds(190, 133, 125, 15);
        
        txtId.setColumns(0);
        txtPwd.setColumns(0);

        pane.setLayout(null);
        pane.add(labelId);
        pane.add(labelPwd);
        pane.add(txtId);
        pane.add(txtPwd);


        setVisible(true); //화면에 프레임 출력
	}
}