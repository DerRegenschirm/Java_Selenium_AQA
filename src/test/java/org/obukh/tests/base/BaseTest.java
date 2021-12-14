package org.obukh.tests.base;

import org.obukh.core.driver.BrowserType;
import org.obukh.core.driver.WebDriverFactory;
import org.obukh.core.listeners.LoggerTestListener;
import org.obukh.pages.base.BasePage;
import org.obukh.core.utils.PropertiesReader;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

@Listeners({LoggerTestListener.class})
public class BaseTest {

    protected BasePage basePage = new BasePage();

    public void goToUrl(String url) {
        WebDriverFactory.getDriver().get(url);
    }

    @BeforeSuite
    public void initAll() {
        WebDriverFactory.getInstance().initDriver(BrowserType.CHROME);
        //WebDriverFactory.initDriver(BrowserType.valueOf(PropertiesReader.getInstance().getPropertyByName("app.base.browser")));//
        PropertiesReader.getInstance("app.properties");
        clearCache();
    }

    @AfterSuite
    public void deactivateAll() {
        if (WebDriverFactory.getDriver() != null) {
            WebDriverFactory.getDriver().quit();
        }
    }

    public void sleep(long msecs) throws InterruptedException {
        Thread.sleep(msecs);
    }

    public void clearCache() {
        WebDriverFactory.getDriver().manage().deleteAllCookies();
    }

}




