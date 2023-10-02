package com.poly.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.poly.utils.WebElementUtils;

public class SignInPage {
	WebDriver driver;
	
	@FindBy(id="username")
	WebElement usernameInput;
	
	@FindBy(id="floatingPassword")
	WebElement passwordInput;
	
	@FindBy(id="submitBtn")
	WebElement submitButton;
	
	@FindBy(id="error")
	WebElement errorMessage;

	public SignInPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void signIn(String username, String password) {
		WebElementUtils webElementUtils = new WebElementUtils(driver);
		webElementUtils.setText(usernameInput, username);
		webElementUtils.setText(passwordInput, password);
		webElementUtils.clickElement(submitButton);
	}
	
	public void verifySuccess(String url) {
		Assert.assertNotEquals(driver.getCurrentUrl(), url);
	}

	public void verifyInvalidForm(){
		Assert.assertTrue(errorMessage.getText().length() == 0);
	}

	public void verifyError(){
		Assert.assertTrue(errorMessage.getText().length()>0);
	}
	
}
