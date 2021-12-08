package org.obukh.pages.base;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.obukh.driver.WebDriverHolder;
import org.obukh.pages.CategoryPage;
import org.obukh.pages.GiftCardPage;
import org.obukh.pages.SearchPage;
import org.obukh.pages.ShoppingCartPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected static Logger logger = LogManager.getLogger(BasePage.class);

    public ApplicationMenu applicationMenu() {
        return new ApplicationMenu();
    }

    public CategoriesMenu categoriesMenu() {
        return new CategoriesMenu();
    }

    public CategoryPage categoryPage() {
        logger.info("User is on the Category page");
        return new CategoryPage();
    }

    public HeaderMenu headerMenu() {
        logger.info("User is on the Category page");
        return new HeaderMenu();
    }

    public SearchPage searchPage() {
        logger.info("User is on the Search page");
        return new SearchPage();
    }

    public GiftCardPage giftCardPage() {
        logger.info("User is on the Gift card description page");
        return new GiftCardPage();
    }

    public ShoppingCartPage shoppingCartPage() {
        logger.info("User is on the Shopping Cart page");
        return new ShoppingCartPage();
    }

    public BasePage waitForElementsLoad(WebElement element) {
        new WebDriverWait(WebDriverHolder.getInstance().getDriver(), 10)
                .until(ExpectedConditions.visibilityOfAllElements(element));
        return this;
    }

    public BasePage waitForElementsClickable(WebElement element) {
        new WebDriverWait(WebDriverHolder.getInstance().getDriver(), 10)
                .until(ExpectedConditions.elementToBeClickable(element));
        return this;
    }

    public BasePage waitForElementsLoad(WebElement... args) {
        new WebDriverWait(WebDriverHolder.getInstance().getDriver(), 10)
                .until(ExpectedConditions.visibilityOfAllElements(args));
        return this;
    }

}
