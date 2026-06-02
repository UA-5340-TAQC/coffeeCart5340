package org.coffeecart5340.ui.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartItemListComponent extends BaseComponent {

    @FindBy(css = "li.list-item")
    private List<WebElement> itemElements;

    public CartItemListComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public List<CartItemComponent> getAllItems() {
        return itemElements.stream()
                .map(element ->  new CartItemComponent(driver, element)).toList();
    }

    public CartItemComponent getItemByName(String expectedName) {
        return getAllItems().stream()
                .filter(item -> item.getItemName().equals(expectedName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(expectedName + " not found"));
    }

    public List<String> getAllItemNames() {
        return getAllItems().stream()
                .map(CartItemComponent::getItemName)
                .toList();
    }

    public double getCalculatedTotalPrice() {
        return getAllItems().stream()
                .mapToDouble(CartItemComponent::getTotalPrice)
                .sum();
    }

    public void clearCart() {
        List<CartItemComponent> currentItems = getAllItems();
        for (CartItemComponent item : currentItems) {
            item.clickDeleteButton();
        }
    }

    public boolean isEmpty() {
        return getAllItems().isEmpty();
    }
}