package org.obukh.tests;

import org.obukh.pages.base.BasePage;
import org.obukh.tests.base.BaseTest;
import org.testng.annotations.Test;

public class ShoppingCartTests extends BaseTest {

    @Test
    public void deleteItemFromCart () {
        BasePage page = new BasePage();
        page.categoriesMenu()
                .selectCategory("Computers ", "Notebooks ");

        int numberOfAddedItems = 3; // to add 3 random items
                page.categoryPage()
                        .clickOnAddToCartButtonSeveralRandomItems(numberOfAddedItems);

                page.applicationMenu().
                        selectShoppingCart();


    }
}
