package org.obukh.pages.base;

import io.qameta.allure.Step;
import org.codehaus.plexus.util.StringUtils;
import org.obukh.core.driver.WebDriverFactory;
import org.obukh.pages.CategoryPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CategoriesMenu extends BasePage {

    @FindBy(className = "top-menu")
    private WebElement categoryMenu;

    public CategoriesMenu() {
        PageFactory.initElements(WebDriverFactory.getDriver(), this);
    }

    public CategoryPage selectCategory(String topCategoryName, String subCategoryName) {
        logger.info("Select category: " + topCategoryName + " --> " + subCategoryName);
       WebElement mainCategory = categoryMenu.findElement(getCategoryMenuElement(topCategoryName));
        new Actions(WebDriverFactory.getDriver())
                .moveToElement(mainCategory)
                .build().perform();
        if (StringUtils.isEmpty(subCategoryName)) {
            mainCategory.click();
        } else {
            mainCategory.findElement(getCategoryMenuElement(subCategoryName)).click();
        }
        return new CategoryPage();
    }

    private By getCategoryMenuElement(String categoryName) {
        return By.xpath(String.format("//ul[contains(@class, 'notmobile')]//a[contains(text(), '%s')]", categoryName));
    }

    @Step("Select category")
    public CategoryPage selectMainCategory(String mainCategoryName) {
        logger.info("Select category: " + mainCategoryName);
        return selectCategory(mainCategoryName,null);
    }

}
