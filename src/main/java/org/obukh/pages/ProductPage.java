package org.obukh.pages;

import org.obukh.core.driver.WebDriverFactory;
import org.obukh.pages.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage extends BasePage {

    @FindBy(css = "div.product-name")
    private WebElement titleLabel;

    @FindBy (xpath = "//div[@class='picture']/img")
    private WebElement mainImage;

    public ProductPage() {
        PageFactory.initElements(WebDriverFactory.getDriver(), this);
        waitForElementsLoad(titleLabel);
    }

}
