package org.obukh.pages;

import io.qameta.allure.Step;
import org.obukh.driver.WebDriverHolder;
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
    private WebElement pageTitle;

    @FindBy(css = ".no-data")
    private WebElement noDataMessage;

    @FindBy(xpath = "//td[@class='sku']/span[@class='sku-number']")
    private List<WebElement> skuCodes;

    @FindBy(css = "span.product-unit-price")
    private List<WebElement> productPrices;

    @FindBy(css = "input.qty-input")
    private List<WebElement> productQty;

    @FindBy(css = "span.product-subtotal")
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

    @Step("Remove random product from the cart")
    public ShoppingCartPage clickRemoveRandomProductFromCart(List<ShoppingCartItem> cartItems) {
        logger.info("Remove random product from the cart");
        Random random = new Random();
        int randomItemToRemove = random.nextInt(cartItems.size() - 1);
        cartItems.get(randomItemToRemove).getRemoveIcon().click();
        return new ShoppingCartPage();
    }

    public Float getFloatPrice(WebElement price) {
//        String priceString = priceEl.getText();
//        Float priceF = Float.parseFloat(priceString
//                .replaceAll("\\$|\\€", ""));
        //        .replace('.',','));
        //logger.info("Parse Price (" + priceString + ")  to float (" + priceF + ")");
        //return priceF; //the price looks like $X.00 or €X.YY
        return Float.parseFloat(price.getText().replaceAll("[$€]", ""));
    }

    @Step("compare prices Inside Table")
    public boolean compareInsideTablePrices(List<ShoppingCartItem> cartItems) {
        for (int i = 0; i < cartItems.size(); i++) {
            Float productPrice = getFloatPrice(cartItems.get(i).getPrice());
            Integer productQty = Integer.parseInt(cartItems.get(i).getQuantity().getAttribute("value"));
            Float amountFromRow = productPrice * productQty;
            Float totalPriceFromRow = getFloatPrice(cartItems.get(i).getTotalProductPrice());

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
        int numberOfProducts = skuCodes.size();
        List<ShoppingCartItem> cartItems = new ArrayList<>();
        for (int i = 0; i < numberOfProducts; i++) {
//            ShoppingCartItem cartItem =
//                    new ShoppingCartItem(productPrices.get(i),productQty.get(i),productTotalPrice.get(i),productRemoveButton.get(i));
            ShoppingCartItem cartItem = new ShoppingCartItem();
            cartItem.setPrice(productPrices.get(i));
            cartItem.setQuantity(productQty.get(i));
            cartItem.setTotalProductPrice(productTotalPrice.get(i));
            cartItem.setRemoveIcon(productRemoveButton.get(i));

            cartItems.add(cartItem);
        }
        return cartItems;
    }

    @Step("Check if message 'Empty cart' exists")
    public boolean isShoppingCartEmpty() {
        try {
            return noDataMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("get Amount From Table")
    public Float getAmountFromTable(List<ShoppingCartItem> cartItems) {
        Float amountFromTable = new Float(0.00f);
        for (int i = 0; i < cartItems.size(); i++) {
            Float totalProductPrice = getFloatPrice(cartItems.get(i).getTotalProductPrice());
            amountFromTable = amountFromTable + totalProductPrice;
        }
        logger.info("Amount from table - " + amountFromTable);
        return amountFromTable;
    }

    @Step("get SubTotal Price")
    public Float getSubTotalPrice() {
        return getFloatPrice(priceSubTotal);
    }

    @Step("get Shipping Cost")
    public Float getShippingCost() {
        return getFloatPrice(priceShipping);
    }

    public Float getTaxCost() {
        return getFloatPrice(priceTax);
    }

    public Float getTotalAmount() {
        return getFloatPrice(priceTotal);
    }


    public static class ShoppingCartItem {
        WebElement sku, image, productTitle, price, quantity, totalProductPrice, removeIcon;

        public ShoppingCartItem() {
        }

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