package com.javaTask.DAO.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class ConnectionAndStatementFactory {
	private static final Logger LOG = Logger.getLogger(ConnectionAndStatementFactory.class.getName());

	private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/webparser";
	private static final String JDBC_DRIVER = "org.postgresql.Driver";

	private static final String USER = "postgres";
	private static final String PASSWORD = "root";

	public static Connection connecting() {
		Connection con = null;
		
		try {
			LOG.info("Registering JDBC driver...");
			Class.forName(JDBC_DRIVER);

			LOG.info("Connecting to DB...");
			con = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			LOG.info("Exception occures when registering file in WebsiteDAO \n" + e.toString());
		} catch (SQLException e) {
			LOG.info("Exception occures when connecting to DB in WebsiteDAO \n" + e.toString());
		}
		
		return con;
	}

	public static Statement createStatement(Connection connection) {
		Statement state = null;

		try {
			state = connection.createStatement();
		} catch (SQLException e) {
			LOG.info("Exception occures when initializing statement in createDatabase" + e.toString());
		}

		return state;
	}
}
