package org.obukh.core.utils;

import org.testng.annotations.DataProvider;

/**
 * @author tobukhova on 12/14/21
 * Java_Selenium_AQA
 **/
public class DataProviders {

    @DataProvider(name = "search requests")
    public static Object[][] searchRequestsDP() {
        return new Object[][]{
                {"Custom T-Shirt"},
                {"Windows 8 Pro"},
                {"First Prize Pies"},
                {"Vintage Style Engagement Ring"}
        };
    }
}
