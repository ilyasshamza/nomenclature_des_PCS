package com.uvsq.ter.nomenclaturePCS;

import javax.swing.JTextArea;

public abstract class Moteur {
	/**
	 * Rechercher dans la base de données les professions appropriées
	 * 
	 * @param mots Le mot que nous recherchons
	 * @param textArea l'emplacement de la sortie(resultats)
	 */
	public abstract void chercher(String mots, JTextArea textArea);
}
