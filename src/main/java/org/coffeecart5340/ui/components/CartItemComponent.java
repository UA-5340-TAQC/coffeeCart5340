package org.coffeecart5340.ui.components;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;

public class CartItemComponent extends BaseComponent {

    @FindBy(xpath = ".//*[@class='unit-controller']/parent::*/following-sibling::div[1]")
    private WebElement priceText;

    @FindBy(xpath = ".//button[text()='+']")
    private WebElement plusButton;

    @FindBy(xpath = ".//button[text()='-']")
    private WebElement minusButton;

    @FindBy(xpath = ".//div[not(@class)][1]")
    private WebElement itemName;

    @FindBy(xpath = ".//span[@class='unit-desc']")
    private WebElement unitDesc;

    @FindBy(xpath = ".//button[contains(@aria-label,'Remove one')]")
    private WebElement deleteButton;

    public CartItemComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public Float getTotalPrice() {
        return Float.parseFloat(priceText.getText().replace("$", "").trim());
    }

    public CartItemComponent clickDeleteButton() {
        waitAndClickElement(deleteButton);
        return this;
    }

    public void clickPlusButton() {
        waitAndClickElement(plusButton);
    }

    public void clickMinusButton() {
        waitAndClickElement(minusButton);
    }

    public Float getOneItemPrice() {
        return Float.parseFloat(Arrays.stream(getUnitDescText().split("x"))
                .toList().
                getFirst()
                .replace("$", "")
                .trim());
    }

    public int getQuantity() {
        return Integer.parseInt(Arrays.stream(getUnitDescText().split("x"))
                .toList()
                .getLast()
                .trim());
    }

    public String getItemName() {
        return itemName.getText();
    }

    public String getUnitDescText() {
        return unitDesc.getText();
    }

    public void hoverOverDeleteButton() {
        waitUntilElementIsVisible(deleteButton);
        hoverOverElement(deleteButton);
    }

    public String getDeleteButtonHoverColor() {
        return deleteButton.getCssValue("color");
    }

    public String getDeleteButtonHoverBackgroundColor() {
        return deleteButton.getCssValue("background-color");
    }

    public boolean isPlusButtonAvailable() {
        try {
            return plusButton.isDisplayed() && plusButton.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isPlusButtonDisplayed() {
        try {
            return plusButton.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isMinusButtonDisplayed() {
        try {
            return minusButton.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isDeleteButtonDisplayed() {
        try {
            return deleteButton.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isPlusButtonEnabled() {
        return plusButton.isEnabled();
    }
}
