package org.coffeecart5340.ui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CupComponent extends BaseComponent {
    @FindBy(xpath = ".//div[@class='cup-body']/div")
    private List<WebElement> ingredients;


    public CupComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public List<IngredientComponent> getIngredients() {
        return ingredients.stream()
                .map(ingredientElement -> new IngredientComponent(driver, ingredientElement))
                .collect(Collectors.toList());
    }
}