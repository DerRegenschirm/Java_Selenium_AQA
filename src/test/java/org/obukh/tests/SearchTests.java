package org.obukh.tests;

import org.obukh.pages.SearchPage;
import org.obukh.pages.base.BasePage;
import org.obukh.tests.base.BaseTest;
import org.obukh.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.*;

public class SearchTests extends BaseTest {

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

    @Test(dataProvider = "searchRequestsDP")
    public void searchResultsTest(String searchRequest) {
        SearchPage page = new SearchPage();
        page.fillSearchField(searchRequest)
                .clickSearchButton();

        Assert.assertTrue(page.isProductFounded(searchRequest));
    }

    @DataProvider
    public Object[][] searchRequestsDP() {
        return new Object[][]{
                {"Custom T-Shirt"},
                {"Windows 8 Pro"},
                {"First Prize Pies"},
                {"Vintage Style Engagement Ring"}
        };
    }

    @Test
    public void productWithNoExistingNameNotFound() {
        SearchPage page = new SearchPage();
        page.fillSearchField("searchRequest")
                .clickSearchButton();

        Assert.assertTrue(page.isNoResultMessageVisible());
    }
}
