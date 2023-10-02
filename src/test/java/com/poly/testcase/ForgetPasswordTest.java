package com.poly.testcase;

import com.poly.common.WebdriverFactory;
import com.poly.page.ForgetPasswordPage;
import com.poly.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class ForgetPasswordTest {
	private WebDriver driver;
	private String URL = "http://localhost:8080/asm/forget-password";
	ForgetPasswordPage forgetPasswordPage;

	@Test(priority = 0)
	public void forgetPasswordWithEmptyField() {
		forgetPasswordPage.forgetPassword("", "");
		forgetPasswordPage.verifyInvalidForm();
	}

	@Test(priority = 1)
	public void forgetPasswordWithEmptyUserName() {
		forgetPasswordPage.forgetPassword("", "huyhungk17c1@gmail.com");
		forgetPasswordPage.verifyInvalidForm();
	}

	@Test(priority = 2)
	public void forgetPasswordWithEmptyEmail() {
		forgetPasswordPage.forgetPassword("admin", "");
		forgetPasswordPage.verifyInvalidForm();
	}

	@Test(priority = 3)
	public void forgetPasswordWithInvalidEmail() {
		forgetPasswordPage.forgetPassword("admin", "abc");
		forgetPasswordPage.verifyInvalidForm();
	}

	@Test(priority = 4)
	public void forgetPasswordWithNotExistUername() throws InterruptedException {
		forgetPasswordPage.forgetPassword("usernamenotexist", "ducdnps22361@fpt.edu.vn");
		Thread.sleep(2000);
		forgetPasswordPage.verifyError();
	}

	@Test(priority = 5)
	public void forgetPasswordWithErrorEmail() throws InterruptedException {
		forgetPasswordPage.forgetPassword("admin", "ducdnp@fpt.edu.vn");
		Thread.sleep(3000);
		forgetPasswordPage.verifyError();
	}

	@Test(priority = 6)
	public void forgetPasswordSuccess() throws InterruptedException {
		String username = "admin";
		String email = "ducdnps22361@fpt.edu.vn";
		forgetPasswordPage.forgetPassword(username, email);
		Thread.sleep(6000);
		forgetPasswordPage.verifySccess();
	}

	@BeforeTest
	public void beforeTest() {
		System.out.println("Before Test");
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

	@BeforeClass
	public void beforeClass() {
		System.out.println("before class");
		driver = WebdriverFactory.getDriver(WebdriverFactory.CHROME_BROWSER, URL);
		PageUtils.waitForPageLoaded(driver);
		forgetPasswordPage = new ForgetPasswordPage(driver);
	}

	@AfterClass
	public void afterClass() {
		System.out.println("SignInTest after class");
	}
}
