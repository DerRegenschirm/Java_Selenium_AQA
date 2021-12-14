package org.obukh.tests;

import org.obukh.tests.base.BaseTest;
import org.obukh.core.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GiftCardTests extends BaseTest {

    @BeforeTest
    public void login() {
        goToUrl(PropertiesReader.getInstance().getPropertyByName("app.base.url"));
        basePage.applicationMenu()
                .selectLogin()
                .logInComplete(PropertiesReader.getInstance().getPropertyByName("user.email"),
                        PropertiesReader.getInstance().getPropertyByName("user.password"));
    }

    @AfterTest
    public void logout() {
        basePage.applicationMenu()
                .selectLogout();
    }

    @Test
    public void validationErrorMessageTest() {
        basePage.categoriesMenu()
                .selectMainCategory("Gift Cards ")
                .categoryPage()
                .clickOnAddToCartButtonRandomGiftCard()
                .clearAllFieldsOnForm()
                .enterRecipientName("Tom Marvolo Riddle")
                .clickOnAddToCartButton();

        Assert.assertTrue(basePage.giftCardPage()
                        .isNotificationBarAppeared(),
                "No notification bar");

        String validationMessageText = "Enter valid sender name";
        Assert.assertTrue(basePage.giftCardPage()
                        .compareMessageText(validationMessageText),
                "Different validation text");

        basePage.giftCardPage()
                .closeNotificationBar();
    }

    @Test
    public void addGiftCardToCart() {
        basePage.categoriesMenu()
                .selectMainCategory("Gift Cards ")
                .categoryPage()
                .clickOnAddToCartButton(2)
                .giftCardPage();

        Integer oldQtyOfItemsInCart = basePage.headerMenu()
                .getQtyOfItemsInCart();

        basePage.giftCardPage()
                .clearAllFieldsOnForm()
                .enterRecipientName("Tom Riddle")
                .enterSenderName("Lord Voldemort")
                .clickOnAddToCartButton();

        basePage.giftCardPage().
                closeNotificationBar();

        Integer newQtyOfItemsInCart = basePage.headerMenu()
                .getQtyOfItemsInCart();
        Integer qtyToCompare = oldQtyOfItemsInCart + 1;

        Assert.assertEquals(qtyToCompare, newQtyOfItemsInCart,
                String.format("Different quantity: %d -> %d", oldQtyOfItemsInCart, newQtyOfItemsInCart));
    }


}
