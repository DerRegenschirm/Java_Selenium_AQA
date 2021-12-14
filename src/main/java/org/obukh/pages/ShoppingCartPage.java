package org.obukh.pages;

import io.qameta.allure.Step;
import org.obukh.core.driver.WebDriverFactory;
import org.obukh.pages.base.BasePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShoppingCartPage extends BasePage {

    @FindBy(xpath = "//h1[contains(text(),'Shopping cart')]")
    private WebElement pageTitleLabel;

    @FindBy(className = "no-data")
    private WebElement noDataLabel;

    @FindBy(xpath = "//td[@class='sku']/span[@class='sku-number']")
    private List<WebElement> skuCodesLabels;

    @FindBy(css = "span.product-unit-price")
    private List<WebElement> productPricesLabels;

    @FindBy(css = "input.qty-input")
    private List<WebElement> productQtyField;

    @FindBy(css = "span.product-subtotal")
    private List<WebElement> productTotalPriceLabels;

    @FindBy(css = "button.remove-btn")
    private List<WebElement> productRemoveButtons;

    @FindBy(xpath = "//tr[@class='order-subtotal']//span[@class = 'value-summary']")
    private WebElement priceSubTotalLabel;

    @FindBy(xpath = "//tr[@class='shipping-cost']//span[@class = 'value-summary']")
    private WebElement priceShippingLabel;

    @FindBy(xpath = "//tr[@class='tax-value']//span[@class = 'value-summary']")
    private WebElement priceTaxLabel;

    @FindBy(xpath = "//tr[@class='order-total']//span[@class = 'value-summary']")
    private WebElement priceTotalLabel;


    public ShoppingCartPage() {
        PageFactory.initElements(WebDriverFactory.getDriver(), this);
        waitForElementsLoad(pageTitleLabel);
    }

    @Step("Remove random product from the cart")
    public ShoppingCartPage clickRemoveRandomProductFromCart(List<ShoppingCartItem> cartItems) {
        logger.info("Remove random product from the cart");
        Random random = new Random();
        int randomItemToRemove = random.nextInt(cartItems.size() - 1);
        cartItems.get(randomItemToRemove).getRemoveIcon().click();
        return new ShoppingCartPage();
    }

    @Step("Transform price element to Float")
    public Float getFloatPrice(WebElement price) {
        logger.info("Transform price element to Float");
        return Float.parseFloat(price.getText().replaceAll("[$€]", "").replace(",", ""));
    }

    @Step("compare prices Inside Table")
    public boolean compareInsideTablePrices(List<ShoppingCartItem> cartItems) {
        for (ShoppingCartItem cartItem : cartItems) {
            Float productPrice = getFloatPrice(cartItem.getPrice());
            Integer productQty = Integer.parseInt(cartItem.getQuantity().getAttribute("value"));
            Float amountFromRow = productPrice * productQty;
            Float totalPriceFromRow = getFloatPrice(cartItem.getTotalProductPrice());

            if (!amountFromRow.equals(totalPriceFromRow)) {
                logger.info(String.format("wrong calculation! %.2f * %d = %.2f",
                        productPrice, productQty, totalPriceFromRow));
                return false;
            }
        }
        return true;
    }

    @Step("Compare subtotal prices")
    public boolean compareSubtotalPrices(List<ShoppingCartItem> cartItems) {
        Float totalPriceInTable = getAmountFromTable(cartItems);
        Float subTotalPriceAboveTable = getSubTotalPrice();
        logger.info(String.format("Compare %.2f and %.2f",
                totalPriceInTable, subTotalPriceAboveTable));
        return (totalPriceInTable.equals(subTotalPriceAboveTable));
    }

    @Step("Compare prices outside table")
    public boolean comparePricesOutsideTable() {
        Float subTotalPriceAboveTable = getSubTotalPrice();
        Float shippingCost = getShippingCost();
        Float taxCost = getTaxCost();
        Float totalCostCalculated = subTotalPriceAboveTable + taxCost + shippingCost;
        Float totalCost = getTotalAmount();
        logger.info(String.format("Compare %.2f and calculated  %.2f",
                totalCost, totalCostCalculated));
        return (totalCost.equals(totalCostCalculated));
    }

    @Step("Init Class with Table fields")
    public List<ShoppingCartItem> initCartTable() {
        logger.info("Init Class with Table fields");
        int numberOfProducts = skuCodesLabels.size();
        List<ShoppingCartItem> cartItems = new ArrayList<>();
        for (int i = 0; i < numberOfProducts; i++) {
//            ShoppingCartItem cartItem =
//                    new ShoppingCartItem(productPrices.get(i),productQty.get(i),productTotalPrice.get(i),productRemoveButton.get(i));
            ShoppingCartItem cartItem = new ShoppingCartItem();
            cartItem.setPrice(productPricesLabels.get(i));
            cartItem.setQuantity(productQtyField.get(i));
            cartItem.setTotalProductPrice(productTotalPriceLabels.get(i));
            cartItem.setRemoveIcon(productRemoveButtons.get(i));

            cartItems.add(cartItem);
        }
        return cartItems;
    }

    @Step("Check if message 'Empty cart' exists")
    public boolean isShoppingCartEmpty() {
        try {
            return noDataLabel.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("get Amount From Table")
    public Float getAmountFromTable(List<ShoppingCartItem> cartItems) {
        Float amountFromTable = 0.00f;
        for (ShoppingCartItem cartItem : cartItems) {
            Float totalProductPrice = getFloatPrice(cartItem.getTotalProductPrice());
            amountFromTable += totalProductPrice;
        }
        logger.info("Amount from table - " + amountFromTable);
        return amountFromTable;
    }

    @Step("get SubTotal Price")
    public Float getSubTotalPrice() {
        return getFloatPrice(priceSubTotalLabel);
    }

    @Step("get Shipping Cost")
    public Float getShippingCost() {
        return getFloatPrice(priceShippingLabel);
    }

    @Step("get Tax Cost")
    public Float getTaxCost() {
        return getFloatPrice(priceTaxLabel);
    }

    @Step("get Total Amount below the Table")
    public Float getTotalAmount() {
        return getFloatPrice(priceTotalLabel);
    }


    public static class ShoppingCartItem {
        WebElement sku, image, productTitle, price, quantity, totalProductPrice, removeIcon;

        public ShoppingCartItem() {
        }

//        public ShoppingCartItem(WebElement sku, WebElement image, WebElement productTitle, WebElement price, WebElement quantity, WebElement totalProductPrice, WebElement removeIcon) {
//            this.sku = sku;
//            this.image = image;
//            this.productTitle = productTitle;
//            this.price = price;
//            this.quantity = quantity;
//            this.totalProductPrice = totalProductPrice;
//            this.removeIcon = removeIcon;
//        }
//
//        public ShoppingCartItem(WebElement price, WebElement quantity, WebElement totalProductPrice, WebElement removeIcon) {
//            this.price = price;
//            this.quantity = quantity;
//            this.totalProductPrice = totalProductPrice;
//            this.removeIcon = removeIcon;
//        }
//
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