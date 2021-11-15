package org.obukh.tests;

import org.obukh.pages.Currency;
import org.obukh.pages.base.BasePage;
import org.obukh.tests.base.BaseTest;
import org.obukh.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class CategoryTests extends BaseTest {

    @BeforeTest
    public void login() {
        goToUrl(PropertiesReader.getInstance().getPropertyByName("app.base.url"));
        BasePage page = new BasePage();
        page.applicationMenu()
                .selectLogin()
                .logInComplete(PropertiesReader.getInstance().getPropertyByName("user.email"), PropertiesReader.getInstance().getPropertyByName("user.password"));
    }

    @AfterTest
    public void logout() {
        BasePage page = new BasePage();
        page.applicationMenu()
                .selectLogout();
    }

    @Test
    public void changeCurrencyTest() {
        BasePage page = new BasePage();
        page.categoriesMenu()
                .selectMainCategory("Books");

        List<Double> previousPrices = page.categoryPage().getPricesOnThePage();
        Currency previousPriceSymbol = page.headerMenu().currentCurrency();

        page.headerMenu()
                .changeCurrency();

        List<Double> newPrices = page.categoryPage().getPricesOnThePage();
        Currency newPriceSymbol = page.headerMenu().currentCurrency();

        Assert.assertTrue(page.categoryPage().isPriceChanged(previousPriceSymbol, newPriceSymbol));
        Assert.assertTrue(page.categoryPage().isPriceCurrencyAndSelectedCurrencyAreTheSame());
        Assert.assertTrue(page.categoryPage().isNewPriceCorrect(previousPrices, newPrices));
    }
}
