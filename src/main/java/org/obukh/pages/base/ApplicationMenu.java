package org.obukh.pages.base;

import io.qameta.allure.Step;
import org.obukh.driver.WebDriverHolder;
import org.obukh.pages.LoginPage;
import org.obukh.pages.MainPage;
import org.obukh.pages.ShoppingCartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;


public class ApplicationMenu extends BasePage {

    public ApplicationMenu() {
        PageFactory.initElements(WebDriverHolder.getInstance().getDriver(), this);
    }

    public void clickOnMenuItem(MenuItems menuItem) {
        logger.info("Select menu item: " + menuItem);
        WebElement button = WebDriverHolder.getInstance().getDriver()
                .findElement(By.className(("ico-") + menuItem.getValue()));
        waitForElementsClickable(button);
        button.click();
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

    @Step("Select shopping cart in main menu")
    public ShoppingCartPage selectShoppingCart() {
        clickOnMenuItem(MenuItems.SHOPPING_CART);
        return new ShoppingCartPage();
    }

}
