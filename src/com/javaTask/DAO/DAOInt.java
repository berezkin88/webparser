package com.javaTask.DAO;

import java.sql.SQLException;
import java.util.List;

import com.javaTask.entity.Website;
import com.javaTask.exceptions.IOExc;

public interface DAOInt {
	
	void createTable(String tname, String tparams) throws SQLException;
	void insert(Website website) throws SQLException;
	List<Website> read(long from, long till) throws SQLException;
	List<?> readAll() throws SQLException;
	void update(Website website) throws SQLException;
	void delete(Website website) throws SQLException;
}
