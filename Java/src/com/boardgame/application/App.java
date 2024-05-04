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
	private static Panel1 panel1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		// DB 연결을 스레드로 실행
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Connection c = com.boardgame.db.DBConnection.getConnection();
					// DB 연결이 성공했을 때 
					con = c;
					panel1.con =c;
					panel1.loadBoardGame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
	
	
	/**
	 * Create the frame.
	 */
	public App() {
		this.setTitle("보드게임 정보");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		panel1 = new Panel1();
		panel1.setLocation(0, 0);
		panel1.setSize(800, 600);
		contentPane.add(panel1);
		
		
		setContentPane(contentPane);
		contentPane.setLayout(null); 
		}

}
