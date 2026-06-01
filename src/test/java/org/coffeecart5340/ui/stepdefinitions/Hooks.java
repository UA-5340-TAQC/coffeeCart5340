package org.coffeecart5340.ui.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.coffeecart5340.utils.DriverManager;
import org.coffeecart5340.utils.TestValueProvider;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class Hooks {

    private static TestValueProvider testValueProvider;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        testValueProvider = new TestValueProvider();
    }

    @Before
    public void setUp() {
        WebDriver driver = initChromeDriver();
        DriverManager.setDriver(driver);
        driver.get(testValueProvider.getBaseUrl());
    }

    @After
    public void tearDown() {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            driver.quit();
        }
        DriverManager.removeDriver();
    }

    @AfterAll
    public static void afterAll() {
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
        Dimension dimension = new Dimension(2560, 1440);
        driver.manage().window().setSize(dimension);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }
}