package org.obukh.tests;

import org.obukh.pages.base.BasePage;
import org.obukh.tests.base.BaseTest;
import org.obukh.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GiftCardTests extends BaseTest {

    @BeforeTest
    public void login() {
        goToUrl(PropertiesReader.getInstance().getPropertyByName("app.base.url"));
        BasePage page = new BasePage();
        page.applicationMenu()
                .selectLogin()
                .logInComplete(PropertiesReader.getInstance().getPropertyByName("user.email"),
                        PropertiesReader.getInstance().getPropertyByName("user.password"));
    }

    @AfterTest
    public void logout() {
        BasePage page = new BasePage();
        page.applicationMenu()
                .selectLogout();
    }

    @Test
    public void validationErrorMessageTest() {
        BasePage page = new BasePage();
        page.categoriesMenu()
                .selectMainCategory("Gift Cards ")
                .categoryPage()
                .clickOnAddToCartButtonRandomGiftCard()
                .giftCardPage()
                .clearAllFieldsOnForm()
                .enterRecipientName("Tom Marvolo Riddle")
                .clickOnAddToCartButton();

        Assert.assertTrue(page.giftCardPage()
                        .isNotificationBarAppeared(),
                "No notification bar");

        String validationMessageText = "Enter valid sender name";
        Assert.assertTrue(page.giftCardPage()
                        .compareMessageText(validationMessageText),
                "Different validation text");

        page.giftCardPage()
                .closeNotificationBar();
    }

    @Test
    public void addGiftCardToCart(){
        BasePage page = new BasePage();
        page.categoriesMenu()
                .selectMainCategory("Gift Cards ")
                .categoryPage()
                .clickOnAddToCartButton(2)
                .giftCardPage();

        Integer oldQtyOfItemsInCart = page.headerMenu()
                        .getQtyOfItemsInCart();

        page.giftCardPage()
                .clearAllFieldsOnForm()
                .enterRecipientName("Tom Riddle")
                .enterSenderName("Lord Voldemort")
                .clickOnAddToCartButton();

        page.giftCardPage().
                closeNotificationBar();

        Integer newQtyOfItemsInCart = page.headerMenu()
                        .getQtyOfItemsInCart();
        Integer qtyToCompare = oldQtyOfItemsInCart+1;

        Assert.assertEquals(qtyToCompare, newQtyOfItemsInCart,
                "Different quantity: " + oldQtyOfItemsInCart + " -> " + newQtyOfItemsInCart);
    }


}
