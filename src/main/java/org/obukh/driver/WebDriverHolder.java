package org.obukh.driver;

import org.openqa.selenium.WebDriver;

public class WebDriverHolder {
    private WebDriver driver = null;

    private static WebDriverHolder webDriverHolder = null;

    private WebDriverHolder(){

    }

    public static WebDriverHolder getInstance(){
        if(webDriverHolder == null){
            webDriverHolder = new WebDriverHolder();
        }
        return webDriverHolder;
    }

    public void initDriver(BrowserType browserTypes){
        if(driver == null){
            driver = WebDriverFactory.initDriver(browserTypes);
            driver.manage().window().maximize();
        }
    }
    public WebDriver getDriver(){
        return driver;
    }
}
