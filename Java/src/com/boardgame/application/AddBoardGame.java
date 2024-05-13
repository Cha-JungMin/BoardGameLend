package com.boardgame.application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
	private static BoardStatus board;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddBoardGame frame = new AddBoardGame(board);
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
	public AddBoardGame(BoardStatus board) {
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
		textPane_1_3.setText("최소 인원 (명)");
		textPane_1_3.setFont(new Font("굴림", Font.PLAIN, 16));
		textPane_1_3.setEditable(false);
		textPane_1_3.setBackground(UIManager.getColor("Button.background"));
		textPane_1_3.setBounds(43, 362, 114, 25);
		contentPane.add(textPane_1_3);
		
		JTextPane textPane_4 = new JTextPane();
		textPane_4.setBounds(43, 397, 196, 34);
		contentPane.add(textPane_4);
		
		JTextPane textPane_1_4 = new JTextPane();
		textPane_1_4.setText("최소 플레이 시간 (분)");
		textPane_1_4.setFont(new Font("굴림", Font.PLAIN, 16));
		textPane_1_4.setEditable(false);
		textPane_1_4.setBackground(UIManager.getColor("Button.background"));
		textPane_1_4.setBounds(43, 441, 162, 25);
		contentPane.add(textPane_1_4);
		
		JTextPane textPane_5 = new JTextPane();
		textPane_5.setBounds(43, 476, 196, 34);
		contentPane.add(textPane_5);
		
		JTextPane textPane_1_4_1 = new JTextPane();
		textPane_1_4_1.setText("대여료 (일)");
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
		
		JTextPane textPane_1_3_1 = new JTextPane();
		textPane_1_3_1.setText("최대 인원 (명)");
		textPane_1_3_1.setFont(new Font("굴림", Font.PLAIN, 16));
		textPane_1_3_1.setEditable(false);
		textPane_1_3_1.setBackground(UIManager.getColor("Button.background"));
		textPane_1_3_1.setBounds(358, 362, 114, 25);
		contentPane.add(textPane_1_3_1);
		
		JTextPane textPane_4_1 = new JTextPane();
		textPane_4_1.setBounds(358, 397, 196, 34);
		contentPane.add(textPane_4_1);
		
		JTextPane textPane_1_3_2 = new JTextPane();
		textPane_1_3_2.setText("최대 플레이 시간 (분)");
		textPane_1_3_2.setFont(new Font("굴림", Font.PLAIN, 16));
		textPane_1_3_2.setEditable(false);
		textPane_1_3_2.setBackground(UIManager.getColor("Button.background"));
		textPane_1_3_2.setBounds(358, 441, 170, 25);
		contentPane.add(textPane_1_3_2);
		
		JTextPane textPane_4_2 = new JTextPane();
		textPane_4_2.setBounds(358, 476, 196, 34);
		contentPane.add(textPane_4_2);
		
		JButton btnNewButton = new JButton("보드게임 추가하기");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = textPane.getText();
				String description = textPane_3.getText();
				int min_people, max_people, min_playtime, max_playtime, rental_fee, copy;
	    		try {
	    			min_people = Integer.parseInt(textPane_4.getText());
	    			max_people = Integer.parseInt(textPane_4_1.getText());
	    			min_playtime = Integer.parseInt(textPane_5.getText());
	    			max_playtime = Integer.parseInt(textPane_4_2.getText());
	    			rental_fee = Integer.parseInt(textPane_5_1.getText());
	    			copy = Integer.parseInt(textPane_5_2.getText());
	    		} catch (NumberFormatException e1) {
	    			JOptionPane.showMessageDialog(null, "인원, 플레이시간, 대여료, 개수는 숫자만 입력 가능합니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
	    			return ;
	    		}
	    		
	    		if (min_people > max_people) {
	    			JOptionPane.showMessageDialog(null, "최소 인원이 최대인원보다 많습니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
	    			return ;
	    		}
	    		if (min_playtime > max_playtime) {
	    			JOptionPane.showMessageDialog(null, "최소 플레이 시간이, 최대 플레이 시간보다 많습니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
	    			return ;
	    		}
	    		if (rental_fee % 100 != 0) {
	    			JOptionPane.showMessageDialog(null, "대여료는 100원 단위로 설정할 수 있습니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
	    			return ;
	    		}
	    		if (copy < 1) {
	    			JOptionPane.showMessageDialog(null, "정확한 개수를 입력해 주세요 .", "입력 오류", JOptionPane.ERROR_MESSAGE);
	    			return ;
	    		}
	    		
	    		
	    		com.boardgame.db.BoardPack.createBoardGame(title, description, min_people, max_people,
	    				min_playtime, max_playtime, rental_fee, copy);
	    		JOptionPane.showMessageDialog(null, "보드게임을 추가하였습니다.", "성공", JOptionPane.PLAIN_MESSAGE);
	    		dispose();
	    		board.refresh();
			}
		});
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 26));
		btnNewButton.setBounds(43, 688, 511, 56);
		contentPane.add(btnNewButton);
		StyledDocument doc = txtpnAsdasd.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
	}
}
