package org.obukh.tests;

import org.obukh.pages.base.MenuItems;
import org.obukh.tests.base.BaseTest;
import org.obukh.core.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginLogoutTests extends BaseTest {

    @Test
    public void loginTest() {
        goToUrl(PropertiesReader.getInstance().getPropertyByName("app.base.url"));

        basePage.applicationMenu()
                .selectLogin()
                .logInComplete(PropertiesReader.getInstance().getPropertyByName("user.email"),
                        PropertiesReader.getInstance().getPropertyByName("user.password"));
        //sleep(500);//wait to see the test execution

        Assert.assertTrue(basePage.applicationMenu().isMenuItemVisible(MenuItems.MY_ACCOUNT),
                "The item 'My Account' should be visible");
        Assert.assertTrue(basePage.applicationMenu().isMenuItemVisible(MenuItems.LOGOUT),
                "The item 'Logout' should be visible");
        Assert.assertFalse(basePage.applicationMenu().isMenuItemVisible(MenuItems.LOGIN),
                "The item 'Login' should NOT be visible");
        Assert.assertFalse(basePage.applicationMenu().isMenuItemVisible(MenuItems.REGISTER),
                "The item 'Register' should NOT be visible");
    }

    @Test
    public void logoutTest() {
        goToUrl(PropertiesReader.getInstance().getPropertyByName("app.base.url"));

        basePage.applicationMenu()
                .selectLogout();

        Assert.assertFalse(basePage.applicationMenu().isMenuItemVisible(MenuItems.MY_ACCOUNT),
                "The item 'My Account' should NOT be visible");
        Assert.assertFalse(basePage.applicationMenu().isMenuItemVisible(MenuItems.LOGOUT),
                "The item 'Logout' should NOT be visible");
        Assert.assertTrue(basePage.applicationMenu().isMenuItemVisible(MenuItems.LOGIN),
                "The item 'Login' should be visible");
        Assert.assertTrue(basePage.applicationMenu().isMenuItemVisible(MenuItems.REGISTER),
                "The item 'Register' should be visible");
    }

}