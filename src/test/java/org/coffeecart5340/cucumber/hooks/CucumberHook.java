package org.coffeecart5340.cucumber.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.coffeecart5340.utils.DriverManager;
import org.coffeecart5340.utils.TestValueProvider;
import org.coffeecart5340.utils.WebDriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CucumberHook {

    private static final TestValueProvider testValueProvider = new TestValueProvider();

    static{
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setUp(Scenario scenario) {
        WebDriver driver = WebDriverFactory.createChromeDriver(testValueProvider.isHeadless());
        DriverManager.setDriver(driver);
        driver.get(testValueProvider.getBaseUrl());
        scenario.log("Scenario started: " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            try {
                if (scenario.isFailed()) {
                    attachScreenshot(driver);
                    attachPageSource(driver);
                }
                scenario.log("Scenario " + (scenario.isFailed() ? "failed" : "passed") + ": " + scenario.getName());
            } finally {
                driver.quit();
                DriverManager.removeDriver();
            }
        }
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    private byte[] attachScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source on failure", type = "text/html")
    private byte[] attachPageSource(WebDriver driver) {
        return driver.getPageSource().getBytes();
    }

    public WebDriver getDriver(){
        return DriverManager.getDriver();
    }
}