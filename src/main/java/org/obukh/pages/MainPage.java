package org.obukh.pages;

import org.obukh.driver.WebDriverHolder;
import org.obukh.pages.base.BasePage;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends BasePage {
    public MainPage() {
        PageFactory.initElements(WebDriverHolder.getInstance().getDriver(), this);
    }
}
