package com.boardgame.page;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Join extends JPanel {
	
	private JPanel		pane;
	private JLabel 			labelId, labelPwd;
	
	public Join() {
		super();
        System.out.println("join panel open!");
        pane = new JPanel();

        labelId = new JLabel("ID:");
		labelPwd = new JLabel("PWD:");
		
		labelId.setBounds(130, 93, 50, 15);
        labelPwd.setBounds(130, 123, 50, 15);
        add(labelId);
        add(labelPwd);
        
        add(pane);

    }
	
}
