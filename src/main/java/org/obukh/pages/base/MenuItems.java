package org.obukh.pages.base;

public enum MenuItems {
    REGISTER("register"),
    LOGIN("login"),
    WISHLIST("wishlist"),
    SHOPPING_CART("cart"),
    MY_ACCOUNT("account"),
    LOGOUT("logout");

    private String value;

    MenuItems (String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
