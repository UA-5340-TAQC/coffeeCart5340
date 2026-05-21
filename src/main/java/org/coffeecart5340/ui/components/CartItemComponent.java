package org.coffeecart5340.ui.components;

import lombok.Getter;
import org.coffeecart5340.ui.pages.CartPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
    @FindBy(xpath = ".//li[@class='list-item']/child::*[1]")
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

    public Double getTotalPrice(){
        return Double.parseDouble(priceText.getText().replace("$", "").trim());
    }

    public CartPage clickDeleteButton(String name){
        if(itemName.getText().equals(name)) {
            waitAndClickElement(deleteButton);
        }
        return new CartPage(driver);
    }

    public CartPage clickPlusButton(int quantity, String name){
        if(quantity == 0)
            throw new IllegalArgumentException("Quantity must be greater than 0");

        if(itemName.getText().equals(name)) {
            for (int i = 1; i <= quantity; i++) {
                waitAndClickElement(plusButton);
            }
        }
        return new CartPage(driver);
    }

    public CartPage clickMinusButton(int quantity, String name){
        if(quantity == 0 || quantity > getQuantity())
            throw new IllegalArgumentException("Quantity must be greater than 0 and less than or equal to current quantity");

        if(itemName.getText().equals(name)) {
            for (int i = 1; i <= quantity; i++) {
                waitAndClickElement(minusButton);
            }
        }
        return new CartPage(driver);
    }

    public Double getOneItemPrice(){
        return Double.parseDouble(Arrays.stream(getUnitDescText().split("x")).toList()
                .getFirst()
                .replace("$", "")
                .trim());
    }

    public int getQuantity(){
        return Integer.parseInt(Arrays.stream(getUnitDescText()
                        .split("x"))
                .toList().getLast().trim());
    }

    public String getItemName(){
        return itemName.getText();
    }

    private String getUnitDescText(){
        return unitDesc.getText();
    }

}
