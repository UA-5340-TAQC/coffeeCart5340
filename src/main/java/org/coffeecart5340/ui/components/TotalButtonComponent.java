package org.coffeecart5340.ui.components;

import org.coffeecart5340.ui.modals.PaymentModal;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class TotalButtonComponent extends BaseComponent {

    @FindBy(css = "button[data-test='checkout']")
    private WebElement checkoutButton;

    public TotalButtonComponent(WebDriver driver ,WebElement rootElement) {
        super(driver, rootElement);
    }

    @Step("Getting total price from the checkout button")
    public double getTotalPrice() {
        // We need to remove "Total: $" and parse it.
        String text = checkoutButton.getText();
        String normalized = text
                .replaceFirst("^\\s*Total:\\s*\\$", "")
                .replace(",", "")
                .trim();
        return Double.parseDouble(normalized);
    }

    @Step("Hovering over the pay container to reveal cart preview")
    public TotalButtonComponent hoverOverButton() {
        hoverOverElement(checkoutButton);
        return this;
    }

    @Step("Clicking the checkout button")
    public PaymentModal clickCheckoutButton() {
        waitAndClickElement(checkoutButton);
        return new PaymentModal(driver);
    }

    public boolean isTotalButtonEnabled() {
        return checkoutButton.isEnabled();
    }

    public boolean isCheckoutButtonDisplayed() {
        return checkoutButton.isDisplayed();
    }

    public boolean isCheckoutButtonEnabled() {
        return checkoutButton.isEnabled();
    }

    public String getCheckoutButtonText() {
        return checkoutButton.getText().trim();
    }
}
