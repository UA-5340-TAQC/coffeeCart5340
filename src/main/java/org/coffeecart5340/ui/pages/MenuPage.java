package org.coffeecart5340.ui.pages;

import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.components.TotalButtonMenuComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MenuPage extends BasePage {

    @FindBy(css = "ul.cart-preview li.list-item")

    private List<WebElement> cartPreviewElements;

    @FindBy(css = ".pay-container")
    private WebElement totalButtonContainer;

    public MenuPage(WebDriver driver) {
        super(driver);
    }

    public List<CartPreviewComponent> getCartPreviews() {
        return cartPreviewElements.stream().map(
                element -> new CartPreviewComponent(element)
        ).toList();
    }

    public TotalButtonMenuComponent getTotalButtonMenuComponent() {
        return new TotalButtonMenuComponent(driver, totalButtonContainer);
    }
}