package org.coffeecart5340.ui;

import lombok.Getter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class Base {

    @Getter
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;


    public Base(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected void waitAndClickElement(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }
    protected void waitAndClickElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }
    protected void waitAndSendKeys(WebElement element, String text){
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }
    protected void waitUntilElementIsVisible(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    protected void waitUntilElementIsInvisible(WebElement element){
        wait.until(ExpectedConditions.invisibilityOf(element));
    }
    protected void waitUntilElementIsStale(WebElement element){
        wait.until(ExpectedConditions.stalenessOf(element));
    }
    protected void scrollToElement(WebElement element){
        actions.moveToElement(element).perform();
    }
    protected void waitUntilElementsAreVisible(List<WebElement> elements){
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }
    protected void scrollToElement(int x, int y){
        js.executeScript("window.scrollTo(arguments[0], arguments[1]);", x, y);
    }
    protected void hoverOverElement(WebElement element){
        actions.moveToElement(element).perform();
    }

}
