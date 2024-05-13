package com.boardgame.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class SelectBoxPanel extends JPanel {
	
	private String[]			items;
	private JComboBox<String> 	sltBox;
	
	private int					x, y, w, h;

	public SelectBoxPanel(int x, int y, int w, int h, String[] datas) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.items = datas;
	    this.setBounds(x, y, w, h);
	    initialization();
	}
	
	private void initialization() {
		sltBox = new JComboBox<>(items);
		sltBox.setBounds(x, y, w, h);
		sltBox.setSelectedIndex(0);
		add(sltBox);
	}
	
	public void setChangeEvent(IntChangeListener callback) {
		sltBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				callback.onChange(sltBox.getSelectedIndex());
			}
		});
	}
	
	public void setSltInit(int number) {
		sltBox.setSelectedIndex(number);
	}
	
	public String getStrSltItem() {
		return (String) sltBox.getSelectedItem();
	}
    
}
