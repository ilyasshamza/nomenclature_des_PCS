package com.uvsq.ter.nomenclaturePCS;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

import javax.swing.UIManager;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.ListSelectionModel;

public class Aide extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 * 
	 * 
	 * /** Create the frame.
	 */
	public Aide(final String mots) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 100, 918, 604);
		setTitle("Aide:professions et cat\u00E9gories socioprofessionnelles");
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Pour centrer le JFrame
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);

		JLabel lblVotreMtierE = new JLabel(
				"Merci de selectionner le groupe socio-professionel dont se trouve votre m\u00E9tier :\r\n");
		lblVotreMtierE.setForeground(Color.WHITE);
		lblVotreMtierE.setFont(new Font("Trebuchet MS", Font.BOLD, 17));
		lblVotreMtierE.setBounds(36, 35, 682, 34);
		contentPane.add(lblVotreMtierE);

		final JList<String> list = new JList<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		list.setForeground(Color.WHITE);
		list.setBackground(Color.DARK_GRAY);
		String[] values = new String[] { "1|  Agriculteurs exploitants",
				"2|  Artisans," + " commer\u00E7ants et chefs d'entreprise",
				"3|  Cadres et professions intellectuelles sup\u00E9rieures", "4|  Professions Interm\u00E9diaires",
				"5|  Employ\u00E9s", "6|  Ouvriers", "7|  Retrait\u00E9s",
				"8|  Autres personnes sans activit\u00E9e professionnelle" };

		list.setSelectionForeground(Color.WHITE);
		list.setFont(new Font("Georgia", Font.PLAIN, 18));

		list.setListData(values);

		list.setBounds(45, 101, 810, 229);
		contentPane.add(list);

		JButton Annuler = new JButton("Annuler");
		Annuler.setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		Annuler.setFont(new Font("Times New Roman", Font.BOLD, 18));
		Annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		Annuler.setBounds(752, 458, 103, 37);
		contentPane.add(Annuler);

		JButton Suivant = new JButton("Suivant");
		Suivant.setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		Suivant.setFont(new Font("Times New Roman", Font.BOLD, 18));
		Suivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Categories cat = new Categories(list.getSelectedIndex() + 1, mots);
				cat.setVisible(true);
				dispose();
			}
		});
		Suivant.setBounds(627, 458, 110, 37);
		contentPane.add(Suivant);

		JButton precedent = new JButton("Précédent");
		precedent.setFont(new Font("Times New Roman", Font.BOLD, 18));
		precedent.setBackground(UIManager.getColor("CheckBoxMenuItem.selectionBackground"));
		precedent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}

		});
		precedent.setBounds(495, 459, 117, 36);
		contentPane.add(precedent);
	}
}
