package org.coffeecart5340.ui.pages;

import org.coffeecart5340.ui.Base;
import org.coffeecart5340.ui.components.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePage extends Base {

    private final By headerLocator = By.xpath("//div[@id='app']/ul");

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HeaderComponent getHeader() {
        WebElement headerElement = driver.findElement(headerLocator);
        return new HeaderComponent(driver, headerElement);
    }
}
