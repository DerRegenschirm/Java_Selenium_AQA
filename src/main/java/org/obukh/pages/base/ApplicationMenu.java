package org.obukh.pages.base;

import io.qameta.allure.Step;
import org.obukh.driver.WebDriverHolder;
import org.obukh.pages.LoginPage;
import org.obukh.pages.MainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Objects;

public class ApplicationMenu extends BasePage {

    @FindBy(xpath = "//span[@class='cart-qty']")
    private WebElement numberOfItemsInCart;

    public ApplicationMenu() {
        PageFactory.initElements(WebDriverHolder.getInstance().getDriver(), this);
    }


    public void clickOnMenuItem(MenuItems menuItem) {
        logger.info("Select menu item: " + menuItem);
        WebDriverHolder.getInstance().getDriver()
                .findElement(By.className(("ico-") + menuItem.getValue()))
                .click();
    }

    public boolean isMenuItemVisible(MenuItems menuItem) {
        try {
            WebElement item = WebDriverHolder.getInstance().getDriver()
                    .findElement(By.className(("ico-") + menuItem.getValue()));
            return item.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Select login in main menu")
    public LoginPage selectLogin() {
        clickOnMenuItem(MenuItems.LOGIN);
        return new LoginPage();
    }

    @Step("Select logout in main menu")
    public MainPage selectLogout() {
        clickOnMenuItem(MenuItems.LOGOUT);
        return new MainPage();
    }

    public Integer getQtyOfItemsInCart() {
        try {
            String qtyWithBrackets = numberOfItemsInCart.getText();
            logger.info("Q-ty with brackets: "+ qtyWithBrackets);
            return Integer.parseInt(qtyWithBrackets.replaceAll("[()]",""));
        } catch (NullPointerException e) {
            logger.info("Q-ty error");
            return 0;
        }
    }

}
