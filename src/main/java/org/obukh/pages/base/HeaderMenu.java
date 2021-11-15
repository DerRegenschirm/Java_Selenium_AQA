package org.obukh.pages.base;

import io.qameta.allure.Step;
import org.obukh.driver.WebDriverHolder;
import org.obukh.pages.CategoryPage;
import org.obukh.pages.Currency;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * @author tobukhova on 11/15/21
 * Java_Selenium_AQA
 **/
public class HeaderMenu extends BasePage {

    @FindBy(xpath = "//span[@class='cart-qty']")
    private WebElement numberOfItemsInCart;

    @FindBy(css = "#customerCurrency")
    private WebElement selectCurrency;

    public HeaderMenu() {
        PageFactory.initElements(WebDriverHolder.getInstance().getDriver(), this);
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

    @Step("Get the current currency")
    public Currency currentCurrency() {
        logger.info("Get the current currency");
        Select selectCurrencyElement = new Select(selectCurrency);
        if (selectCurrencyElement.getFirstSelectedOption().getText().equals(Currency.DOLLAR.getText())) {
            return Currency.DOLLAR;
        }
        return Currency.EURO;
    }

    @Step("Change currency")
    public CategoryPage changeCurrency() {
        logger.info("Change currency");
        Select selectCurrencyElement = new Select(selectCurrency);
        if (currentCurrency().equals(Currency.DOLLAR)) {
            selectCurrencyElement.selectByVisibleText(Currency.EURO.getText());
        } else {
            selectCurrencyElement.selectByVisibleText(Currency.DOLLAR.getText());
        }
        return new CategoryPage();
    }


}
