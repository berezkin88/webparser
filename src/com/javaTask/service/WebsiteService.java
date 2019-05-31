package com.javaTask.service;

import java.sql.SQLException;
import java.util.List;

import com.javaTask.DAO.DAOImpl;
import com.javaTask.entity.Website;

public class WebsiteService extends DAOImpl {

	@Override
	public void insert(Website website) throws SQLException {
		super.insert(website);
	}

	@Override
	public List<Website> read(long from, long till) throws SQLException {
		return super.read(from, till);
	}

	@Override
	public void update(Website website) throws SQLException {
		super.update(website);
	}

	@Override
	public void delete(Website website) throws SQLException {
		super.delete(website);
	}

	
}
