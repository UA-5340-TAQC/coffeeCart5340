package org.coffeecart5340.ui.modals;

import io.qameta.allure.Step;
import lombok.Getter;
import org.coffeecart5340.ui.pages.MenuPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentModal extends BaseModal {

    @FindBy(xpath = "//div[contains(@class, 'modal')]")
    private WebElement rootElement;

    @Getter
    @FindBy(id = "name")
    private WebElement nameInput;

    @Getter
    @FindBy(id = "email")
    private WebElement emailInput;

    @Getter
    @FindBy(id = "promotion")
    private WebElement promotionCheckbox;

    @Getter
    @FindBy(id = "submit-payment")
    private WebElement submitButton;

    @Getter
    @FindBy(xpath = ".//button[@class='close']")
    private WebElement closeButton;

    public PaymentModal(WebDriver driver) {
        super(driver);
    }

    @Override
    protected WebElement getRootElement() {
        return rootElement;
    }

        @Step("Entering name: {name} in Payment Modal")
    public PaymentModal enterName(String name) {
        waitAndSendKeys(nameInput, name);
        return this;
    }

    @Step("Entering email: {email} in Payment Modal")
    public PaymentModal enterEmail(String email) {
        waitAndSendKeys(emailInput, email);
        return this;
    }

    @Step("Clicking promotion checkbox in Payment Modal")
    public PaymentModal clickPromotionCheckbox() {
        waitAndClickElement(promotionCheckbox);
        return this;
    }

    @Step("Clicking Submit Payment button")
    public MenuPage clickSubmitButton() {
        waitAndClickElement(submitButton);
        // Assuming successful payment keeps us on/returns us to MenuPage
        return new MenuPage(driver);
    }

    @Step("Clicking close (x) button on Payment Modal")
    public MenuPage clickCloseButton() {
        waitAndClickElement(closeButton);
        return new MenuPage(driver);
    }
}
