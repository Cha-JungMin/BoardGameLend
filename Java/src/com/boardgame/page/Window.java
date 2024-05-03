package com.boardgame.page;

import java.awt.Container;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	private Container		container;

	public Window() {
		setTitle("zzzz"); // 프레임 제목 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(450, 300); // 프레임 크기 설정

        Container contentPane = getContentPane(); //프레임에서 컨텐트팬 받아오기
        contentPane.setLayout(null);
        
        Login pane = new Login();
        
        contentPane.add(pane);
        
        setVisible(true); //화면에 프레임 출력
	}
	
}
