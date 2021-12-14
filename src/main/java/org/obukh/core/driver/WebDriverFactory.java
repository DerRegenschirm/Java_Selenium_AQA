package org.obukh.core.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;

import java.util.Arrays;

public class WebDriverFactory {

    private static WebDriverFactory webDriverFactory = null;

    private static WebDriver driver;

    private WebDriverFactory() {
        driver = null;
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            driver = initDriver();
        }
        return driver;
    }

    public static WebDriver initDriver(BrowserType browserType) {
        switch (browserType) {
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case EDGE:
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            case CHROME:
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER); // Resolve timeout exception issue in selenium like: [SEVERE]: Timed out receiving message from renderer: 0.100
                return new ChromeDriver(chromeOptions);
        }
    }

    public static WebDriver initDriver() {
        try {
            String browser = System.getProperty("browserType", "chrome");
            return initDriver(BrowserType.valueOf(browser.toUpperCase()));
        } catch (Exception e) {
            System.out.println("Please check the BrowserType parameter. \n"
                    + "Now we can support only: "
                    + Arrays.asList(BrowserType.values()));
        }
        return null;
    }

    public static WebDriverFactory getInstance() {
        if (webDriverFactory == null) {
            webDriverFactory = new WebDriverFactory();
        }
        return webDriverFactory;
    }

}
