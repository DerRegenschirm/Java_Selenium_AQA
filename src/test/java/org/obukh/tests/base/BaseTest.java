package org.obukh.tests.base;
import org.obukh.driver.BrowserType;
import org.obukh.driver.WebDriverHolder;
import org.obukh.listeners.LoggerTestListener;
import org.obukh.utils.PropertiesReader;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

@Listeners({LoggerTestListener.class})
public class BaseTest {

    public void goToUrl(String url) {
        WebDriverHolder.getInstance().getDriver().get(url);
    }

    @BeforeSuite
    public void initAll() {
        WebDriverHolder.getInstance().initDriver(BrowserType.CHROME);
        PropertiesReader.getInstance("app.properties");
        clearCache();
    }

    @AfterSuite
    public void deactivateAll() {
        if (WebDriverHolder.getInstance().getDriver() != null) {
            WebDriverHolder.getInstance().getDriver().quit();
        }
    }

    public void sleep(long msecs) throws InterruptedException {
            Thread.sleep(msecs);
    }

    public void clearCache(){
        WebDriverHolder.getInstance().getDriver().manage().deleteAllCookies();
    }

}




