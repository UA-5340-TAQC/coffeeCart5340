package org.coffeecart5340.ui.testrunners;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.coffeecart5340.utils.BaseAllureListener;
import org.coffeecart5340.utils.DriverManager;
import org.coffeecart5340.utils.TestValueProvider;
import org.coffeecart5340.utils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;


@Listeners(BaseAllureListener.class)
public class BaseUiTestRunner {
    protected static TestValueProvider testValueProvider;
    protected WebDriver driver;
    protected SoftAssert softAssert;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        WebDriverManager.chromedriver().setup();
        testValueProvider = new TestValueProvider();
    }

    @BeforeMethod
    public void beforeMethod() {
        driver = WebDriverFactory.createChromeDriver(testValueProvider.isHeadless());
        DriverManager.setDriver(driver);
        driver.get(testValueProvider.getBaseUrl());
        softAssert = new SoftAssert();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            driver.quit();
        }
        DriverManager.removeDriver();
    }
}
