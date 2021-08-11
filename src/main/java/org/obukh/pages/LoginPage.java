package org.obukh.pages;

import io.qameta.allure.Step;
import org.obukh.driver.WebDriverHolder;
import org.obukh.pages.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {
    @FindBy(css="input.email")
    private WebElement emailField;

    @FindBy(css = "input.password")
    private WebElement passwordField;

    @FindBy(css="button.login-button")
    private WebElement loginButton;

    public LoginPage() {
        PageFactory.initElements(WebDriverHolder.getInstance().getDriver(), this);
    }

    @Step ("Enter Email")
    public LoginPage enterToEmailField (String email) {
        logger.info("Print email: " + email);
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }
    @Step ("Enter Password")
    public LoginPage enterToPasswordField (String password) {
        logger.info("Print password: " + password);
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    @Step ("Click Login button")
    public LoginPage pressLogInButton() {
        logger.info("Press Login button");
        loginButton.click();
        return this;
    }

    public MainPage logInComplete (String userEmail, String password) {
        enterToEmailField(userEmail);
        enterToPasswordField(password);
        pressLogInButton();
        return new MainPage();
    }

}
