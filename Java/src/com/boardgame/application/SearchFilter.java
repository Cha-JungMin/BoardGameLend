package com.boardgame.application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SearchFilter extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static BoardStatus board;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchFilter frame = new SearchFilter(board);
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
	public SearchFilter(BoardStatus board) {
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
		contentPane.add(textPane);
		
		JButton btnNewButton = new JButton("검색 조회 필터");
		btnNewButton.setEnabled(false);
		btnNewButton.setBackground(Color.GRAY);
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 26));
		btnNewButton.setBounds(12, 10, 513, 48);
		contentPane.add(btnNewButton);
		
		JTextPane textPane_2 = new JTextPane();
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
		textPane_4.setBounds(12, 276, 196, 34);
		contentPane.add(textPane_4);
		
		JTextPane textPane_4_1 = new JTextPane();
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
		textPane_4_2.setBounds(12, 368, 196, 34);
		contentPane.add(textPane_4_2);
		
		JTextPane textPane_4_1_1 = new JTextPane();
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
		
		JTextPane textPane_1_3_3 = new JTextPane();
		textPane_1_3_3.setEnabled(false);
		textPane_1_3_3.setText("~");
		textPane_1_3_3.setFont(new Font("굴림", Font.PLAIN, 30));
		textPane_1_3_3.setEditable(false);
		textPane_1_3_3.setBackground(UIManager.getColor("Button.background"));
		textPane_1_3_3.setBounds(250, 276, 38, 34);
		contentPane.add(textPane_1_3_3);
		
		JTextPane textPane_1_3_3_1 = new JTextPane();
		textPane_1_3_3_1.setText("~");
		textPane_1_3_3_1.setFont(new Font("굴림", Font.PLAIN, 30));
		textPane_1_3_3_1.setEnabled(false);
		textPane_1_3_3_1.setEditable(false);
		textPane_1_3_3_1.setBackground(UIManager.getColor("Button.background"));
		textPane_1_3_3_1.setBounds(250, 368, 38, 34);
		contentPane.add(textPane_1_3_3_1);
		
		
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String board_title = textPane.getText();
			        if (board_title.isEmpty()) {
			        	board_title = "";
			        }
			        String genre = textPane_2.getText();
			        if (genre.isEmpty()) {
			            genre = "";
			        }

			        int min_people, max_people, min_rental_fee, max_rental_fee;
			        try {
			            min_people = textPane_4.getText().isEmpty() ? 1 : Integer.parseInt(textPane_4.getText());
			            max_people = textPane_4_1.getText().isEmpty() ? 9999 : Integer.parseInt(textPane_4_1.getText());
			            min_rental_fee = textPane_4_2.getText().isEmpty() ? 0 : Integer.parseInt(textPane_4_2.getText());
			            max_rental_fee = textPane_4_1_1.getText().isEmpty() ? 999999999 : Integer.parseInt(textPane_4_1_1.getText());
			        } catch (NumberFormatException ex) {
			        	JOptionPane.showMessageDialog(null, "올바른 값을 입력해 주세요.", "오류", JOptionPane.ERROR_MESSAGE);
			        	return;
			        }

				ResultSet result = com.boardgame.db.BoardPack.SearchBoardGame(board_title,
						genre, min_people, max_people, min_rental_fee, max_rental_fee);
				board.loadBoardGame(result);
			}
		});
		
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
