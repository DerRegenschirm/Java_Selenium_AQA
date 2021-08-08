package org.obukh;

import org.obukh.driver.BrowserType;
import org.obukh.driver.WebDriverHolder;
import org.obukh.listeners.LoggerTestListener;
import org.obukh.utils.PropertiesReader;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;


@Listeners({LoggerTestListener.class})
public class BaseTest {
    protected void goToUrl(String url) {
        WebDriverHolder.getInstance().getDriver().get(url);
    }

    @BeforeSuite
    public void initAll() {
        WebDriverHolder.getInstance().initDriver(BrowserType.CHROME);
        PropertiesReader.getInstance("app.properties");
    }

    @AfterSuite
    public void deactivateAll() {
        if (WebDriverHolder.getInstance().getDriver() != null) {
            WebDriverHolder.getInstance().getDriver().quit();
        }
    }

    public void sleep(long msecs) {
        try {
            Thread.sleep(msecs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}




