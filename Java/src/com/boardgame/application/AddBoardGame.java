package com.boardgame.application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class AddBoardGame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static Connection con;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddBoardGame frame = new AddBoardGame();
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
	public AddBoardGame() {
		setTitle("보드게임 추가");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 627, 791);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnAsdasd = new JTextPane();
		txtpnAsdasd.setFont(new Font("굴림", Font.PLAIN, 26));
		txtpnAsdasd.setForeground(UIManager.getColor("Button.foreground"));
		txtpnAsdasd.setBackground(new Color(171, 165, 165));
		txtpnAsdasd.setEditable(false);
		txtpnAsdasd.setText("보드게임 정보입력");
		txtpnAsdasd.setBounds(12, 24, 589, 43);
		contentPane.add(txtpnAsdasd);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(43, 121, 511, 34);
		contentPane.add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBackground(UIManager.getColor("Button.background"));
		textPane_1.setFont(new Font("굴림", Font.PLAIN, 16));
		textPane_1.setEditable(false);
		textPane_1.setText("게임 이름");
		textPane_1.setBounds(43, 86, 114, 25);
		contentPane.add(textPane_1);
		
		JTextPane textPane_1_2 = new JTextPane();
		textPane_1_2.setText("설명");
		textPane_1_2.setFont(new Font("굴림", Font.PLAIN, 16));
		textPane_1_2.setEditable(false);
		textPane_1_2.setBackground(UIManager.getColor("Button.background"));
		textPane_1_2.setBounds(43, 244, 114, 25);
		contentPane.add(textPane_1_2);
		
		JTextPane textPane_3 = new JTextPane();
		textPane_3.setBounds(43, 279, 511, 84);
		contentPane.add(textPane_3);
		
		JTextPane textPane_1_3 = new JTextPane();
		textPane_1_3.setText("인원 (명)");
		textPane_1_3.setFont(new Font("굴림", Font.PLAIN, 16));
		textPane_1_3.setEditable(false);
		textPane_1_3.setBackground(UIManager.getColor("Button.background"));
		textPane_1_3.setBounds(43, 362, 114, 25);
		contentPane.add(textPane_1_3);
		
		JTextPane textPane_4 = new JTextPane();
		textPane_4.setBounds(43, 397, 511, 34);
		contentPane.add(textPane_4);
		
		JTextPane textPane_1_4 = new JTextPane();
		textPane_1_4.setText("플레이 시간 (분)");
		textPane_1_4.setFont(new Font("굴림", Font.PLAIN, 16));
		textPane_1_4.setEditable(false);
		textPane_1_4.setBackground(UIManager.getColor("Button.background"));
		textPane_1_4.setBounds(43, 441, 114, 25);
		contentPane.add(textPane_1_4);
		
		JTextPane textPane_5 = new JTextPane();
		textPane_5.setBounds(43, 476, 511, 34);
		contentPane.add(textPane_5);
		
		JTextPane textPane_1_4_1 = new JTextPane();
		textPane_1_4_1.setText("대여료 (분)");
		textPane_1_4_1.setFont(new Font("굴림", Font.PLAIN, 16));
		textPane_1_4_1.setEditable(false);
		textPane_1_4_1.setBackground(UIManager.getColor("Button.background"));
		textPane_1_4_1.setBounds(43, 520, 114, 25);
		contentPane.add(textPane_1_4_1);
		
		JTextPane textPane_5_1 = new JTextPane();
		textPane_5_1.setBounds(43, 555, 511, 34);
		contentPane.add(textPane_5_1);
		
		JTextPane textPane_1_4_2 = new JTextPane();
		textPane_1_4_2.setText("개수");
		textPane_1_4_2.setFont(new Font("굴림", Font.PLAIN, 16));
		textPane_1_4_2.setEditable(false);
		textPane_1_4_2.setBackground(UIManager.getColor("Button.background"));
		textPane_1_4_2.setBounds(43, 599, 114, 25);
		contentPane.add(textPane_1_4_2);
		
		JTextPane textPane_5_2 = new JTextPane();
		textPane_5_2.setBounds(43, 634, 511, 34);
		contentPane.add(textPane_5_2);
		
		JTextPane txtpnAsdasd_1 = new JTextPane();
		txtpnAsdasd_1.setText("보드게임 추가");
		txtpnAsdasd_1.setForeground(Color.BLACK);
		txtpnAsdasd_1.setFont(new Font("굴림", Font.PLAIN, 26));
		txtpnAsdasd_1.setEditable(false);
		txtpnAsdasd_1.setBackground(new Color(171, 165, 165));
		txtpnAsdasd_1.setBounds(12, 690, 589, 43);
		contentPane.add(txtpnAsdasd_1);
		
		StyledDocument doc = txtpnAsdasd.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
	}
}
