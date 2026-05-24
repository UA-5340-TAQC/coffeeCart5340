package org.coffeecart5340.ui.components;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CupCardComponent extends BaseComponent {

    @Getter
    @FindBy(xpath = ".//h4")
    private WebElement cupTitle;

    @Getter
    @FindBy(xpath = ".//small")
    private WebElement cupPrice;

    @Getter
    @FindBy(xpath = ".//div[contains(@class, 'cup-body')]")
    private WebElement cupBody;

    public CupCardComponent(WebElement rootElement) {
        super(rootElement);
    }

    public CupCardComponent clickCup() {
        waitAndClickElement(cupBody);
        return this;
    }

    public String getCupName() {
        return cupBody.getAttribute("aria-label");
    }

    public String getCupTestAttribute() {
        return cupBody.getAttribute("data-test");
    }

    public String getCupPriceText() {
        return cupPrice.getText();
    }

    public Float getCupPrice() {
        return Float.parseFloat(getCupPriceText()
                .replace("$", "")
                .trim());
    }

    public boolean isCupDisplayed() {
        return cupBody.isDisplayed();
    }
}