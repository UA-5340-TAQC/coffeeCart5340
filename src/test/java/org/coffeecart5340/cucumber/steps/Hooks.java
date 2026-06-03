package org.coffeecart5340.cucumber.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.coffeecart5340.utils.TestValueProvider;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Hooks {

    private static final TestValueProvider testValueProvider = new TestValueProvider();

    // SharedContext — передає driver між Step класами
    private final SharedContext context;

    public Hooks(SharedContext context) {
        this.context = context;
    }

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(testValueProvider.getBaseUrl());
        context.driver = driver;
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && context.driver != null) {
            byte[] screenshot = ((TakesScreenshot) context.driver)
                    .getScreenshotAs(OutputType.BYTES);
            Allure.getLifecycle().addAttachment(
                    "Screenshot on failure", "image/png", "png", screenshot
            );
        }
        if (context.driver != null) {
            context.driver.quit();
        }
    }
}