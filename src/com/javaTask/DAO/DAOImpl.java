package com.javaTask.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaTask.entity.Website;
import com.javaTask.exceptions.CNFExc;
import com.javaTask.exceptions.IOExc;
import com.javaTask.exceptions.SQLExc;

public class DAOImpl implements DAOInt {

	private static final Logger LOG = Logger.getLogger(DAOImpl.class.getName());

	private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/webparser";
	private static final String JDBC_DRIVER = "org.postgresql.Driver";

	private static final String USER = "postgres";
	private static final String PASSWORD = "root";

	private Connection connection;
	private Statement statement;

	@Override
	public void createTable(String tname, String tparams) throws SQLException {
		try {
			connecting();

			LOG.info("creating table...");
			statement = createState(connection);

			String sql = "CREATE TABLE " + tname + " ( " + tparams + " ) ";

			statement.execute(sql);
			LOG.info("table created");
		} catch (SQLExc | CNFExc e) {
			LOG.info("Exception occures in createTable method");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Override
	public void insert(Website website) throws SQLException {
		try {
			connecting();

			LOG.info("inserting into table...");
			statement = createState(connection);
			
			website.setTimestamp(System.currentTimeMillis());

			String sql = "INSERT INTO websites (login, pass, url, specs, timestmp) VALUES ( '" + website.getLogin() + "', '" + website.getPassword() + "', '" + website.getUrl() + "', '" + specs(website) + "', " + website.getTimestamp() + ")";
			
			statement.execute(sql);
			LOG.info("insertion completed");
		} catch (SQLExc | CNFExc e) {
			LOG.info("Exception occures in insert method");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Override
	public List<Website> read(long from, long till) throws SQLException {
		List<Website> result = new ArrayList<>();
		
		try {
			connecting();

			LOG.info("reading from WEBSITES table...");
			statement = createState(connection);

			String SQL = "SELECT * FROM WEBSITES WHERE timestmp BETWEEN " + from + " AND " + till;

			ResultSet resultSet = statement.executeQuery(SQL);
			LOG.info("reading completed");
			
			while(resultSet.next()) {
				try {
					result.add(createWebsite(resultSet));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (SQLExc | CNFExc e) {
			LOG.info("Exception occures in read method");
			e.printStackTrace();
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

	@Override
	public List<Website> readAll() throws SQLException {
		List<Website> result = new ArrayList<>();
		
		try {
			connecting();

			LOG.info("reading from WEBSITES table...");
			statement = createState(connection);

			String SQL = "SELECT * FROM WEBSITES";

			ResultSet resultSet = statement.executeQuery(SQL);
			LOG.info("reading completed");
			
			while(resultSet.next()) {
				try {
					result.add(createWebsite(resultSet));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (SQLExc | CNFExc e) {
			LOG.info("Exception occures in readAll method");
			e.printStackTrace();
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

	@Override
	public void update(Website website) throws SQLException {
		try {
			connecting();

			LOG.info("updating table...");
			statement = createState(connection);

			String SQL = "UPDATE WEBSITES SET specs = " + specs(website) + " WHERE url LIKE " + website.getUrl();

			statement.execute(SQL);
			LOG.info("update completed");
			
		} catch (SQLExc | CNFExc e) {
			LOG.info("Exception occures in readAll method");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Override
	public void delete(Website website) throws SQLException {
		try {
			connecting();

			LOG.info("deleting from table...");
			statement = createState(connection);

			String SQL = "DELETE FROM WEBSITES WHERE url LIKE " + website.getUrl();

			statement.execute(SQL);
			LOG.info("deleting completed");
			
		} catch (SQLExc | CNFExc e) {
			LOG.info("Exception occures in readAll method");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	private void connecting() throws CNFExc, SQLExc {

		try {
			LOG.info("Registering JDBC driver...");
			Class.forName(JDBC_DRIVER);

			LOG.info("Connecting to DB...");
			connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			throw new CNFExc("Exception occures when registering file in DAOImpl \n" + e.toString());
		} catch (SQLException e) {
			throw new SQLExc("Exception occures when connecting to DB in DAOImpl \n" + e.toString());
		}
	}

	private Statement createState(Connection connection) throws SQLExc {
		Statement state;
		
		try {
			state = connection.createStatement();
		} catch (SQLException e) {
			throw new SQLExc("Exception occures when initializing statement in createDatabase" + e.toString());
		}
 
		return state;
	}
	
	private String specs(Website website) {
		JSONObject obj = new JSONObject(website.getSpecs());
		
		return obj.toString();
	}
	
	private Website createWebsite(ResultSet resultSet) throws SQLException, IOExc {
		Website web = new Website();
		web.setLogin(resultSet.getString("login"));
		web.setPassword(resultSet.getString("pass"));
		web.setUrl(resultSet.getString("url"));
		web.setSpecs(makeMapFromJSON(resultSet.getString("specs")));
		return web;
	}

	private Map<String, String> makeMapFromJSON(String string) throws IOExc {
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, String> specs;
		try {
			specs = mapper.readValue(string, new TypeReference<Map<String, String>>() {
			});
		} catch (IOException e) {
			throw new IOExc("Exception thrown when converting string to Map<>" + e.toString());
		}
		
		return specs;
	}
}
