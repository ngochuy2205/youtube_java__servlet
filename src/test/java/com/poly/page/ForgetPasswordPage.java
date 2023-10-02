package com.poly.page;

import com.poly.utils.WebElementUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class ForgetPasswordPage {
    WebDriver driver;

    @FindBy(id="username")
    WebElement usernameInput;

    @FindBy(name="email")
    WebElement emailInput;

    @FindBy(id="submit-btn")
    WebElement submitButton;

    @FindBy(id="success")
    WebElement successMessage;

    @FindBy(id="error")
    WebElement errorMessage;
    public ForgetPasswordPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void forgetPassword(String username, String email){
        WebElementUtils utils = new WebElementUtils(driver);
        utils.setText(usernameInput, username);
        utils.setText(emailInput, email);
        utils.clickElement(submitButton);
    }

    public void verifyError(){
        Assert.assertTrue(errorMessage.getText().length() > 0);
    }

    public void verifySccess(){
        Assert.assertTrue(successMessage.getText().length()>0);
    }

    public void verifyInvalidForm(){
        Assert.assertTrue(errorMessage.getText().length()==0 & successMessage.getText().length()==0);
    }
}
