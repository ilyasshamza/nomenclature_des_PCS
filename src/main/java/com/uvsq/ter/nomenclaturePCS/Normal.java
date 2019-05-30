package com.uvsq.ter.nomenclaturePCS;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import java.sql.*;

public class Normal extends Moteur {

	@Override
	public void chercher(String mots, JTextArea textArea) {

		if (!mots.isEmpty()) {
			try {
				// 1 get a connection to database
				Connection con = DBConnection.getInstance().getConnection();
				// 2 create a statement

				PreparedStatement prstt = (PreparedStatement) con.prepareStatement(
						"select * from `metiers` where `code` like ? or `libelle` like ? union select * from metiers where code in (select code from correspondance where prop like ?)");

				prstt.setString(1, "%" + mots + "%");
				prstt.setString(2, "%" + mots + "%");
				prstt.setString(3, "%" + mots + "%");
				// 3 Execute sql query
				ResultSet res = prstt.executeQuery();
				// 4 process the result set
				if (!res.next()) {
					int p = JOptionPane.showConfirmDialog(null, "Voulez vous qu'on cherche ensemble votre métier ?",
							"Pouvons nous vous être utile ?", JOptionPane.YES_NO_CANCEL_OPTION);
					if (p == 0) {
						Aide aide = new Aide(mots);
						aide.setVisible(true);
					}
				} else {
					do {
						textArea.setText(textArea.getText() + "code" + res.getString("code") + "--> "
								+ res.getString("libelle") + "\n");
					} while (res.next());
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}

	}
}
