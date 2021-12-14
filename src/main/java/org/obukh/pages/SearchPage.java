package org.obukh.pages;

import io.qameta.allure.Step;
import org.obukh.core.driver.WebDriverFactory;
import org.obukh.pages.base.BasePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchPage extends BasePage {
    @FindBy(css = "input.search-box-text")
    private WebElement searchField;

    @FindBy(css = "button.search-box-button")
    private WebElement searchButton;

    @FindBy(xpath = "//h2[@class='product-title']/a[1]")
    private WebElement firstFoundedProductTitle;

    @FindBy(css = "div.product-item")
    private List<WebElement> allFoundProducts;

    @FindBy(css = "div.no-result")
    private WebElement noResultMessage;

    public SearchPage() {
        PageFactory.initElements(WebDriverFactory.getDriver(), this);
        waitForElementsLoad(searchButton, searchField);
    }

    @Step("Fill search field with request: {request}")
    public SearchPage fillSearchField(String request) {
        logger.info("fillSearchField with: " + request);
        searchField.sendKeys(request);
        return this;
    }

    @Step("Click the Search button")
    public SearchPage clickSearchButton() {
        logger.info("clickSearchButton");
        searchButton.click();
        return new SearchPage();
    }

    @Step("Check if the product '{searchRequest}' was founded")
    public boolean isProductFounded(String searchRequest) {
        try {
            return firstFoundedProductTitle.getText().equals(searchRequest);
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    @Step("Check if there are any founded products")
    public boolean isNoProductFound() {
        return allFoundProducts.size() == 0;
    }

    @Step("Check if NoResultMessage is visible")
    public boolean isNoResultMessageVisible() {
        try {
            return noResultMessage.getText().equals("No products were found that matched your criteria.");
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

}
