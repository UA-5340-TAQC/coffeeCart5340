package org.coffeecart5340.ui.components;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPreviewComponent extends BaseComponent {

    @FindBy(xpath = ".//span[1]")
    private WebElement itemName;

    @FindBy(xpath = ".//span[@class='unit-desc']")
    private WebElement itemAmount;

    @FindBy(xpath = ".//button[text()='+']")
    private WebElement plusButton;

    @FindBy(xpath = ".//button[contains(@aria-label,'Remove one')]")
    private WebElement minusButton;

    public CartPreviewComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }


    public String getItemName() {
        return itemName.getAttribute("textContent").trim();
    }

    public int getItemAmount() {
        try {
            String number = itemAmount.getAttribute("textContent").replaceAll("[^0-9]", "");
            return number.isEmpty() ? 0 : Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public int getQuantity() {
        String amountText = itemAmount.getText().replace("x", "").trim();
        return Integer.parseInt(amountText);
    }

    public int getQuantity() {
        String amountText = itemAmount.getText().replace("x", "").trim();
        return Integer.parseInt(amountText);
    }

    public CartPreviewComponent clickPlus() {
        waitAndClickElement(plusButton);
        return this;
    }

    public CartPreviewComponent clickMinus() {
        waitAndClickElement(minusButton);
        return this;
    }

    public boolean isPlusButtonAvailable() {
        try {
            return plusButton.isDisplayed() && plusButton.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
