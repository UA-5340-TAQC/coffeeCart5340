package org.coffeecart5340.ui.modals;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddToCartModal extends BaseModal {

    @Getter
    @FindBy(xpath = ".//button[text()='Yes']")
    private WebElement yesButton;

    @Getter
    @FindBy(xpath = ".//button[text()='No']")
    private WebElement noButton;

    @Getter
    @FindBy(xpath = ".//p/strong")
    private WebElement coffeeName;

    @Getter
    @FindBy(xpath = ".//p")
    private WebElement description;

    public AddToCartModal(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public void ClickYesButton(){
        waitAndClickElement(yesButton);
    }

    public void ClickNoButton(){
        waitAndClickElement(noButton);
    }

    public String getName(){
        return coffeeName.getText();
    }

    public String getDescription(){
        return description.getText();
    }



}
