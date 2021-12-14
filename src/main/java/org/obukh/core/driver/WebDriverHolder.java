//package org.obukh.core.driver;
//
// TO REMOVE??
//
//import org.openqa.selenium.WebDriver;
//
//public class WebDriverHolder {
//    //private WebDriver driver = null;
//
//    private static WebDriverHolder webDriverHolder = null;
//
//    private WebDriverHolder(){ }
//
//    public static WebDriverHolder getInstance(){
//        if(webDriverHolder == null){
//            webDriverHolder = new WebDriverHolder();
//        }
//        return webDriverHolder;
//    }

//    public void initDriver(BrowserType browserTypes){
//        if(driver == null){
//            driver = WebDriverFactory.initDriver(browserTypes);
//            //driver.manage().window().maximize();
//            driver.manage().window();
//        }
//    }
//    public WebDriver getDriver(){
//        return driver;
//    }
//}
