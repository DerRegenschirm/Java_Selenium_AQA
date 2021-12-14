package org.obukh.pages;

import io.qameta.allure.Step;
import org.obukh.core.driver.WebDriverFactory;
import org.obukh.pages.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Random;

import java.util.ArrayList;
import java.util.List;

public class CategoryPage extends BasePage {

    @FindBy(css = ".price.actual-price")
    private List<WebElement> pricesOnThePageLabels;

    @FindBy(className = "page-title")
    private WebElement pageTitleLabel;

    @FindBy(xpath = "//div[@class='product-item']")
    private List<WebElement> productItems;

    @FindBy(xpath = "//button[contains(@class,'product-box-add-to-cart-button')]")
    private List<WebElement> addToCartButtons;

    @FindBy(xpath = "//div[@class = 'bar-notification-container']//span[@class ='close']")
    private WebElement closeNotificationBar;

    public CategoryPage() {
        PageFactory.initElements(WebDriverFactory.getDriver(), this);
        waitForElementsLoad(pageTitleLabel);
    }

    @Step("Get list of prices on the product wall")
    public List<Double> getPricesOnThePage() {
        logger.info("Get list of prices on the product wall");
        List<Double> pricesList = new ArrayList<>();
//        for (WebElement element : pricesOnThePageLabels) {
//            pricesList.add(Double.parseDouble(element.getText().replace(String.valueOf(currentPriceCurrency().getSymbol()), "")));
//        }
        pricesOnThePageLabels.forEach(element ->
                pricesList.add(Double.parseDouble(element.getText().replace(String.valueOf(currentPriceCurrency().getSymbol()), ""))));
        return pricesList;
    }

    @Step("Get the current currency from product wall")
    public Currency currentPriceCurrency() {
        logger.info("Get the current currency from product wall");
        if (pricesOnThePageLabels.get(0).getText().contains(String.valueOf(Currency.DOLLAR.getSymbol()))) {
            return Currency.DOLLAR;
        } else if (pricesOnThePageLabels.get(0).getText().contains(String.valueOf(Currency.EURO.getSymbol()))) {
            return Currency.EURO;
        } else {
            System.out.println("Selected currency and prices currency are different!");
            return null;
        }
    }

    @Step("isNewPriceCorrect")
    public boolean isNewPriceCorrect(List<Double> previousPrices, List<Double> newPrices) {
        logger.info("isNewPriceCorrect");
        double index = headerMenu().currentCurrency().getIndex();
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

    @Step("isPriceCurrencyAndSelectedCurrencyAreTheSame")
    public boolean isPriceCurrencyAndSelectedCurrencyAreTheSame() {
        return currentPriceCurrency().equals(headerMenu().currentCurrency());
    }

    @Step("isPriceChanged")
    public boolean isPriceChanged(Currency previousPrice, Currency newPrice) {
        return !previousPrice.equals(newPrice);
    }

    @Step("Add to cart a random gift card")
    public GiftCardPage clickOnAddToCartButtonRandomGiftCard() {
        Random random = new Random();
        int randomInt = random.nextInt(addToCartButtons.size());
        addToCartButtons.get(randomInt).click();
        return new GiftCardPage();
    }

    @Step("Close Notification Bar")
    public CategoryPage closeNotificationBar() {
        try {
            logger.info("Close Notification Bar");
            int counter = 0;
            do {
                waitForElementsLoad(closeNotificationBar);
                closeNotificationBar.click();
                counter++;
            } while ((closeNotificationBar != null)&&(counter<10)); // need to click the Close button several times
            return new CategoryPage();
        } catch (Exception e) {
            return new CategoryPage();
        }
    }

    @Step("Add to cart a random item")
    public CategoryPage clickOnAddToCartButtonRandomItem() {
        logger.info("Add to cart a random item");
        Random random = new Random();
        int randomInt = random.nextInt(addToCartButtons.size());
        addToCartButtons.get(randomInt).click();
        return new CategoryPage();
    }

    @Step("Add to cart random item - not Apple(1st) ")
    public CategoryPage clickOnAddToCartButtonRandomItemsNotApple() {
        logger.info("Add to cart random item - not Apple(1st)");
        Random random = new Random();
        int randomInt = random.nextInt(addToCartButtons.size() - 1) + 1; // to not add the 1st item - Apple
        addToCartButtons.get(randomInt).click();
        return new CategoryPage();
    }

    @Step("Add to cart an item")
    public CategoryPage clickOnAddToCartButton(WebElement item) {
        logger.info("Add to cart an item");
        for (int index = 0; index <= productItems.size(); index++) {
            WebElement productItem = productItems.get(index);
            if (productItem.equals(item)) {
                addToCartButtons.get(index).click();
                break;
            }
        }
        return new CategoryPage();
    }

    @Step("Add to cart an item")
    public CategoryPage clickOnAddToCartButton(int index) {
        logger.info("Add to cart an item number " + index);
        addToCartButtons.get(index).click();
        return new CategoryPage();
    }

}
