package org.obukh.tests;

import org.obukh.pages.ShoppingCartPage;
import org.obukh.pages.base.BasePage;
import org.obukh.tests.base.BaseTest;
import org.obukh.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class ShoppingCartTests extends BaseTest {

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
    public void removeItemFromCartCalculation() {
        BasePage page = new BasePage();
        page.categoriesMenu()
                .selectCategory("Computers ", "Notebooks ");

        page.categoryPage()
                .clickOnAddToCartButtonRandomItemsNotApple()
                .closeNotificationBar()
                .clickOnAddToCartButtonRandomItemsNotApple()
                .closeNotificationBar()
                .clickOnAddToCartButtonRandomItemsNotApple()
                .closeNotificationBar();

        page.applicationMenu().
                selectShoppingCart();

        ShoppingCartPage cartPage = page.shoppingCartPage();

        if (cartPage.isShoppingCartEmpty()) {
            Assert.fail("No items in cart");
        }

        List<ShoppingCartPage.ShoppingCartItem> cartItems = cartPage.initCartTable();

        Assert.assertTrue(cartPage.compareInsideTablePrices(cartItems),
                "Wrong calculation inside the table");
        Assert.assertTrue(cartPage.compareSubtotalPrices(cartItems),
                "Wrong subtotal cost calculation");
        Assert.assertTrue(cartPage.comparePricesOutsideTable(),
                "Wrong total cost calculation");

        Float totalCostBefore = cartPage.getTotalAmount();

        cartPage.clickRemoveRandomProductFromCart(cartItems);

        Float totalCostAfter = cartPage.getTotalAmount();

        Assert.assertTrue(totalCostAfter < totalCostBefore, "The cost wasn't getting less");
}
}