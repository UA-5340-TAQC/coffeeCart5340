package org.coffeecart5340.ui.testrunners;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.coffeecart5340.utils.TestValueProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

public class BaseUiTestRunner {
    protected static TestValueProvider testValueProvider;
    protected WebDriver driver;
    protected SoftAssert softAssert;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        WebDriverManager.chromedriver().setup();
        testValueProvider = new TestValueProvider();
    }

    public WebDriver initChromeDriver() {
        ChromeOptions options = new ChromeOptions();

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(5));
        return driver;
    }

    @BeforeMethod
    public void beforeMethod() {
        driver = initChromeDriver();
        driver.get(testValueProvider.getBaseUrl());
        softAssert = new SoftAssert();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        if (driver != null) {
            driver.quit();
        }
    }

}
