package org.obukh.pages;

import org.obukh.driver.WebDriverHolder;
import org.obukh.pages.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ShoppingCartPage extends BasePage {

    @FindBy(xpath = "//h1[contains(text(),'Shopping cart')]")
    private WebElement pageTitle;

    @FindBy(css = "span.product-unit-price")
    private List<WebElement> productPrices;

    @FindBy(css = "input.qty-input")
    private List<WebElement> productQty;

    @FindBy(css="span.product-subtotal")
    private List<WebElement> productTotalPrice;

    @FindBy(css = "button.remove-btn")
    private List<WebElement> productRemoveButton;

    @FindBy(xpath = "//tr[@class='order-subtotal']//span[@class = 'value-summary']")
    private WebElement priceSubTotal;

    @FindBy(xpath = "//tr[@class='shipping-cost']//span[@class = 'value-summary']")
    private WebElement priceShipping;

    @FindBy(xpath = "//tr[@class='tax-value']//span[@class = 'value-summary']")
    private WebElement priceTax;

    @FindBy(xpath = "//tr[@class='order-total']//span[@class = 'value-summary']")
    private WebElement priceTotal;



    public ShoppingCartPage() {
        PageFactory.initElements(WebDriverHolder.getInstance().getDriver(), this);
        waitForElementsLoad(pageTitle);
    }

    public Float getIntegerPrice(String price) {
        return Float.parseFloat(price.replace("$€","")); //the price looks like $X.00 or €X.YY
    }




    public static class ShoppingCartItem {
        WebElement sku, image, productTitle, price, quantity, totalProductPrice, removeIcon;

        public ShoppingCartItem(WebElement sku, WebElement image, WebElement productTitle, WebElement price, WebElement quantity, WebElement totalProductPrice, WebElement removeIcon) {
            this.sku = sku;
            this.image = image;
            this.productTitle = productTitle;
            this.price = price;
            this.quantity = quantity;
            this.totalProductPrice = totalProductPrice;
            this.removeIcon = removeIcon;
        }

        public ShoppingCartItem(WebElement price, WebElement quantity, WebElement totalProductPrice, WebElement removeIcon) {
            this.price = price;
            this.quantity = quantity;
            this.totalProductPrice = totalProductPrice;
            this.removeIcon = removeIcon;
        }

//        public WebElement getSku() {
//            return sku;
//        }
//
//        public void setSku(WebElement sku) {
//            this.sku = sku;
//        }
//
//        public WebElement getImage() {
//            return image;
//        }
//
//        public void setImage(WebElement image) {
//            this.image = image;
//        }
//
//        public WebElement getProductTitle() {
//            return productTitle;
//        }
//
//        public void setProductTitle(WebElement productTitle) {
//            this.productTitle = productTitle;
//        }

        public WebElement getPrice() {
            return price;
        }

        public void setPrice(WebElement price) {
            this.price = price;
        }

        public WebElement getQuantity() {
            return quantity;
        }

        public void setQuantity(WebElement quantity) {
            this.quantity = quantity;
        }

        public WebElement getTotalProductPrice() {
            return totalProductPrice;
        }

        public void setTotalProductPrice(WebElement totalProductPrice) {
            this.totalProductPrice = totalProductPrice;
        }

        public WebElement getRemoveIcon() {
            return removeIcon;
        }

        public void setRemoveIcon(WebElement removeIcon) {
            this.removeIcon = removeIcon;
        }
    }
}
