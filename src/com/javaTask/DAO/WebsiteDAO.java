package com.javaTask.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaTask.DAO.connection.ConnectionAndStatementFactory;
import com.javaTask.entity.Website;
import com.javaTask.exceptions.IOExc;

public class WebsiteDAO {

	private static final Logger LOG = Logger.getLogger(WebsiteDAO.class.getName());
	
	private static Statement statement;
	private static Connection connection;

	public static void createTable(String tname, String tparams) throws SQLException {
		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("creating table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String sql = "CREATE TABLE " + tname + " ( " + tparams + " ) ";

			statement.execute(sql);
			LOG.info("table created");
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	public static void insert(Website website) throws SQLException {
		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("inserting into table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			website.setTimestamp(System.currentTimeMillis());

			String sql = "INSERT INTO websites (login, pass, url, specs, timestmp) VALUES ( '" + website.getLogin()
					+ "', '" + website.getPassword() + "', '" + website.getUrl() + "', '" + specs(website) + "', "
					+ website.getTimestamp() + ")";

			statement.execute(sql);
			LOG.info("insertion completed");
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	public static List<Website> read(long from, long till) throws SQLException {
		List<Website> result = new ArrayList<>();

		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("reading from WEBSITES table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "SELECT * FROM WEBSITES WHERE timestmp BETWEEN " + from + " AND " + till;

			ResultSet resultSet = statement.executeQuery(SQL);
			LOG.info("reading completed");

			while (resultSet.next()) {
				try {
					result.add(createWebsite(resultSet));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		return result;
	}

	public static List<Website> readAll() throws SQLException {
		List<Website> result = new ArrayList<>();

		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("reading from WEBSITES table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "SELECT * FROM WEBSITES";

			ResultSet resultSet = statement.executeQuery(SQL);
			LOG.info("reading completed");

			while (resultSet.next()) {
				try {
					result.add(createWebsite(resultSet));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		return result;
	}

	public static void update(Website website) throws SQLException {
		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("updating table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "UPDATE WEBSITES SET specs = '" + specs(website) + "' WHERE url LIKE '" + website.getUrl() + "'";

			statement.execute(SQL);
			LOG.info("update completed");
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	public static void delete(Website website) throws SQLException {
		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("deleting from table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "DELETE FROM WEBSITES WHERE url LIKE '" + website.getUrl() + "'";

			statement.execute(SQL);
			LOG.info("deleting completed");
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	private static String specs(Website website) {
		JSONObject obj = new JSONObject(website.getSpecs());

		return obj.toString();
	}

	private static Website createWebsite(ResultSet resultSet) throws SQLException, IOExc {
		Website web = new Website();
		web.setLogin(resultSet.getString("login"));
		web.setPassword(resultSet.getString("pass"));
		web.setUrl(resultSet.getString("url"));
		web.setSpecs(makeMapFromJSON(resultSet.getString("specs")));
		return web;
	}

	private static Map<String, String> makeMapFromJSON(String string) {
		ObjectMapper mapper = new ObjectMapper();

		Map<String, String> specs = null;
		try {
			specs = mapper.readValue(string, new TypeReference<Map<String, String>>() {
			});
		} catch (IOException e) {
			LOG.info("Exception thrown when converting string to Map<>" + e.toString());
		}

		return specs;
	}
}
