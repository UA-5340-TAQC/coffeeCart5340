package org.coffeecart5340.ui.components;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;

public class CartItemComponent extends BaseComponent {

    @FindBy(xpath = ".//*[@class='unit-controller']/parent::*/following-sibling::div[1]")
    private WebElement priceText;

    @Getter
    @FindBy(xpath = ".//button[text()='+']")
    private WebElement plusButton;

    @Getter
    @FindBy(xpath = ".//button[text()='-']")
    private WebElement minusButton;

    @Getter
    @FindBy(xpath = ".//div[not(@class)][1]")
    private WebElement itemName;

    @Getter
    @FindBy(xpath = ".//span[@class='unit-desc']")
    private WebElement unitDesc;

    @Getter
    @FindBy(xpath = ".//button[@class='delete']")
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
}
