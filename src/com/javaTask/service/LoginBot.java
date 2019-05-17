package com.javaTask.service;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.javaTask.entity.User;

public class LoginBot {

	private static final Logger LOG = Logger.getLogger(LoginBot.class.getName());

	private static final String SEPARATOR = File.separator;
	private static final String USER_DIR = System.getProperty("user.dir");
	private static final String DRIVER_PATH = USER_DIR + SEPARATOR + "lib" + SEPARATOR + "geckodriver-v0.24.0-win64"
			+ SEPARATOR + "geckodriver.exe";
	private static final String BASE_URL = "https://prom.ua/";

	public WebDriver getWebDriver() {

		LOG.info("Launching the Firefox driver");

		System.setProperty("webdriver.gecko.driver", DRIVER_PATH);

		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);
		// somehow this driver constructor is marked as depricated on my end??
		WebDriver driver = new FirefoxDriver(options);
//		WebDriver driver = new FirefoxDriver();

		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(45, TimeUnit.SECONDS);

		return driver;
	}

	public WebDriver logInUser(User user) {

		LOG.info("Starting login process");

		WebDriver driver = getWebDriver();
		driver.get(BASE_URL);
		Timer.waitSeconds(5);

		LOG.info("getting login link");
		WebElement loginLink = driver.findElement(By.cssSelector("[data-qaid=\"auth_element\"]"));
		String link = loginLink.getAttribute("href");

		Timer.waitSeconds(1);

		driver.get(link);
		Timer.waitSeconds(15);

		WebElement emailField = driver.findElement(By.cssSelector("[data-qaid=\"phone-email-input-field\"]"));
		WebElement passwordlField = driver.findElement(By.cssSelector("[data-qaid=\"password-input-field\"]"));
		WebElement submitElement = driver.findElement(By.cssSelector("[data-qaid=\"submit-login-button\"]"));

		LOG.info("about to insert credentials");
		emailField.sendKeys(user.getEmail());
		passwordlField.sendKeys(user.getPassword());

		LOG.info("about to submit");
		submitElement.submit();
		Timer.waitSeconds(6);

		driver.get(BASE_URL);
		
		Timer.waitSeconds(6);

		LOG.info("Login completed");

		return driver;
	}

}
