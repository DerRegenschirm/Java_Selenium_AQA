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
    public void checkTheCorrectCalculations() {
        BasePage page = new BasePage();
        page.categoriesMenu()
                .selectCategory("Computers ", "Notebooks ");

        int numberOfAddedItems = 3; // to add 3 random items to the cart
        page.categoryPage()
                .clickOnAddToCartButtonSeveralRandomItems(numberOfAddedItems);

        page.applicationMenu().
                selectShoppingCart();

        if (page.shoppingCartPage().isShoppingCartEmpty()) {
            Assert.fail("No items in cart");
        }

        List<ShoppingCartPage.ShoppingCartItem> cartItems = page.shoppingCartPage().initCartTable();

        Assert.assertTrue(page.shoppingCartPage().compareInsideTablePrices(cartItems),
                "Wrong calculation inside the table");
        Assert.assertTrue(page.shoppingCartPage().compareSubtotalPrices(cartItems),
                "Wrong subtotal cost calculation");
        Assert.assertTrue(page.shoppingCartPage().comparePricesOutsideTable(),
                "Wrong total cost calculation");
    }

    @Test
    public void deleteItemFromCart() {
        BasePage page = new BasePage();
        page.categoriesMenu()
                .selectCategory("Computers ", "Notebooks ");

        int numberOfAddedItems = 3; // to add 3 random items
        page.categoryPage()
                .clickOnAddToCartButtonSeveralRandomItems(numberOfAddedItems);

        page.applicationMenu().
                selectShoppingCart();

        if (page.shoppingCartPage().isShoppingCartEmpty()) {
            Assert.fail("No items in cart");
        }

        List<ShoppingCartPage.ShoppingCartItem> cartItems = page.shoppingCartPage().initCartTable();
        Float totalCostBefore = page.shoppingCartPage().getTotalAmount();

        page.applicationMenu().
                selectShoppingCart().clickRemoveRandomProductFromCart(cartItems);

        Float totalCostAfter = page.shoppingCartPage().getTotalAmount();

        Assert.assertTrue(totalCostAfter < totalCostBefore, "The cost wasn't getting less");
    }
}