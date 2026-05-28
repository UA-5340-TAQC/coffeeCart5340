package org.coffeecart5340.ui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IngredientComponent extends BaseComponent {


    public IngredientComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public String getName() {
        return rootElement.getText();
    }

    public String getType() {
        return rootElement.getAttribute("class");
    }
}
