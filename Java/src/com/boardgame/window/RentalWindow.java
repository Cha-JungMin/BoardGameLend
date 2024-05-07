package com.boardgame.window;

import java.awt.Container;

import javax.swing.JFrame;

import com.boardgame.panel.RentalPanel;

public class RentalWindow extends JFrame implements Window {

	private Container container = getContentPane();

	public RentalWindow() {
		initialization();
	}
	
	@Override
	public void initialization() {
		setTitle("BoardGameRental");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        RentalPanel rentalPanel = new RentalPanel(RentalWindow.this);
        container.add(rentalPanel);
        
        setVisible(true);
	}

}
