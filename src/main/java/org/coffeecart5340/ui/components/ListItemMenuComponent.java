package org.coffeecart5340.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ListItemMenuComponent extends BaseComponent {

    @FindBy(xpath = ".//span[not(@class)]")
    protected WebElement itemNameSpan;

    @FindBy(xpath = ".//span[@class='unit-desc']")
    protected WebElement quantitySpan;

    @FindBy(xpath = ".//button[contains(@aria-label, 'Add one')]")
    protected WebElement plusButton;

    @FindBy(xpath = ".//button[contains(@aria-label, 'Remove one')]")
    protected WebElement minusButton;

    public ListItemMenuComponent(WebElement rootElement) {
        super(rootElement);
    }

    @Step("Get item name")
    public String getItemName() {
        return itemNameSpan.getText().trim();
    }

    @Step("Get item quantity")
    public int getQuantity() {
        try {
            String number = quantitySpan.getText().replaceAll("[^0-9]", "");
            return number.isEmpty() ? 0 : Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Step("Increment item quantity")
    public void increment() {
        waitAndClickElement(plusButton);
    }

    @Step("Decrement item quantity")
    public void decrement() {
        waitAndClickElement(minusButton);
    }

    @Step("Wait for quantity to be {expectedQuantity}")
    public void waitForQuantity(int expectedQuantity) {
        wait.until(driver -> getQuantity() == expectedQuantity);
    }

    @Step("Add {count} items")
    public void addItems(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Count must be greater than 0");
        }
        for (int i = 0; i < count; i++) {
            increment();
        }
    }

    @Step("Remove {count} items")
    public void removeItems(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Count must be greater than 0");
        }
        for (int i = 0; i < count; i++) {
            decrement();
        }
    }

    @Step("Set item quantity to {targetQuantity}")
    public void setQuantity(int targetQuantity) {
        if (targetQuantity < 0) {
            throw new IllegalArgumentException("Target quantity cannot be negative");
        }
        int currentQuantity = getQuantity();
        if (currentQuantity < targetQuantity) {
            addItems(targetQuantity - currentQuantity);
        } else if (currentQuantity > targetQuantity) {
            removeItems(currentQuantity - targetQuantity);
        }
    }

    @Step("Get full item info: name and quantity")
    public String getItemInfo() {
        return String.format("Item: %s, Quantity: %d", getItemName(), getQuantity());
    }

}
