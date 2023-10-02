package com.poly.common;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;


public class WebdriverFactory {
	public static final String CHROME_BROWSER = "chrome";
	public static final String EDGE_BROWSER = "edge";
	
	public static WebDriver getDriver(String browserType, String baseURL) {
		switch (browserType) {
		case CHROME_BROWSER: {
//			Khoi tao cac tuy bien
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
//			Khoi tao doi tuong chrome drive
			WebDriver driver = new ChromeDriver(options);
//			Set trinh duyet maxsize
			driver.manage().window().maximize();
//			Truy cao vao trang web
			driver.get(baseURL);
//			Set thoi gian doi toi da de tim kiem 1 phan tu
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//			Set thoi gian toi da de load trang
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
//			Tra ve drive
			return driver;
		}
		case EDGE_BROWSER: {
			EdgeOptions options = new EdgeOptions();
			WebDriver driver = new EdgeDriver(options);
			driver.manage().window().maximize();
			driver.get(baseURL);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
			return driver;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + browserType);
		}
	}
}
