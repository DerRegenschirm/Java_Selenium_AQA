package org.obukh.pages;

import org.obukh.driver.WebDriverHolder;
import org.obukh.pages.base.BasePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SearchPage extends BasePage {
    @FindBy(css = "input.search-box-text")
    private WebElement searchField;

    @FindBy(css = "button.search-box-button")
    private WebElement searchButton;

    @FindBy(xpath = "//h2[@class=\"product-title\"]/a[1]")
    private WebElement firstFoundedProductTitle;

    @FindBy(css = "div.product-item")
    private List<WebElement> allFoundProducts;

    @FindBy(css = "div.no-result")
    private WebElement noResultMessage;

    public SearchPage() {
        PageFactory.initElements(WebDriverHolder.getInstance().getDriver(), this);
        waitForElementsLoad(searchButton,searchField);
    }

    public SearchPage fillSearchField(String request) {
        logger.info("fillSearchField with: " + request);
        searchField.sendKeys(request);
        return this;
    }

    public SearchPage clickSearchButton() {
        logger.info("clickSearchButton");
        searchButton.click();
        return new SearchPage();
    }

    public boolean isProductFounded(String searchRequest) {
        try {
            return firstFoundedProductTitle.getText().equals(searchRequest);
        } catch (NoSuchElementException ex) {
            return false;
        }

    }

    public boolean isNoProductFound() {
        return allFoundProducts.size() == 0;
    }

    public boolean isNoResultMessageVisible(){
        try {
            return noResultMessage.getText().equals("No products were found that matched your criteria.");
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

}
