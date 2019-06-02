package com.javaTask.service.bots;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.javaTask.utils.Timer;

public class AddToBasketBot {

	private static final Logger LOG = Logger.getLogger(AddToBasketBot.class.getName());

	private static final String SEPARATOR = File.separator;
	private static final String USER_DIR = System.getProperty("user.dir");
	private static final String DRIVER_PATH = USER_DIR + SEPARATOR + "lib" + SEPARATOR + "geckodriver-v0.24.0-win64"
			+ SEPARATOR + "geckodriver.exe";

	public WebDriver getWebDriver() {

		LOG.info("Launching the Firefox driver");

		System.setProperty("webdriver.gecko.driver", DRIVER_PATH);

		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);
		// somehow this driver constructor is marked as depricated on my end??
		WebDriver driver = new FirefoxDriver(options);

		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(45, TimeUnit.SECONDS);

		return driver;
	}
	
	public WebDriver addToCart(String url, WebDriver driver) {
		
		LOG.info("Starting adding to cart process");

		driver.get(url);
		Timer.waitSeconds(10);
		
		LOG.info("getting buy button link");
		WebElement buyLink = driver.findElement(By.cssSelector("a[data-qaid=\"buy-button\"]"));
		String link = buyLink.getAttribute("href");
		
		Timer.waitSeconds(1);
		
		driver.get(link);
		
		LOG.info("the product has been added");
		
		return driver;
	}
}
