package org.coffeecart5340.ui.modals;

import io.qameta.allure.Step;
import lombok.Getter;
import org.coffeecart5340.ui.pages.MenuPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddToCartModal extends BaseModal {

    @FindBy(xpath = "//dialog[@data-cy='add-to-cart-modal']")
    private WebElement rootElement;

    @Getter
    @FindBy(xpath = ".//button[text()='Yes']")
    private WebElement yesButton;

    @Getter
    @FindBy(xpath = ".//button[text()='No']")
    private WebElement noButton;

    @Getter
    @FindBy(xpath = ".//p/strong")
    private WebElement coffeeName;

    @FindBy(xpath = ".//p")
    private WebElement description;

    public AddToCartModal(WebDriver driver) {
        super(driver);
    }

    @Override
    public WebElement getRootElement(){
        return rootElement;
    }

    @Step("Clicking yes button")
    public MenuPage clickYesButton(){
        waitAndClickElement(yesButton);
        return new MenuPage(driver);
    }

    @Step("Clicking no button")
    public MenuPage clickNoButton(){
        waitAndClickElement(noButton);
        return new MenuPage(driver);
    }

    public String getName(){
        return coffeeName.getText();
    }

    public String getDescription(){
        return description.getText();
    }

}
