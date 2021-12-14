package org.obukh.pages;

import io.qameta.allure.Step;
import org.obukh.core.driver.WebDriverFactory;
import org.obukh.pages.base.BasePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class GiftCardPage extends BasePage {

    @FindBy(css = "div.product-name")
    private WebElement titleLabel;

    @FindBy(xpath = "//div[@class='picture']/img")
    private WebElement mainImage;

    @FindBy(xpath = "//div[@class='overview']//button[contains(@class,'add-to-cart-button')]")
    private WebElement addToCartMainButton;

    @FindBy(css = "input.recipient-name")
    private WebElement recipientNameField;

    @FindBy(css = "input.sender-name")
    private WebElement senderNameField;

    @FindBy(xpath = "//div[@id='bar-notification']")
    private WebElement notificationBar;

    @FindBy(xpath = "//div[@id='bar-notification']//p")
    private List<WebElement> notificationLabels;

    @FindBy(xpath = "//span[@class='close']")
    private WebElement closeNotificationBarItem;

    @FindBy(xpath = "//div[@class='giftcard']//input")
    private List<WebElement> allInputFieldsOnForm;

    public GiftCardPage() {
        PageFactory.initElements(WebDriverFactory.getDriver(), this);
        waitForElementsLoad(titleLabel, mainImage);
    }

    @Step("Clear all input fields on the form")
    public GiftCardPage clearAllFieldsOnForm() {
        for (WebElement field :
                allInputFieldsOnForm) {
            field.clear();
        }
        return this;
    }

    @Step("Enter sender's name: {name}")
    public GiftCardPage enterSenderName(String name) {
        logger.info("Print sender name: " + name);
        senderNameField.clear();
        senderNameField.sendKeys(name);
        return this;
    }

    @Step("Enter recipient's name: {recipient}")
    public GiftCardPage enterRecipientName(String recipient) {
        logger.info("Print recipient name: " + recipient);
        recipientNameField.clear();
        recipientNameField.sendKeys(recipient);
        return this;
    }

    @Step("Click on the button Add to Cart")
    public GiftCardPage clickOnAddToCartButton() {
        logger.info("Click on add to cart button");
        addToCartMainButton.click();
        return new GiftCardPage();
    }

    @Step("Check if the Notification Bar appeared?")
    public boolean isNotificationBarAppeared() {
        return notificationBar.isDisplayed();
    }

    @Step("Close notification bar")
    public GiftCardPage closeNotificationBar() throws NoSuchElementException {
        waitForElementsLoad(closeNotificationBarItem);
        closeNotificationBarItem.click();
        return this;
    }

    @Step("Compare text on messages: {message}")
    public boolean compareMessageText ( String message) {
        if (isNotificationBarAppeared()) {
            for (WebElement messageElement :
                    notificationLabels) {
                if (message.equals(messageElement.getText())) {
                    return true;
                }
            }
        }
        return false;
    }
}
