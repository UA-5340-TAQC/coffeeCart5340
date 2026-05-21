package org.coffeecart5340.ui.pages;

import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MenuPage extends BasePage {

    public MenuPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "ul.cart-preview li.list-item")

    private List<WebElement> cartPreviewElements;


    public List<CartPreviewComponent> getCartPreviews() {
        return cartPreviewElements.stream()
    .map(element -> new CartPreviewComponent(driver, element))
    .toList();
    }
}