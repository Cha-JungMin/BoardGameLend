package com.boardgame.application;

import java.awt.EventQueue;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class App extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Connection con;
	private static BoardStatus boardStatus;

	public static void main(String[] args) {	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App(con);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
    }
	
	public App(Connection con) {
		boardStatus.con =con;
		this.setTitle("보드게임 정보");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 508);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		boardStatus = new BoardStatus();
		boardStatus.setLocation(0, 0);
		boardStatus.setSize(800, 476);
		contentPane.add(boardStatus);
		
		setContentPane(contentPane);
		contentPane.setLayout(null); 
		this.setVisible(true);
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					boardStatus.refresh();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
		}

}
