package com.javaTask.service;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.javaTask.entity.User;

public class SignUpBot {

	private static final String SEPARATOR = File.separator;
	private static final String USER_DIR = System.getProperty("user.dir");
	private static final String DRIVER_PATH = USER_DIR + SEPARATOR + "lib" + SEPARATOR + "geckodriver-v0.24.0-win64"
			+ SEPARATOR + "geckodriver.exe";
	private static final String BASE_URL = "https://prom.ua/";

	public WebDriver getWebDriver() {

		System.setProperty("webdriver.gecko.driver", DRIVER_PATH);

		WebDriver driver = new FirefoxDriver();

		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(45, TimeUnit.SECONDS);

		return driver;
	}

	public WebDriver registerUser(User user) {

		WebDriver driver = getWebDriver();
		Actions action = new Actions(driver);
		Timer.waitSeconds(3);
		driver.get(BASE_URL);
		Timer.waitSeconds(5);

		Timer.waitSeconds(3);

		WebElement registerBlock = driver.findElement(By.cssSelector("[data-qaid=\"reg_element\"]"));

		// hover over the register block
		action.moveToElement(registerBlock).perform();

		Timer.waitSeconds(3);

		WebElement registerLinkElement = driver.findElement(By.cssSelector("[data-qaid=\"reg_as_buyer_btn\"]"));

		String link = registerLinkElement.getAttribute("href");

		Timer.waitSeconds(3);

		driver.get(link);
		Timer.waitSeconds(25);

		WebElement nameField = driver.findElement(By.cssSelector("[data-qaid=\"name\"]"));
		WebElement emailField = driver.findElement(By.cssSelector("[data-qaid=\"email\"]"));
		WebElement passwordlField = driver.findElement(By.cssSelector("[data-qaid=\"password\"]"));
		WebElement submitElement = driver.findElement(By.cssSelector("[data-qaid=\"submit\"]"));

		nameField.sendKeys(user.getName());
		emailField.sendKeys(user.getEmail());
		passwordlField.sendKeys(user.getPassword());

		submitElement.submit();
		String pageLink = driver.getCurrentUrl();
		Timer.waitSeconds(6);

		driver.get(pageLink);

		Timer.waitSeconds(10);

		return driver;
	}

	public static String getDriverPath() {
		return DRIVER_PATH;
	}

}
