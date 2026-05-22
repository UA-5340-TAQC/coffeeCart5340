package org.coffeecart5340.ui.components;

import lombok.Getter;
import org.coffeecart5340.ui.pages.MenuPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DiscountComponent extends BaseComponent{

    @Getter
    @FindBy(xpath = ".//button[@class='yes']")
    private WebElement yesButton;

    @Getter
    @FindBy(xpath = ".//button[contains(text(), 'Nah')]")
    private WebElement noButton;

    @FindBy(xpath = ".//span")
    private WebElement discountText;

    @Getter
    @FindBy(xpath = ".//div[contains(@class, 'cup-body')]")
    private WebElement cupRootElement;

    @Getter
    private final CupComponent cupComponent;

    public DiscountComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
        this.cupComponent = new CupComponent(driver, cupRootElement);
    }

    public String getDiscountText(){
        return discountText.getText();
    }

    public MenuPage clickYesButton(){
        waitAndClickElement(yesButton);
        return new MenuPage(driver);
    }

    public MenuPage clickNoButton(){
        waitAndClickElement(noButton);
        return new MenuPage(driver);
    }

    private void hoverOverNoButton(){
        hoverOverElement(noButton);
    }
}
