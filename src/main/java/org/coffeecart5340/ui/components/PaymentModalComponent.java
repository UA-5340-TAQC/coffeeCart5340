package org.coffeecart5340.ui.components;

import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class PaymentModalComponent extends BaseComponent{

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "promotion")
    private WebElement promoCheckbox;

    @FindBy(id = "submit-payment")
    private WebElement submitButton;

    @FindBy(css = ".close")
    private WebElement closeIcon;

    public PaymentModalComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @Step("Enter name: {0}")
    public PaymentModalComponent enterName(String name) {
        nameInput.clear();
        nameInput.sendKeys(name);
        return this;
    }

    @Step("Enter email: {0}")
    public PaymentModalComponent enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    @Step("Check promotional messages box")
    public PaymentModalComponent checkPromo() {
        if (!promoCheckbox.isSelected()) {
            promoCheckbox.click();
        }
        return this;
    }

    @Step("Uncheck promotional messages box")
    public PaymentModalComponent uncheckPromo() {
        if (promoCheckbox.isSelected()) {
            promoCheckbox.click();
        }
        return this;
    }

    @Step("Click Submit button")
    public void clickSubmit() {
        submitButton.click();
    }

    @Step("Click Close icon")
    public void clickClose() {
        closeIcon.click();
    }

    @Step("Fill payment details - Name: {0}, Email: {1}, Promo: {2}")
    public void fillPaymentDetailsAndSubmit(String name, String email, boolean acceptPromo) {
        enterName(name);
        enterEmail(email);

        if (acceptPromo) {
            checkPromo();
        } else {
            uncheckPromo();
        }

        clickSubmit();
    }
}
