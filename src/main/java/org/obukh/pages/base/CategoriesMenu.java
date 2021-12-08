package org.obukh.pages.base;

import io.qameta.allure.Step;
import org.codehaus.plexus.util.StringUtils;
import org.obukh.driver.WebDriverHolder;
import org.obukh.pages.CategoryPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CategoriesMenu extends BasePage {

    @FindBy(className = "top-menu")
    private WebElement categoryMenu;

    public CategoryPage selectCategory(String topCategoryName, String subCategoryName) {
        logger.info("Select category: " + topCategoryName + " --> " + subCategoryName);
        WebElement mainCategory = categoryMenu.findElement(By.xpath("//ul[contains(@class, 'notmobile')]//a[contains(text(), '" + topCategoryName + "')]"));
        new Actions(WebDriverHolder.getInstance().getDriver())
                .moveToElement(mainCategory)
                .build().perform();
        if (StringUtils.isEmpty(subCategoryName)) {
            mainCategory.click();
        } else {
            mainCategory.findElement(By.xpath("//ul[contains(@class, 'notmobile')]//a[contains(text(), '" + subCategoryName + "')]")).click();
        }
        return new CategoryPage();
    }

    @Step("Select category")
    public CategoryPage selectMainCategory(String mainCategoryName) {
        logger.info("Select category: " + mainCategoryName);
        return selectCategory(mainCategoryName,null);
    }

    public CategoriesMenu() {
        PageFactory.initElements(WebDriverHolder.getInstance().getDriver(), this);
    }
}
