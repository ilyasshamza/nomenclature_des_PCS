package com.uvsq.ter.nomenclaturePCS;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;

public class Categories extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private java.sql.Statement stmt;
	private ResultSet rs;
	private JTable table;
	private JLabel lblVotreMtierSe;
	private Connection conn;
	private int etat;
	private JProgressBar progressBar;
	private ArrayList<String> codes;
	private final String tables[] = { "categorie", "professions", "metiers" };
	private final String messagelabel[] = { "Merci de selectionner la catégorie dont se trouve votre métier: ",
			"Merci de selectionner la profession dont se trouve votre métier:",
			"Vous trouverez votre métier dans les propositions au dessous :" };

	/**
	 * Create the frame.
	 */
	public Categories(int groupe, final String mots) {
		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 918, 604);
		setTitle("Aide: professions et cat\u00E9gories socioprofessionnelles");
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		// Pour centrer le JFrame
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
		codes = new ArrayList<String>();
		codes.add(Integer.toString(groupe));
		etat = 0;
		try {
			conn = DBConnection.getInstance().getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		lblVotreMtierSe = new JLabel("Merci de selectionner la catégorie dont se trouve votre métier: ");
		lblVotreMtierSe.setForeground(Color.WHITE);
		lblVotreMtierSe.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		lblVotreMtierSe.setBounds(32, 12, 543, 32);
		contentPane.add(lblVotreMtierSe);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.DARK_GRAY);
		scrollPane.setFont(new Font("Tahoma", Font.PLAIN, 16));

		scrollPane.setBounds(15, 97, 882, 451);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));

		scrollPane.setViewportView(table);

		JLabel lblSuiv = new JLabel("");
		lblSuiv.setIcon(new ImageIcon(Categories.class.getResource("/next1.png")));
		lblSuiv.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (table.getSelectedRow() >= 0) {
					String code = (table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
					String libelle = (table.getModel().getValueAt(table.getSelectedRow(), 1).toString());
					// String g=selectedCode();
					if (etat == 2) {
						table.removeAll();
						afficherAll(tables[etat], codes.get(etat));

						int p = JOptionPane.showConfirmDialog(null, mots + "=" + libelle + " ?",
								"Merci pour votre indication", JOptionPane.YES_NO_CANCEL_OPTION);
						if (p == 0) {
							classification(mots, code);

						}
						dispose();

					} else {
						if (etat == 0) {
							etat++;
							codes.add(selectedCode());
							progressBar.setValue(34 * (etat + 1));
							lblVotreMtierSe.setText(messagelabel[etat]);
							table.removeAll();
							afficherAll(codes.get(etat));

						} else {
							etat++;
							codes.add(selectedCode());
							progressBar.setValue(34 * (etat + 1));
							lblVotreMtierSe.setText(messagelabel[etat]);
							table.removeAll();
							afficherAll(tables[etat], codes.get(etat));
						}
					}
				}

			}
		});

		lblSuiv.setBounds(789, 12, 69, 39);
		contentPane.add(lblSuiv);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Categories.class.getResource("/undo.png")));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (etat == 0) {
					codes.remove(0);
					Aide a = new Aide(mots);
					a.setVisible(true);
					dispose();
				} else {
					if (etat == 1) {
						codes.remove(etat);
						etat--;
						progressBar.setValue(34 * (etat + 1));
						lblVotreMtierSe.setText(messagelabel[etat]);
						table.removeAll();
						afficherAll(tables[etat], codes.get(etat));

					} else {
						codes.remove(etat);
						etat--;
						progressBar.setValue(34 * (etat + 1));
						lblVotreMtierSe.setText(messagelabel[etat]);
						table.removeAll();
						afficherAll(codes.get(etat));
					}
				}
			}
		});
		label.setBounds(705, 12, 69, 39);
		contentPane.add(label);

		progressBar = new JProgressBar();
		progressBar.setForeground(new Color(50, 205, 50));
		progressBar.setValue(34);
		progressBar.setBounds(15, 74, 882, 14);
		contentPane.add(progressBar);

		afficherAll(tables[etat], Integer.toString(groupe));
	}

	public void afficherAll(String dbtable, String code) {

		String sql = "select CODE,LIBELLE from " + dbtable + "  where code like  \'" + code + "%\'";

		try {
			stmt =  conn.createStatement();
			rs = stmt.executeQuery(sql);
			table.setModel(DbUtils.resultSetToTableModel(rs));
			table.getColumnModel().getColumn(0).setPreferredWidth(128);
			table.getColumnModel().getColumn(1).setPreferredWidth(477);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void afficherAll(String code) {

		String sql = "SELECT * FROM professions WHERE CODE >=" + code
				+ "  and Code < (SELECT code from categorie WHERE CODE > " + code + " LIMIT 1)";
		try {
			stmt =  conn.createStatement();
			rs = stmt.executeQuery(sql);
			table.setModel(DbUtils.resultSetToTableModel(rs));
			table.getColumnModel().getColumn(0).setPreferredWidth(128);
			table.getColumnModel().getColumn(1).setPreferredWidth(477);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public String selectedCode() {
		int row = table.getSelectedRow();
		String Table_click = (table.getModel().getValueAt(row, 0).toString());
		return Table_click;
	}

	private void classification(String mots, String code) {
		// String sqlprop = "INSERT INTO `proposition`(`prop`) VALUES (?)";
		String sqlprop = "INSERT IGNORE INTO proposition (prop)  VALUES  (?)";
		String sqlcorresp = "INSERT INTO `correspondance`(`code`, `prop`) VALUES (?,?)";

		try {

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt1 = conn.prepareStatement(sqlprop);
			preparedStmt1.setString(1, mots);

			PreparedStatement preparedStmt2 = conn.prepareStatement(sqlcorresp);
			preparedStmt2.setString(1, code);
			preparedStmt2.setString(2, mots);

			// execute the preparedstatement
			preparedStmt1.execute();
			preparedStmt2.execute();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
