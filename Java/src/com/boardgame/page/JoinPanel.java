package com.boardgame.page;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JoinPanel extends JPanel {
	
	private JLabel 		labelId, labelPwd;
	
	public JoinPanel() {
        System.out.println("join panel open!");

        labelId = new JLabel("ID:");
		labelPwd = new JLabel("PWD:");
		
		setLayout(null);
		
		labelId.setBounds(130, 93, 50, 15);
        labelPwd.setBounds(130, 123, 50, 15);
        add(labelId);
        add(labelPwd);
        
    }
	
}
