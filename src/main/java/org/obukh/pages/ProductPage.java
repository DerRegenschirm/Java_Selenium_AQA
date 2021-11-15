package org.obukh.pages;

import org.obukh.driver.WebDriverHolder;
import org.obukh.pages.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends BasePage {

    @FindBy(css = "div.product-name")
    private WebElement title;

    @FindBy (xpath = "//div[@class='picture']/img")
    private WebElement mainPicture;

    public ProductPage() {
        PageFactory.initElements(WebDriverHolder.getInstance().getDriver(), this);
        waitForElementsLoad(title);
    }

}
