package org.coffeecart5340.ui.testrunners;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.coffeecart5340.utils.BaseAllureListener;
import org.coffeecart5340.utils.DriverManager;
import org.coffeecart5340.utils.TestValueProvider;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.time.Duration;


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

    public WebDriver initChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        if (testValueProvider.isHeadless()) {
            // use new headless mode when available
            options.addArguments("--headless=new");
        }
        // Allow remote origins (used by newer Chrome/Chromedriver combinations)
        options.addArguments("--window-size=2560,1440");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-popups-blocking");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        // Extra flags that help Chrome run reliably in CI containers
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");


        WebDriver driver = new ChromeDriver(options);
        Dimension dimension = new Dimension(2560, 1440);
        driver.manage().window().setSize(dimension);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }

    @BeforeMethod
    public void beforeMethod() {
        driver = initChromeDriver();
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

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        if (driver != null) {
            driver.quit();
        }
    }

}
