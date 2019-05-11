package com.javaTask.appRunner;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

import com.javaTask.entity.User;
import com.javaTask.entity.Website;
import com.javaTask.exceptions.IOExc;
import com.javaTask.exceptions.JAXBExc;
import com.javaTask.service.AppService;
import com.javaTask.service.SignUpBot;
import com.javaTask.service.UI;

import javafx.application.Application;

public class Runner {
	
	public static final String PRODUCT_URL = "https://prom.ua/p895411456-igrovoj-noutbu-omen.html";
	public static final Logger LOG = Logger.getLogger(Runner.class.getName());
	
	public static void main(String[] args) {
		Application.launch(UI.class, args);
		
		//TODO 3 tabs
		//TODO first for searching goods
		//TODO second for signup functionality
		//TODO third adding good to the basket after logging in and inserting good sku
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
}
