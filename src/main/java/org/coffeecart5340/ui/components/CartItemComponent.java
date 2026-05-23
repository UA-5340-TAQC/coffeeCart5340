package org.coffeecart5340.ui.components;

import lombok.Getter;
import org.coffeecart5340.ui.pages.CartPage;
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


    public CartItemComponent(WebElement rootElement) {
        super(rootElement);
    }

    public Float getTotalPrice(){
        return Float.parseFloat(priceText.getText().replace("$", "").trim());
    }

    public CartItemComponent clickDeleteButton(){
        waitAndClickElement(deleteButton);
        return this;
    }

    public CartItemComponent clickPlusButton(){
        waitAndClickElement(plusButton);
        return this;
    }

    public CartItemComponent clickMinusButton(){
        waitAndClickElement(minusButton);
        return this;
    }

    public Float getOneItemPrice(){
        return Float.parseFloat(Arrays.stream(getUnitDescText().split("x")).toList()
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

    public String getUnitDescText(){
        return unitDesc.getText();
    }

}
