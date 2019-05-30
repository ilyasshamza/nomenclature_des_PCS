package com.uvsq.ter.nomenclaturePCS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Lucene extends Moteur {
	private String fileindex = "index";

	private  IndexSearcher createSearcher() throws IOException {
		Directory dir = FSDirectory.open(Paths.get(fileindex));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		return searcher;
	}

	private TopDocs searchByFiled(String champ, String keyword, IndexSearcher searcher, int topdocs) throws Exception {
		QueryParser qp = new QueryParser(champ, new StandardAnalyzer());
		Query query = qp.parse(keyword);
		TopDocs hits = searcher.search(query, topdocs);
		return hits;
	}

	private Map<String, String> chercher(String champ, String motcle, int topdocs) throws Exception {
		IndexSearcher searcher = createSearcher();
		TopDocs foundDocs = searchByFiled(champ, motcle, searcher, topdocs);

		// System.out.println("Total Results :: " + foundDocs.totalHits);
		TreeMap<String, String> list = new TreeMap<String, String>();
		for (ScoreDoc sd : foundDocs.scoreDocs) {
			Document d = searcher.doc(sd.doc);
			list.put(String.format(d.get("code")), String.format(d.get("LIBELLE")));
		}
		return list;
	}

	private Document createDocument(String code, String libelle) {

		Document document = new Document();
		try {
			document.add(new StringField("code", code, Field.Store.YES));
			document.add(new TextField("LIBELLE", libelle, Field.Store.YES));

		} catch (Exception e) {
			return null;
		}
		return document;
	}

	private IndexWriter createWriter(String indecderectory) throws IOException {
		FSDirectory dir = FSDirectory.open(Paths.get(indecderectory));
		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
		IndexWriter writer = new IndexWriter(dir, config);
		return writer;
	}

	/**
	 * indexer la base de donnée avec lucene dans le fichier index
	 */
	public void index() {

		File f = new File(fileindex);
		if (f.isDirectory() && f.exists()) {

			f.delete();
			try {

				IndexWriter writer = createWriter(fileindex);

				ArrayList<Document> documents = new ArrayList<Document>();
				try {
					// 1 get a connection to database
					Connection con = DBConnection.getInstance().getConnection();
					// 2 create a statement

					PreparedStatement prstt = (PreparedStatement) con.prepareStatement("select * from `metiers` ");

					// 3 Execute sql query
					ResultSet res = prstt.executeQuery();
					// 4 process the result set

					while (res.next()) {
						Document document = createDocument(res.getString("code"), res.getString("libelle"));
						if (document != null) {
							documents.add(document);
						}

						System.out.print("* ");

					}

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				// Let's clean everything first
				writer.deleteAll();
				writer.addDocuments(documents);
				writer.commit();
				writer.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());

			}
		}
	}

	@Override
	public void chercher(String mots, JTextArea textArea) {

		// pour indexer la base de donnee
		// index();

		Map<String, String> list;

		try {

			list = chercher("LIBELLE", mots, 10);

			if (list.size() != 0) {
				for (Map.Entry<String, String> entry : list.entrySet()) {
					textArea.setText(textArea.getText() + "code" + entry.getKey() + "--> " + entry.getValue() + "\n");

				}
			} else {
				int p = JOptionPane.showConfirmDialog(null, "Voulez vous qu'on cherche ensemble votre métier ?",
						"Pouvons nous vous être utile ?", JOptionPane.YES_NO_CANCEL_OPTION);
				if (p == 0) {
					Aide aide = new Aide(mots);
					aide.setVisible(true);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

	}

}
