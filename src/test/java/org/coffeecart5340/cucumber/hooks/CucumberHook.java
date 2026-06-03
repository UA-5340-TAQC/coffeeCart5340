package org.coffeecart5340.cucumber.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.coffeecart5340.utils.DriverManager;
import org.coffeecart5340.utils.TestValueProvider;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class CucumberHook {

    private static final TestValueProvider testValueProvider = new TestValueProvider();
    private SoftAssert softAssert;

    static{
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setUp(Scenario scenario) {
        WebDriver driver = initChromeDriver();
        DriverManager.setDriver(driver);
        driver.get(testValueProvider.getBaseUrl());
        scenario.log("Scenario started: " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            if (scenario.isFailed()) {
                attachScreenshot();
                attachPageSource();
            }
            scenario.log("Scenario " + (scenario.isFailed() ? "failed" : "passed") + ": " + scenario.getName());
            driver.quit();
            DriverManager.removeDriver();
        }
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    private byte[] attachScreenshot() {
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source on failure", type = "text/html")
    private byte[] attachPageSource() {
        return DriverManager.getDriver().getPageSource().getBytes();
    }

    private WebDriver initChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        if (testValueProvider.isHeadless()) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=2560,1440");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-popups-blocking");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(2560, 1440));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }
    public WebDriver getDriver(){
        return DriverManager.getDriver();
    }
    public SoftAssert getSoftAssert(){
        return softAssert;
    }
}