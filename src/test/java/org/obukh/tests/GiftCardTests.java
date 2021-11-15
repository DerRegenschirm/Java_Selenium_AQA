package org.obukh.tests;

import org.obukh.pages.base.BasePage;
import org.obukh.tests.base.BaseTest;
import org.obukh.utils.PropertiesReader;
//import org.openqa.selenium.support.PageFactory;
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
                .logInComplete(PropertiesReader.getInstance().getPropertyByName("user.email"), PropertiesReader.getInstance().getPropertyByName("user.password"));
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
        //Thread.sleep(1000);

        Assert.assertTrue(page.giftCardPage()
                        //.switchToAlert()
                        .isNotificationBarAppeared(),
                "No notification bar");

        String validationMessageText = "Enter valid sender name";
        Assert.assertTrue(page.giftCardPage()
                        //.switchToAlert()
                        .compareMessageText(validationMessageText),
                "Different validation text");

        page.giftCardPage()
//                .switchToAlert()
                .closeNotificationBar();
    }

    @Test
    public void addGiftCardToCart() throws Exception {
        BasePage page = new BasePage();
        page.categoriesMenu()
                .selectMainCategory("Gift Cards ")
                .categoryPage()
                .clickOnAddToCartButton(2)
                .giftCardPage();

        Integer oldQtyOfItemsInCart = page.applicationMenu()
                        .getQtyOfItemsInCart();

        page.giftCardPage()
                .clearAllFieldsOnForm()
                .enterRecipientName("Tom Riddle")
                .enterSenderName("Lord Voldemort")
                .clickOnAddToCartButton();

        Thread.sleep(1000);

        page.giftCardPage().
//                //switchToAlert().dismiss();
                closeNotificationBar();

        //Thread.sleep(1000);

        Integer newQtyOfItemsInCart = page.applicationMenu()
                        .getQtyOfItemsInCart();
        Integer qtyToCompare = oldQtyOfItemsInCart+1;

        Assert.assertEquals(qtyToCompare, oldQtyOfItemsInCart,
                "Different quantity: " + oldQtyOfItemsInCart + " -> " + newQtyOfItemsInCart);
    }


}
