package org.obukh.pages.base;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.obukh.pages.CategoryPage;
import org.obukh.pages.GiftCardPage;
import org.obukh.pages.SearchPage;

public class BasePage {

    protected static Logger logger = LogManager.getLogger(BasePage.class);

    public ApplicationMenu applicationMenu() {
        return new ApplicationMenu();
    }

    public CategoriesMenu categoriesMenu() {
        return new CategoriesMenu();
    }

    public CategoryPage categoryPage() {
        logger.info("User is on the Category page");
        return new CategoryPage();
    }

    public SearchPage searchPage() {
        logger.info("User is on the Search page");
        return new SearchPage();
    }

    public GiftCardPage giftCardPage() {
        logger.info("User is on the Gift card description page");
        return new GiftCardPage();
    }

}
