package com.javaTask.appRunner;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

import com.javaTask.entity.User;
import com.javaTask.entity.Website;
import com.javaTask.exceptions.IOExc;
import com.javaTask.exceptions.JAXBExc;
import com.javaTask.service.AddToBasket;
import com.javaTask.service.AppService;
import com.javaTask.service.LoginBot;
import com.javaTask.service.SignUpBot;
import com.javaTask.service.UI;
import com.javaTask.service.WebsiteService;

import javafx.application.Application;

public class Runner {
	
	public static final String PRODUCT_URL = "https://prom.ua/p895411456-igrovoj-noutbu-omen.html";
	public static final Logger LOG = Logger.getLogger(Runner.class.getName());
	
	public static void main(String[] args) {
		Application.launch(UI.class, args);
		
	}
	
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
		User user = new User("Oleksandr", "berezkin88@ukr.net", "Test1234");
		SignUpBot sub = new SignUpBot();
		
		WebDriver web = sub.registerUser(user);
		
		web.quit();
	}
	
	public static void selenLoginFunc() {
		User user = new User("Oleksandr", "berezkin88@ukr.net", "Test1234");
		LoginBot lb = new LoginBot();
		
		WebDriver web = lb.logInUser(user);
		
		web.quit();
	}

	public static void selenAddFunc() {
		String link = "https://prom.ua/p895411456-igrovoj-noutbu-omen.html";
		AddToBasket atb = new AddToBasket();
		
		WebDriver web = atb.addToCart(link, atb.getWebDriver());
		
		web.quit();
	}
	
	public static void getDataFromWebAndSaveToDB(String url) {
		WebsiteService ws = new WebsiteService();
		
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
