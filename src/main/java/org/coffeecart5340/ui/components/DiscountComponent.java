package org.coffeecart5340.ui.components;

import io.qameta.allure.Step;
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

    public DiscountComponent(WebElement rootElement) {
        super(rootElement);
        this.cupComponent = new CupComponent(cupRootElement);
    }

    public String getDiscountText(){
        return discountText.getText();
    }

    @Step("Clicking yes button")
    public MenuPage clickYesButton(){
        waitAndClickElement(yesButton);
        return new MenuPage(driver);
    }

    @Step("Clicking nah button")
    public MenuPage clickNoButton(){
        waitAndClickElement(noButton);
        return new MenuPage(driver);
    }

    public String getNoButtonStyle(){
        return noButton.getCssValue("background-color");
    }

    public String getYesButtonStyle(){
        return yesButton.getCssValue("background-color");
    }

    public void hoverOverNoButton(){
        hoverOverElement(noButton);
    }

    public void hoverOverYesButton(){
        hoverOverElement(yesButton);
    }
}
