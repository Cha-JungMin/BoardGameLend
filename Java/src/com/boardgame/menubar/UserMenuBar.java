package com.boardgame.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.boardgame.panel.RentalPanel;
import com.boardgame.panel.SearchGenrePanel;
import com.boardgame.panel.SearchRentalFeePanel;
import com.boardgame.panel.UserEditPanel;
import com.boardgame.window.LoginWindow;
import com.boardgame.window.RentalWindow;
import com.boardgame.window.SearchGenreWindow;

public class UserMenuBar extends JMenuBar {

	private JFrame			frame;
	private JMenuBar		menuBar;
	private JMenu			menuRental, menuSearch, menuInfo;
	private JMenuItem		itemRentalBG, itemSearchBG, itemGenre, itemRentalFee, itemEdit, itemLogout;
	private int				userId;
	
	public UserMenuBar(JFrame _frame, int user_id) {
		frame = _frame;
		userId = user_id;
		menuBar = new JMenuBar();
		menuRental = new JMenu("Rental");
		menuSearch = new JMenu("Search");
		menuInfo = new JMenu("Info");
		
		itemRentalBG = new JMenuItem("BoardGame");
		itemSearchBG = new JMenuItem("BoardGame");
		itemGenre = new JMenuItem("Genre");
		itemRentalFee = new JMenuItem("RentalFee");
		itemEdit = new JMenuItem("Edit");
		itemLogout = new JMenuItem("Logout");
		
		initialization();
	}
	
	private void initialization() {
		menuRental.add(itemRentalBG);
		menuSearch.add(itemSearchBG);
		menuSearch.add(itemGenre);
		menuSearch.add(itemRentalFee);
		menuInfo.add(itemEdit);
		menuInfo.add(itemLogout);
		
		itemRentalBG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RentalPanel panel = new RentalPanel(frame, userId);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.revalidate();
				frame.repaint();
			}
		});
		
		itemSearchBG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		itemGenre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SearchGenrePanel panel = new SearchGenrePanel(frame, userId);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.revalidate();
				frame.repaint();
			}
		});
		
		itemRentalFee.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SearchRentalFeePanel panel = new SearchRentalFeePanel(frame, userId);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.revalidate();
				frame.repaint();
			}
		});
		
		itemEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserEditPanel panel = new UserEditPanel(frame, userId);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.revalidate();
				frame.repaint();
			}
		});
		
		itemLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					frame.dispose();
					new LoginWindow();
			}
		});
		
		menuBar.add(menuRental);
		menuBar.add(menuSearch);
		menuBar.add(menuInfo);
		
		add(menuBar);
	}
}
