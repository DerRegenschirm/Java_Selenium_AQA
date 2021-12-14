package org.obukh.tests;

import org.obukh.pages.ShoppingCartPage;
import org.obukh.tests.base.BaseTest;
import org.obukh.core.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class ShoppingCartTests extends BaseTest {

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
    public void removeItemFromCartCalculation() {

        basePage.categoriesMenu()
                .selectCategory("Computers ", "Notebooks ");

        basePage.categoryPage()
                .clickOnAddToCartButtonRandomItemsNotApple()
                .closeNotificationBar()
                .clickOnAddToCartButtonRandomItemsNotApple()
                .closeNotificationBar()
                .clickOnAddToCartButtonRandomItemsNotApple()
                .closeNotificationBar();

        basePage.applicationMenu().
                selectShoppingCart();

        if (basePage.shoppingCartPage().isShoppingCartEmpty()) {
            Assert.fail("No items in cart");
        }

        List<ShoppingCartPage.ShoppingCartItem> cartItems = basePage.shoppingCartPage().initCartTable();

        Assert.assertTrue(basePage.shoppingCartPage().compareInsideTablePrices(cartItems),
                "Wrong calculation inside the table");
        Assert.assertTrue(basePage.shoppingCartPage().compareSubtotalPrices(cartItems),
                "Wrong subtotal cost calculation");
        Assert.assertTrue(basePage.shoppingCartPage().comparePricesOutsideTable(),
                "Wrong total cost calculation");

        Float totalCostBefore = basePage.shoppingCartPage().getTotalAmount();

        basePage.shoppingCartPage().clickRemoveRandomProductFromCart(cartItems);

        Float totalCostAfter = basePage.shoppingCartPage().getTotalAmount();

        Assert.assertTrue(totalCostAfter < totalCostBefore,
                "The cost wasn't getting less");
    }
}