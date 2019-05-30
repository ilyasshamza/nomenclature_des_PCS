package com.uvsq.ter.nomenclaturePCS;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class DBConnection {

	private static DBConnection instance = null;
	private Connection connection;

	private DBConnection() throws Exception {
		Properties pr = new Properties();
		pr.load(new FileReader("dbinfo.properties"));
		this.connection = DriverManager.getConnection(pr.getProperty("url"), pr.getProperty("user"),
				pr.getProperty("password"));
	}

	public Connection getConnection() {
		return connection;
	}

	public static DBConnection getInstance() throws Exception {
		if (instance == null) {
			instance = new DBConnection();
		} else if (instance.getConnection().isClosed()) {
			instance = new DBConnection();
		}

		return instance;
	}
}
