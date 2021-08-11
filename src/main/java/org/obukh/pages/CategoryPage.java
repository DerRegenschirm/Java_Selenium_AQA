package org.obukh.pages;

import io.qameta.allure.Step;
import org.obukh.driver.WebDriverHolder;
import org.obukh.pages.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class CategoryPage extends BasePage {
    @FindBy(css = "#customerCurrency")
    private WebElement selectCurrency;
    //Select selectCurrencyElement = new Select(selectCurrency);

    @FindBy(css = ".price.actual-price")
    private List<WebElement> pricesOnThePage;

    @FindBy(className = "page-title")
    private WebElement pageTitle;

    public CategoryPage() {
        PageFactory.initElements(WebDriverHolder.getInstance().getDriver(), this);
        waitForPageLoad();
    }

    @Step("Get list of prices on the product wall")
    public List<Double> getPricesOnThePage() {
        logger.info("Get list of prices on the product wall");
        List<Double> pricesList = new ArrayList<>();
        for (WebElement element :
                pricesOnThePage) {
            pricesList.add(Double.parseDouble(element.getText().replace(String.valueOf(currentPriceCurrency().getSymbol()), "")));
        }
        return pricesList;
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

    @Step("Get the current currency from product wall")
    public Currency currentPriceCurrency() {
        logger.info("Get the current currency from product wall");
        if (pricesOnThePage.get(0).getText().contains(String.valueOf(Currency.DOLLAR.getSymbol()))) {
            return Currency.DOLLAR;
        } else if (pricesOnThePage.get(0).getText().contains(String.valueOf(Currency.EURO.getSymbol()))) {
            return Currency.EURO;
        } else {
            System.out.println("Selected currency and prices currency are different!");
            return null;
        }
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

    @Step("isNewPriceCorrect")
    public boolean isNewPriceCorrect(List<Double> previousPrices, List<Double> newPrices) {
        double index = currentCurrency().getIndex();
        if (previousPrices.size() == newPrices.size()) {
            for (int i = 0; i < previousPrices.size() - 1; i++) {
                if (Math.ceil(previousPrices.get(i)) != Math.ceil(newPrices.get(i) * index)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    private CategoryPage waitForPageLoad() {
        new WebDriverWait(WebDriverHolder.getInstance().getDriver(), 10)
                .until(ExpectedConditions.visibilityOfAllElements(pageTitle, selectCurrency));
        return this;
    }

    @Step("isPriceCurrencyAndSelectedCurrencyAreTheSame")
    public boolean isPriceCurrencyAndSelectedCurrencyAreTheSame() {
        return currentPriceCurrency().equals(currentCurrency());
    }

    @Step("isPriceChanged")
    public boolean isPriceChanged(Currency previousPrice, Currency newPrice) {
        return !previousPrice.equals(newPrice);
    }
}
