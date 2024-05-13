package com.boardgame.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.boardgame.db.SQLCall;
import com.boardgame.window.Alert;

import oracle.jdbc.internal.OracleTypes;

public class RentalReviewPanel extends JPanel {

	private int		userId;
	private int		rentalDetailId;
	private JDialog	frame;
	
	private JLabel	labelProduct, labelGameName, labelRentalDate,
					labelRentalStatus, labelTotalFee,
					labelGrade, labelComment;
	private JButton	btnSave;
	
	private TextAreaPanel txtArea;
	
	private SelectBoxPanel sltGrade;
	
	private String[] gradeList = {"1", "2", "3", "4", "5"};
	private Object[] list;
	
	public RentalReviewPanel(JDialog frame, int userId, int rentalDetailId) {
		this.frame = frame;
		this.userId = userId;
		this.rentalDetailId = rentalDetailId;
		initialization();
	}
	
	public void initialization() {
		setLayout(null);
		setDataDrawing();
		setSwingDrawing();
	}
	
	private void setSwingDrawing() {
		btnSave = new JButton("Save");
		labelProduct.setBounds(20, 20, 100, 30);
		labelGameName.setBounds(20, 60, 300, 30);
		labelRentalDate.setBounds(20, 100, 350, 30);
		labelRentalStatus.setBounds(20, 140, 180, 30);
		labelTotalFee.setBounds(20, 180, 100, 30);
		labelGrade.setBounds(20, 220, 80, 30);
		labelComment.setBounds(20, 260, 100, 30);
		btnSave.setBounds(20, 410, 350, 30);
		add(labelProduct);
		add(labelGameName);
		add(labelRentalDate);
		add(labelRentalStatus);
		add(labelTotalFee);
		add(labelGrade);
		add(labelComment);
		add(btnSave);
		add(txtArea);
		add(sltGrade);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateReview(Integer.parseInt(sltGrade.getStrSltItem()), txtArea.getText());
			}
		});
	}
	
	private void setDataDrawing() {
		new SQLCall(
				"{ call rental_pack.get_rental_detail_id_info(?, ?) }",
				cs -> {
					try {
						cs.registerOutParameter(2, OracleTypes.CURSOR);
						cs.setInt(1, rentalDetailId);
						cs.execute();
						ResultSet resultSet = (ResultSet) cs.getObject(2);
						while (resultSet.next()) {
							list = new Object[] {
									resultSet.getInt(1),
									resultSet.getInt(2),
									resultSet.getInt(3),
									resultSet.getInt(4),
									resultSet.getString(5),
									resultSet.getString(6),
									resultSet.getString(7),
									resultSet.getString(8),
									resultSet.getInt(9),
									resultSet.getString(10),
									resultSet.getInt(11)
							};
							labelProduct = new JLabel("제품: " + resultSet.getInt(4));
							labelGameName = new JLabel("보드게임: " + resultSet.getString(5));
							labelRentalDate = new JLabel("대여 기간: " + resultSet.getString(6) + " ~ " + resultSet.getString(7));
							labelRentalStatus = new JLabel("대여 상태: " + resultSet.getString(10));
							labelGrade = new JLabel("평점:");
							sltGrade = new SelectBoxPanel(25, 220, 100, 30, gradeList);
							labelComment = new JLabel("리뷰:");
							txtArea = new TextAreaPanel(20, 300, 350, 100);
							txtArea.setText(resultSet.getString(8));
							int i = 0;
							for (String str : gradeList) {
								if (str.equals(Integer.toString(resultSet.getInt(9)))) sltGrade.setSltInit(i);
								i++;
							}
							labelTotalFee = new JLabel("총 가격: " + resultSet.getInt(11));
						}
					} catch (SQLException err) {
						System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
						err.printStackTrace();
					}
				});
	}
	
	private void updateReview(int grade, String msg) {
		new SQLCall(
				"{ call rental_pack.update_rental_detail_review(?, ?, ?, ?) }",
				cs -> {
					try {
						cs.registerOutParameter(4, java.sql.Types.INTEGER);
						cs.setInt(1, rentalDetailId);
						cs.setInt(2, grade);
						cs.setString(3, msg);
						cs.execute();
						if (cs.getInt(4) == 1) {
							new Alert("해당 보드게임의 리뷰를 수정했습니다.");
							frame.dispose();
							JFrame sFrame = (JFrame) SwingUtilities.getWindowAncestor(frame);
							MyRentalPanel panel = new MyRentalPanel(sFrame, this.userId);
							sFrame.getContentPane().removeAll();
							sFrame.getContentPane().add(panel);
							sFrame.revalidate();
							sFrame.repaint();
						}
						if (cs.getInt(4) == 0) new Alert("해당 보드게임의 리뷰를 수정하지 못 했습니다.\n다시 시도해 주세요.");
					} catch (SQLException err) {
						System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
						err.printStackTrace();
					}
				});
	}
	
}
