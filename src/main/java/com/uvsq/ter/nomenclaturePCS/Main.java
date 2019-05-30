package com.uvsq.ter.nomenclaturePCS;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.io.File;
import java.util.Enumeration;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JRadioButton;

public class Main extends JFrame {

	/**
	 * 
	 */
	private Moteur moteur;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	private String imagefolder = System.getProperty("user.dir") + File.separator + "images" + File.separator;
	private JLabel lblNewLabel;
	private JLabel label_1;
	private ButtonGroup type;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setResizable(false);
		setBounds(new Rectangle(100, 100, 918, 604));
		setIconImage(Toolkit.getDefaultToolkit().getImage(imagefolder + "search.png"));
		setTitle("Recherche: Professions et cat\u00E9gories socioprofessionnelles");
		// moteur=new Normal();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(350, 100, 1006, 716);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setForeground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// Pour centrer le JFrame
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				moteur = factory(type);
				if (moteur != null) {
					textArea.setText("");
					moteur.chercher(textField.getText(), textArea);
				}
			}
		});

		textField.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();

		JLabel label = new JLabel("");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				moteur = factory(type);
				textArea.setText("");
				moteur.chercher(textField.getText(), textArea);

			}
		});
		label.setIcon(new ImageIcon(("images/search.png")));
		label.setLabelFor(textField);

		lblNewLabel = new JLabel("Nomenclature des professions et catégories socioprofessionnelles");
		lblNewLabel.setForeground(SystemColor.inactiveCaption);
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 23));

		label_1 = new JLabel("");
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!textField.getText().isEmpty()) {
					int p = JOptionPane.showConfirmDialog(null, "Voulez vous qu'on cherche ensemble votre métier ?",
							"Pouvons nous vous être utile ?", JOptionPane.YES_NO_CANCEL_OPTION);
					if (p == 0) {
						Aide aide = new Aide(textField.getText());
						aide.setVisible(true);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Veuillez saisir le métier que vous chercher ");
					
				}
			}
		});
		label_1.setIcon(new ImageIcon(("images/aide.png")));

		JRadioButton rdbtnNormal = new JRadioButton("Normal");
		rdbtnNormal.setForeground(Color.WHITE);
		rdbtnNormal.setFont(new Font("Vani", Font.BOLD | Font.ITALIC, 15));
		rdbtnNormal.setBackground(Color.DARK_GRAY);

		JRadioButton rdbtnFullTextSql = new JRadioButton("Full Text SQL");
		rdbtnFullTextSql.setForeground(Color.WHITE);
		rdbtnFullTextSql.setFont(new Font("Vani", Font.BOLD | Font.ITALIC, 15));
		rdbtnFullTextSql.setBackground(Color.DARK_GRAY);

		JRadioButton rdbtnFuzzysoundex = new JRadioButton("Fuzzy+Soundex");
		rdbtnFuzzysoundex.setForeground(Color.WHITE);
		rdbtnFuzzysoundex.setFont(new Font("Vani", Font.BOLD | Font.ITALIC, 15));
		rdbtnFuzzysoundex.setBackground(Color.DARK_GRAY);

		JRadioButton rdbtnLevenshtein = new JRadioButton("Levenshtein");
		rdbtnLevenshtein.setForeground(Color.WHITE);
		rdbtnLevenshtein.setFont(new Font("Vani", Font.BOLD | Font.ITALIC, 15));
		rdbtnLevenshtein.setBackground(Color.DARK_GRAY);

		JRadioButton rdbtnLucene = new JRadioButton("Lucene");
		rdbtnLucene.setForeground(Color.WHITE);
		rdbtnLucene.setFont(new Font("Vani", Font.BOLD | Font.ITALIC, 15));
		rdbtnLucene.setBackground(Color.DARK_GRAY);

		type = new ButtonGroup();
		type.add(rdbtnLevenshtein);
		type.add(rdbtnFuzzysoundex);
		type.add(rdbtnFullTextSql);
		type.add(rdbtnNormal);
		type.add(rdbtnLucene);

		JLabel lblModeDeRecherche = new JLabel("Mode de recherche :");
		lblModeDeRecherche.setFont(new Font("Vani", Font.BOLD | Font.ITALIC, 15));
		lblModeDeRecherche.setForeground(Color.WHITE);
		
		JLabel lblRechercher = new JLabel("Rechercher  :");
		lblRechercher.setForeground(new Color(250, 250, 210));
		lblRechercher.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 23));

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(3)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 977, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblModeDeRecherche, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(rdbtnNormal)
									.addGap(18)
									.addComponent(rdbtnFullTextSql)
									.addGap(18)
									.addComponent(rdbtnFuzzysoundex)
									.addGap(18)
									.addComponent(rdbtnLevenshtein)
									.addGap(18)
									.addComponent(rdbtnLucene))
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 850, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblRechercher, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 733, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblRechercher, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
							.addGap(13)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(rdbtnNormal)
										.addComponent(rdbtnFullTextSql)
										.addComponent(rdbtnFuzzysoundex)
										.addComponent(rdbtnLevenshtein)
										.addComponent(rdbtnLucene))
									.addGap(18))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(18)
									.addComponent(lblModeDeRecherche, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.addGap(75)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 462, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Courier New", Font.BOLD, 13));
		scrollPane.setViewportView(textArea);
		contentPane.setLayout(gl_contentPane);
	}

	private Moteur factory(ButtonGroup bg) {
		
		for (Enumeration<AbstractButton> buttons = bg.getElements(); buttons.hasMoreElements();) {

			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				if (button.getText().equals("Normal")) {
					return new Normal();
				}
				if (button.getText().equals("Full Text SQL")) {
					return new FullTextSql();
				}
				if (button.getText().equals("Fuzzy+Soundex")) {
					return new FuzzySoundex();
				}
				if (button.getText().equals("Levenshtein")) {
					return new Levenshteine();
				}
				if (button.getText().equals("Lucene")) {
					return new Lucene();
				}
			}
		}

		return null;

	}

}
