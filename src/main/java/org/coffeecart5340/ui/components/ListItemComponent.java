package org.coffeecart5340.ui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ListItemComponent extends BaseComponent{

    @FindBy(xpath = ".//span[not(@class)]")
    protected WebElement itemNameSpan;

    @FindBy(xpath = ".//span[@class='unit-desc']")
    protected WebElement quantitySpan;

    @FindBy(xpath = ".//button[contains(@aria-label, 'Add one')]")
    protected WebElement plusButton;

//    @FindBy(xpath = ".//button[contains(@aria-label, 'Remove one')]")
//    protected WebElement minusButton;

    public ListItemComponent(WebElement rootElement) {
        super(rootElement);
    }

//    public String getRawText() {
//        return rootElement.getText().trim();
//    }

    public String getItemName() {
        return itemNameSpan.getText().trim();
    }

    public int getQuantity() {
        try {
            String number = quantitySpan.getText().replaceAll("[^0-9]", "");
            return number.isEmpty() ? 0 : Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void increment(){
        waitAndClickElement(plusButton);
    }

//    public void decrement(){
//        waitAndClickElement(minusButton);
//    }

    public void waitForQuantity(int expectedQuantity) {
        wait.until(driver -> getQuantity() == expectedQuantity);
    }


}
