package org.obukh.tests;

import org.obukh.pages.base.BasePage;
import org.obukh.pages.base.MenuItems;
import org.obukh.tests.base.BaseTest;
import org.obukh.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginLogoutTests extends BaseTest {

    @Test
    public void loginTest() throws InterruptedException {
        goToUrl(PropertiesReader.getInstance().getPropertyByName("app.base.url"));

        BasePage page = new BasePage();
        page.applicationMenu()
                .selectLogin()
                .logInComplete(PropertiesReader.getInstance().getPropertyByName("user.email"), PropertiesReader.getInstance().getPropertyByName("user.password"));
        sleep(500);

        Assert.assertTrue(page.applicationMenu().isMenuItemVisible(MenuItems.MY_ACCOUNT));
        Assert.assertTrue(page.applicationMenu().isMenuItemVisible(MenuItems.LOGOUT));
        Assert.assertFalse(page.applicationMenu().isMenuItemVisible(MenuItems.LOGIN));
        Assert.assertFalse(page.applicationMenu().isMenuItemVisible(MenuItems.REGISTER));
    }

    @Test
    public void logoutTest() {
        goToUrl(PropertiesReader.getInstance().getPropertyByName("app.base.url"));

        BasePage page = new BasePage();

        page.applicationMenu()
                .selectLogout();

        Assert.assertFalse(page.applicationMenu().isMenuItemVisible(MenuItems.MY_ACCOUNT));
        Assert.assertFalse(page.applicationMenu().isMenuItemVisible(MenuItems.LOGOUT));
        Assert.assertTrue(page.applicationMenu().isMenuItemVisible(MenuItems.LOGIN));
        Assert.assertTrue(page.applicationMenu().isMenuItemVisible(MenuItems.REGISTER));
    }

}