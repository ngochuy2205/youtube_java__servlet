package com.poly.utils;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PageUtils {
	public static final int timeoutForWait = 20;

	public static void waitForPageLoaded(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				return ((JavascriptExecutor) input).executeScript("return document.readyState").toString()
						.equals("complete");
			}

		};

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutForWait));
			wait.until(expectation);
		} catch (Exception e) {
			Assert.fail("Time out for page loading");
		}
	}
}
