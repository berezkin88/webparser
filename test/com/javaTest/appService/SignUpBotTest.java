package com.javaTest.appService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.javaTask.service.SignUpBot;

class SignUpBotTest {

	@Test
	void testGetWebDriver() {
		WebDriver driver = null;
		
		System.setProperty("webdriver.gecko.driver", SignUpBot.getDriverPath());

		driver = new FirefoxDriver();

		assertTrue(driver != null);
		
		driver.quit();
	}

}
