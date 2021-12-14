package org.obukh.pages;

import org.obukh.core.driver.WebDriverFactory;
import org.obukh.pages.base.BasePage;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends BasePage {
    public MainPage() {
        PageFactory.initElements(WebDriverFactory.getDriver(), this);
    }
}
