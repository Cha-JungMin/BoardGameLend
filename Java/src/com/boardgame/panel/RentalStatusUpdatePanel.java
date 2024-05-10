package com.boardgame.panel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.boardgame.db.SQLCall;

import oracle.jdbc.internal.OracleTypes;

public class RentalStatusUpdatePanel extends JPanel {

	private int		userId;
	private int		rentalDetailId;
	private JDialog	frame;
	
	private JLabel	labelProduct, labelGameName, labelRentalDate,
					labelRentalStatus, labelTotalFee;
	
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
		labelProduct.setBounds(20, 20, 100, 30);
		labelGameName.setBounds(20, 60, 100, 30);
		labelRentalDate.setBounds(20, 100, 350, 30);
		labelRentalStatus.setBounds(20, 140, 100, 30);
		labelTotalFee.setBounds(20, 180, 100, 30);
		add(labelProduct);
		add(labelGameName);
		add(labelRentalDate);
		add(labelRentalStatus);
		add(labelTotalFee);
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
							System.out.println(resultSet.getInt(1));
							System.out.println(resultSet.getInt(2));
							System.out.println(resultSet.getInt(3));
							labelProduct = new JLabel("제품: " + resultSet.getInt(4));
							labelGameName = new JLabel("보드게임: " + resultSet.getString(5));
							labelRentalDate = new JLabel("대여 기간: " + resultSet.getString(6) + " ~ " + resultSet.getString(7));
							System.out.println(resultSet.getString(8));
							System.out.println(resultSet.getInt(9));
							labelRentalStatus = new JLabel("대여 상태: " + resultSet.getString(10));
							labelTotalFee = new JLabel("총 가격: " + resultSet.getInt(11));
						}
					} catch (SQLException err) {
						System.err.format("SQL State: %s\n%s", err.getSQLState(), err.getMessage());
						err.printStackTrace();
					}
				});
	}
	
}
