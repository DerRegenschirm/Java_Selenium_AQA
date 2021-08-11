package org.obukh.pages.base;

import io.qameta.allure.Step;
import org.obukh.driver.WebDriverHolder;
import org.obukh.pages.LoginPage;
import org.obukh.pages.MainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class ApplicationMenu extends BasePage {

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

    @Step("Select logwin in main menu")
    public LoginPage selectLogin() {
        clickOnMenuItem(MenuItems.LOGIN);
        return new LoginPage();
    }

    @Step("Select logout in main menu")
    public MainPage selectLogout() {
        clickOnMenuItem(MenuItems.LOGOUT);
        return new MainPage();
    }

}
