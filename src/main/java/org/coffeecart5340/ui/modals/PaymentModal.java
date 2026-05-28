package org.coffeecart5340.ui.modals;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NonNull;
import org.coffeecart5340.ui.pages.MenuPage;
import org.openqa.selenium.JavascriptExecutor;
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

    @Step("Entering name in Payment Modal")
    public PaymentModal enterName(String name) {
        waitAndSendKeys(nameInput, name);
        return this;
    }

    @Step("Entering email in Payment Modal")
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
        return new MenuPage(driver);
    }

    @Step("Clicking close (x) button on Payment Modal")
    public MenuPage clickCloseButton() {
        waitAndClickElement(closeButton);
        return new MenuPage(driver);
    }

    public boolean isPromotionCheckboxChecked() {
        return promotionCheckbox.isSelected();
    }

    public String getNameValue() {
        return nameInput.getAttribute("value");
    }

    public String getEmailValue() {
        return emailInput.getAttribute("value");
    }

    public String getEmailValidationMessage() {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;", emailInput);
    }


    @Step("Check promotional messages box")
    public PaymentModal checkPromo() {
        if (!promotionCheckbox.isSelected()) {
            waitAndClickElement(promotionCheckbox);
        }
        return this;
    }

    @Step("Uncheck promotional messages box")
    public PaymentModal uncheckPromo() {
        if (promotionCheckbox.isSelected()) {
            waitAndClickElement(promotionCheckbox);
        }
        return this;
    }

    @Step("Click Submit button")
    public void clickSubmit() {
        waitAndClickElement(submitButton);
    }

    @Step("Click Close icon")
    public void clickClose() {
        waitAndClickElement(closeButton);
    }

    @Step("Fill payment details - Name: {name}, Email: {email}, Promo: {acceptPromo}")
    public void fillPaymentDetailsAndSubmit(@NonNull String name, @NonNull String email, boolean acceptPromo) {
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
