package org.obukh.listeners;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.obukh.driver.WebDriverHolder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

public class LoggerTestListener implements ITestListener {
    private static Logger logger = LogManager.getLogger(LoggerTestListener.class.getName());

    @Override
    public void onTestStart(ITestResult tr) {
        logger.info("Test " + tr.getName() + " started");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        logger.info("Test " + tr.getName() + " finished successfully");
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        logger.info("Test " + tr.getName() + " failed");

        try {
            saveScreenshotAllure(tr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File screenshotAs = ((TakesScreenshot) WebDriverHolder.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotAs, new File(tr.getName() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        logger.info("Test " + tr.getName() + " skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }


    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotAllure(ITestResult iTestResult) throws Exception {
        File fileCopy = new File(iTestResult.getName() + ".png");
        File screenshotFile = ((TakesScreenshot) WebDriverHolder.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile, fileCopy);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileUtils.readFileToByteArray(fileCopy);
    }

}
