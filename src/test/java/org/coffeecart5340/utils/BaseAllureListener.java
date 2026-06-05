package org.coffeecart5340.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class BaseAllureListener implements ITestListener {

    // API attachments
    public static void attachApiRequest(String request) {
        Allure.addAttachment("API Request", "application/json", request, ".json");
    }

    public static void attachApiResponse(String response) {
        Allure.addAttachment("API Response", "application/json", response, ".json");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        attachFailureArtifacts();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        attachFailureArtifacts();
    }

    private void attachFailureArtifacts() {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) return;

        try {
            // 1. Скріншот
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot", "image/png", new ByteArrayInputStream(screenshot), ".png");

            // 2. Page Source
            String pageSource = driver.getPageSource();
            Allure.addAttachment("Page source", "text/html", pageSource, ".html");

            // 3. Browser Logs
            String logs = driver.manage().logs().get(LogType.BROWSER).getAll().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining("\n"));
            Allure.addAttachment("Browser logs", "text/plain", logs, ".log");

        } catch (Exception e) {
            Allure.addAttachment("Error gathering artifacts", "text/plain", e.getMessage(), ".txt");
        }
    }
}