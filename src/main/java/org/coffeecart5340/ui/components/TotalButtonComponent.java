package org.coffeecart5340.ui.components;

import org.coffeecart5340.ui.modals.PaymentModal;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;
import lombok.Getter;

public class TotalButtonComponent extends BaseComponent {

    @Getter
    @FindBy(css = "button[data-test='checkout']")
    private WebElement checkoutButton;

    public TotalButtonComponent(WebElement rootElement) {
        super(rootElement);
    }

    @Step("Getting total price from the checkout button")
    public Float getTotalPrice() {
        // We need to remove "Total: $" and parse it.
        String text = checkoutButton.getText();
        return Float.parseFloat(text.replace("Total: $", "").trim());
    }

    @Step("Hovering over the pay container to reveal cart preview")
    public TotalButtonComponent hoverOverButton() {
        hoverOverElement(rootElement);
        return this;
    }

    @Step("Clicking the checkout button")
    public PaymentModal clickCheckoutButton() {
        waitAndClickElement(checkoutButton);
        return new PaymentModal(driver);
    }
}
