package com.poly.testcase;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.poly.common.WebdriverFactory;
import com.poly.page.SignUpPage;
import com.poly.utils.PageUtils;

public class SignUpTest {
	private WebDriver driver;
	private String URL = "http://localhost:8080/asm/sign-up";
	private SignUpPage signUpPage;

	@Test(priority = 0)
	public void signupWithEmptyField(){
		signUpPage.signUp("", "", "");
		signUpPage.verifyInvalidForm();
	}

	@Test(priority = 1)
	public void signupWithEmptyUsername(){
		signUpPage.signUp("", "123", "123");
		signUpPage.verifyInvalidForm();
	}

	@Test(priority = 2)
	public void signupWithEmptyPassword(){
		signUpPage.signUp("abc", "", "123");
		signUpPage.verifyInvalidForm();
	}

	@Test(priority = 3)
	public void signupWithEmptyConfirmPassword(){
		signUpPage.signUp("abc", "123", "");
		signUpPage.verifyInvalidForm();
	}

	@Test(priority = 4)
	public void signupWithEmptyConfirmPasswordNotSame(){
		signUpPage.signUp("abc", "123", "abv");
		signUpPage.verifyFaile();
	}

	@Test(priority = 5)
	public void signupWithExistsUsername() throws InterruptedException {
		signUpPage.signUp("admin", "11111", "11111");
		Thread.sleep(2000);
		signUpPage.verifyFaile();
	}

	@Test(priority = 6)
	public void signupSuccess() throws InterruptedException {
		String username = "abcxyztkyuasdfadsfasd";
		String password = "11111";
		String confirmPassword = "11111";
		signUpPage.signUp(username, password, confirmPassword);
		Thread.sleep(1000);
		signUpPage.verifySucceess();
	}
	
	@BeforeTest
	public void beforeTest() {
		System.out.println("Before SignUp Test");
	}
	
	@AfterTest
	public void afterTest() {
		driver.quit();
	}
	
	@BeforeClass
	public void beforeClass() {
		driver = WebdriverFactory.getDriver(WebdriverFactory.EDGE_BROWSER, URL);
		PageUtils.waitForPageLoaded(driver);
		signUpPage = new SignUpPage(driver);
	}
	
	@AfterClass
	public void afterClass() {
		System.out.println("SiguppnTest after class");
	}
}
