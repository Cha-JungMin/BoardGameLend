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

public class RentalStatusUpdatePanel extends JPanel {

	private int		userId;
	private int		rentalDetailId;
	private JDialog	frame;
	
	private JLabel	labelProduct, labelGameName, labelRentalDate,
					labelRentalStatus, labelTotalFee;
	private JButton	btnSave;
	
	private SelectBoxPanel sltRentalStatus;
	
	private String[] rentalStatusValue = {"대여중", "대여완료", "대여예정", "대여취소"};
	private Object[] list;
	
	public RentalStatusUpdatePanel(JDialog frame, int userId, int rentalDetailId) {
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
		labelRentalStatus.setBounds(20, 140, 100, 30);
		labelTotalFee.setBounds(20, 180, 100, 30);
		btnSave.setBounds(20, 220, 350, 30);
		add(labelProduct);
		add(labelGameName);
		add(labelRentalDate);
		add(labelRentalStatus);
		add(labelTotalFee);
		add(btnSave);
		add(sltRentalStatus);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sltRentalStatus.getStrSltItem().equals("대여취소")) {
					removeStatement();
					return;
				}
				updateStatement(sltRentalStatus.getStrSltItem());
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
							labelRentalStatus = new JLabel("대여 상태: ");
							sltRentalStatus = new SelectBoxPanel(70, 140, 100, 30, rentalStatusValue);
							int i = 0;
							for (String str : rentalStatusValue) {
								if (str.equals(resultSet.getString(10))) sltRentalStatus.setSltInit(i);
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
	
	private void updateStatement(String str) {
		new SQLCall(
				"{ call rental_pack.update_rental_detail_statement(?, ?, ?) }",
				cs -> {
					try {
						cs.registerOutParameter(3, java.sql.Types.INTEGER);
						cs.setInt(1, rentalDetailId);
						cs.setString(2, str);
						cs.execute();
						if (cs.getInt(3) == 1) {
							new Alert("해당 보드게임의 대여 상태를 수정했습니다.");
							frame.dispose();
							JFrame sFrame = (JFrame) SwingUtilities.getWindowAncestor(frame);
							MyRentalPanel panel = new MyRentalPanel(sFrame, this.userId);
							sFrame.getContentPane().removeAll();
							sFrame.getContentPane().add(panel);
							sFrame.revalidate();
							sFrame.repaint();
						}
						if (cs.getInt(3) == 0) new Alert("해당 보드게임의 대여 상태를 수정하지 못 했습니다.\n다시 시도해 주세요.");
					} catch (SQLException err) {
						System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
						err.printStackTrace();
					}
				});
	}
	
	private void removeStatement() {
		new SQLCall(
				"{ call rental_pack.delete_rental_detail(?, ?) }",
				cs -> {
					try {
						cs.registerOutParameter(2, java.sql.Types.INTEGER);
						cs.setInt(1, rentalDetailId);
						cs.execute();
						if (cs.getInt(2) == 1) {
							new Alert("해당 보드게임의 대여 취소가 되었습니다.");
							frame.dispose();
							JFrame sFrame = (JFrame) SwingUtilities.getWindowAncestor(frame);
							MyRentalPanel panel = new MyRentalPanel(sFrame, this.userId);
							sFrame.getContentPane().removeAll();
							sFrame.getContentPane().add(panel);
							sFrame.revalidate();
							sFrame.repaint();
						}
						if (cs.getInt(2) == 0) new Alert("해당 보드게임의 대여 취소를 못 했습니다.\n다시 시도해 주세요.");
					} catch (SQLException err) {
						System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
						err.printStackTrace();
					}
				});
	}
}
