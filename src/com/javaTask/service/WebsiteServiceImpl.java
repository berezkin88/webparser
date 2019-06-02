package com.javaTask.service;

import java.sql.SQLException;
import java.util.List;

import com.javaTask.DAO.WebsiteDAO;
import com.javaTask.entity.Website;

public class WebsiteServiceImpl implements WebsiteService {
	
	@Override
	public void insert(Website website) throws SQLException {
		WebsiteDAO.insert(website);
	}

	@Override
	public List<Website> read(long from, long till) throws SQLException {
		return WebsiteDAO.read(from, till);
	}

	@Override
	public void update(Website website) throws SQLException {
		WebsiteDAO.update(website);
	}

	@Override
	public void delete(Website website) throws SQLException {
		WebsiteDAO.delete(website);
	}

	@Override
	public void createTable(String tname, String tparams) throws SQLException {
		WebsiteDAO.createTable(tname, tparams);
	}

	@Override
	public List<?> readAll() throws SQLException {
		return WebsiteDAO.readAll();
	}

	
}
