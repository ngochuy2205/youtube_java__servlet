package com.poly.page;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.poly.utils.WebElementUtils;

public class SignUpPage {
	WebDriver driver;
	@FindBy(id = "username")
	WebElement usernameInput;

	@FindBy(id = "floatingPassword")
	WebElement passwordInput;
	
	@FindBy(id = "confirmPassword")
	WebElement confirmPasswordInput;

	@FindBy(xpath="/html/body/div/form/div[4]/button")
	WebElement submitButton;
	
	@FindBy(xpath="/html/body/div/form/div[5]")
	WebElement errorMessage;
	
	@FindBy(xpath="/html/body/div/form/div[6]")
	WebElement successMessage;
	
	public SignUpPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void signUp(String username, String password, String confirmPassword) {
		WebElementUtils webElementUtils = new WebElementUtils(driver);
		webElementUtils.setText(usernameInput, username);
		webElementUtils.setText(passwordInput, password);
		webElementUtils.setText(confirmPasswordInput, confirmPassword);
		webElementUtils.clickElement(submitButton);
	}

	public void verifyInvalidForm(){
		assertTrue(successMessage.getText().length()==0 && errorMessage.getText().length()==0);
	}

	public void verifySucceess() {
		assertTrue(successMessage.getText().length() > 0);
	}
	
	public void verifyFaile() {
		assertTrue(errorMessage.getText().length() > 0);
	}
}
