package com.boardgame.application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

public class SearchFilter extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Connection con;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchFilter frame = new SearchFilter(con);
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
	public SearchFilter(Connection con) {
		setTitle("검색 필터");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(12, 65, 76, 25);
		textPane_1.setText("게임 이름");
		textPane_1.setFont(new Font("굴림", Font.PLAIN, 16));
		textPane_1.setEditable(false);
		textPane_1.setBackground(UIManager.getColor("Button.background"));
		contentPane.add(textPane_1);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(12, 97, 513, 41);
		textPane.setText("<dynamic>");
		contentPane.add(textPane);
		
		JButton btnNewButton = new JButton("검색 조회 필터");
		btnNewButton.setEnabled(false);
		btnNewButton.setBackground(Color.GRAY);
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 26));
		btnNewButton.setBounds(12, 10, 513, 48);
		contentPane.add(btnNewButton);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setText("<dynamic>");
		textPane_2.setBounds(12, 180, 513, 41);
		contentPane.add(textPane_2);
		
		JTextPane textPane_1_1 = new JTextPane();
		textPane_1_1.setText("포함 장르");
		textPane_1_1.setFont(new Font("굴림", Font.PLAIN, 16));
		textPane_1_1.setEditable(false);
		textPane_1_1.setBackground(UIManager.getColor("Button.background"));
		textPane_1_1.setBounds(12, 148, 76, 25);
		contentPane.add(textPane_1_1);
		
		JTextPane textPane_4 = new JTextPane();
		textPane_4.setText("0");
		textPane_4.setBounds(12, 276, 196, 34);
		contentPane.add(textPane_4);
		
		JTextPane textPane_4_1 = new JTextPane();
		textPane_4_1.setText("0");
		textPane_4_1.setBounds(327, 276, 196, 34);
		contentPane.add(textPane_4_1);
		
		JTextPane textPane_1_3_1 = new JTextPane();
		textPane_1_3_1.setText("최대 인원 (명)");
		textPane_1_3_1.setFont(new Font("굴림", Font.PLAIN, 16));
		textPane_1_3_1.setEditable(false);
		textPane_1_3_1.setBackground(UIManager.getColor("Button.background"));
		textPane_1_3_1.setBounds(327, 241, 114, 25);
		contentPane.add(textPane_1_3_1);
		
		JTextPane textPane_1_3 = new JTextPane();
		textPane_1_3.setText("최소 인원 (명)");
		textPane_1_3.setFont(new Font("굴림", Font.PLAIN, 16));
		textPane_1_3.setEditable(false);
		textPane_1_3.setBackground(UIManager.getColor("Button.background"));
		textPane_1_3.setBounds(12, 241, 114, 25);
		contentPane.add(textPane_1_3);
		
		JTextPane textPane_4_2 = new JTextPane();
		textPane_4_2.setText("0");
		textPane_4_2.setBounds(12, 368, 196, 34);
		contentPane.add(textPane_4_2);
		
		JTextPane textPane_4_1_1 = new JTextPane();
		textPane_4_1_1.setText("0");
		textPane_4_1_1.setBounds(327, 368, 196, 34);
		contentPane.add(textPane_4_1_1);
		
		JTextPane textPane_1_3_1_1 = new JTextPane();
		textPane_1_3_1_1.setText("최대 대여료");
		textPane_1_3_1_1.setFont(new Font("굴림", Font.PLAIN, 16));
		textPane_1_3_1_1.setEditable(false);
		textPane_1_3_1_1.setBackground(UIManager.getColor("Button.background"));
		textPane_1_3_1_1.setBounds(327, 333, 114, 25);
		contentPane.add(textPane_1_3_1_1);
		
		JTextPane textPane_1_3_2 = new JTextPane();
		textPane_1_3_2.setText("최소 대여료(원)");
		textPane_1_3_2.setFont(new Font("굴림", Font.PLAIN, 16));
		textPane_1_3_2.setEditable(false);
		textPane_1_3_2.setBackground(UIManager.getColor("Button.background"));
		textPane_1_3_2.setBounds(12, 333, 114, 25);
		contentPane.add(textPane_1_3_2);
		
		JButton btnNewButton_1 = new JButton("검색");
		btnNewButton_1.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_1.setBounds(12, 447, 196, 41);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("닫기");
		btnNewButton_1_1.setFont(new Font("굴림", Font.PLAIN, 20));
		btnNewButton_1_1.setBounds(329, 447, 196, 41);
		contentPane.add(btnNewButton_1_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(178, 168, 5, 24);
		contentPane.add(textArea);
	}
}
