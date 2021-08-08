package org.obukh.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Arrays;

public class WebDriverFactory {
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
                return new ChromeDriver();
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

}
