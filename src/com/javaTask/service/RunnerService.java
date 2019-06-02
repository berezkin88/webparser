package com.javaTask.service;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

import com.javaTask.entity.User;
import com.javaTask.entity.Website;
import com.javaTask.exceptions.IOExc;
import com.javaTask.exceptions.JAXBExc;
import com.javaTask.service.bots.AddToBasketBot;
import com.javaTask.service.bots.LoginAndSignupBot;

public class RunnerService {
	public static final String PRODUCT_URL = "https://prom.ua/p895411456-igrovoj-noutbu-omen.html";
	public static final User USER = new User("Oleksandr", "berezkin88@ukr.net", "Test1234");
	public static final Logger LOG = Logger.getLogger(RunnerService.class.getName());
	
	public static void jsoupFunc() {
		Website web = AppService.collectData(PRODUCT_URL);

		try {
			AppService.writeToJSON(web);
			AppService.writeToXML(web);
		} catch (IOExc | JAXBExc e) {
			e.printStackTrace();
		}
	}
	
	public static void selenFunc() {
		LoginAndSignupBot lsb = new LoginAndSignupBot();
		
		WebDriver web = lsb.registerUser(USER);
		
		web.quit();
	}
	
	public static void selenLoginFunc() {
		LoginAndSignupBot lsb = new LoginAndSignupBot();
		
		WebDriver web = lsb.logInUser(USER);
		
		web.quit();
	}

	public static void selenAddFunc() {
		String link = "https://prom.ua/p895411456-igrovoj-noutbu-omen.html";
		AddToBasketBot atbb = new AddToBasketBot();
		
		WebDriver web = atbb.addToCart(link, atbb.getWebDriver());
		
		web.quit();
	}
	
	public static void getDataFromWebAndSaveToDB(String url) {
		WebsiteService ws = new WebsiteServiceImpl();
		
		Website web = AppService.collectData(url);
		User user = new User("Oleksandr", "berezkin88@ukr.net", "Test1234");
		
		web.setLogin(user.getName());
		web.setPassword(user.getPassword());
		web.setTimestamp(System.currentTimeMillis());
		
		try {
			ws.insert(web);
		} catch (SQLException e) {
			LOG.info("Exception occured in getDataFromWebAndSaveToDB() method");
			e.printStackTrace();
		}
	}
}
