package com.uvsq.ter.nomenclaturePCS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Levenshteine extends Moteur {

	@Override
	public void chercher(String mots, JTextArea textArea) {

		if (!mots.isEmpty()) {
			try {
				// 1 get a connection to database
				Connection con = DBConnection.getInstance().getConnection();
				// 2 create a statement

				PreparedStatement prstt = (PreparedStatement) con
						.prepareStatement("SELECT * FROM groupe WHERE levenshtein(LIBELLE,?)<5");

				prstt.setString(1, mots);
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
