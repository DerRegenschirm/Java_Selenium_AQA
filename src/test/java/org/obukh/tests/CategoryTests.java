package org.obukh.tests;

import org.obukh.pages.Currency;
import org.obukh.tests.base.BaseTest;
import org.obukh.core.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class CategoryTests extends BaseTest {

    @BeforeTest
    public void login() {
        goToUrl(PropertiesReader.getInstance().getPropertyByName("app.base.url"));
        basePage.applicationMenu()
                .selectLogin()
                .logInComplete(PropertiesReader.getInstance().getPropertyByName("user.email"), PropertiesReader.getInstance().getPropertyByName("user.password"));
    }

    @AfterTest
    public void logout() {
        basePage.applicationMenu()
                .selectLogout();
    }

    @Test
    public void changeCurrencyTest() {
        basePage.categoriesMenu()
                .selectMainCategory("Books");

        List<Double> previousPrices = basePage.categoryPage().getPricesOnThePage();
        Currency previousPriceSymbol = basePage.headerMenu().currentCurrency();

        basePage.headerMenu()
                .changeCurrency();

        List<Double> newPrices = basePage.categoryPage().getPricesOnThePage();
        Currency newPriceSymbol = basePage.headerMenu().currentCurrency();

        Assert.assertTrue(basePage.categoryPage().isPriceChanged(previousPriceSymbol, newPriceSymbol),
                String.format("The price symbol wasn't changed: %s -> %s", previousPriceSymbol, newPriceSymbol));
        Assert.assertTrue(basePage.categoryPage().isPriceCurrencyAndSelectedCurrencyAreTheSame(),
                "The price currency and selected currency are different");
        Assert.assertTrue(basePage.categoryPage().isNewPriceCorrect(previousPrices, newPrices),
                String.format("The new price is not correct: %s -> %s", previousPrices, newPrices));
    }
}
