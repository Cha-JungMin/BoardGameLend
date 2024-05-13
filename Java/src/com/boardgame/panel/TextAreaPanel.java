package com.boardgame.panel;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextAreaPanel extends JScrollPane {
	
	private JTextArea	txtArea;
	
	private int			x, y, w, h;

	public TextAreaPanel(int _x, int _y, int _w, int _h) {
		this.x = _x;
    	this.y = _y;
    	this.w = _w;
    	this.h = _h;
    	initialization();
	}
	
	public void initialization() {
		txtArea = new JTextArea(this.w, this.h);
		this.setBounds(x, y, w, h);
		txtArea.setLineWrap(true); // 가로 스크롤을 고정하고 줄 바꿈을 활성화
		txtArea.setWrapStyleWord(true); // 단어 단위로 줄 바꿈을 하도록 설정
        setViewportView(txtArea);
	}
	
	public void setText(String str) {
		txtArea.setText(str);
	}
	
	public String getText() {
		return txtArea.getText();
	}
	
}
