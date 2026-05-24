package org.coffeecart5340.ui.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.stream.Collectors;

public class CupComponent extends BaseComponent {
    private final By ingredientLocator = By.cssSelector("div");

    public CupComponent(WebElement rootElement) {
        super(rootElement);
    }

    public List<IngredientComponent> getIngredients() {
        List<WebElement> ingredientElements = rootElement.findElements(ingredientLocator);

        return ingredientElements.stream()
                .map(IngredientComponent::new)
                .collect(Collectors.toList());
    }
}