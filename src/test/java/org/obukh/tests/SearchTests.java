package org.obukh.tests;

import org.obukh.core.utils.DataProviders;
import org.obukh.tests.base.BaseTest;
import org.obukh.core.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.*;

public class SearchTests extends BaseTest {

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

    @Test(dataProvider = "search requests", dataProviderClass = DataProviders.class)
    public void searchResultsTest(String searchRequest) {
        basePage.searchPage().fillSearchField(searchRequest)
                .clickSearchButton();

        Assert.assertTrue(basePage.searchPage().isProductFounded(searchRequest));
    }

    @Test
    public void productWithNoExistingNameNotFound() {
        basePage.searchPage().fillSearchField("searchRequest")
                .clickSearchButton();

        Assert.assertTrue(basePage.searchPage().isNoResultMessageVisible());
    }
}
