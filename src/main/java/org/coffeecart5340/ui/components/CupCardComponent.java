package org.coffeecart5340.ui.components;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

    public CupCardComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }


    public CupCardComponent clickCup() {
        waitAndClickElement(cupBody);
        return this;
    }

    public CupCardComponent rightClickCup(){
        Actions actions = new Actions(driver);
        actions.contextClick(cupBody).perform();
        return this;
    }

    public CupCardComponent translateCupTitle(){
        Actions actions = new Actions(driver);
        actions.doubleClick(cupTitle).perform();
        return this;
    }

    public String getCupName() {
        return cupBody.getAttribute("aria-label");
    }

    public String getCupTitleText() {
        String fullText = cupTitle.getText();
        return fullText.split("\n")[0].trim();
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