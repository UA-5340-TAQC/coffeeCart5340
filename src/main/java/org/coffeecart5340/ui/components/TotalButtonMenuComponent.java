package org.coffeecart5340.ui.components;

import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TotalButtonMenuComponent extends BaseComponent {

    @FindBy(css = "ul.cart-preview.show")
    private WebElement cartPreview;

    @FindBy(css = "ul.cart-preview.show li.list-item")
    private List<WebElement> cartPreviewItems;

    @FindBy(css = "button.pay[data-test='checkout']")
    private WebElement totalButton;

    public TotalButtonMenuComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @Step("Hover over Total button to show cart preview")
    public void hoverOverTotalButton() {
        hoverOverElement(totalButton);
    }

    @Step("Check if cart preview is visible")
    public boolean isCartPreviewVisible() {
        try {
            return cartPreview.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Get list of cart preview items")
    public List<CartPreviewComponent> getCartPreviewItems() {
        return cartPreviewItems.stream()
                .map(itemElement -> new CartPreviewComponent(driver, itemElement))
                .toList();
    }

    @Step("Get item count in cart preview")
    public int getCartPreviewItemCount() {
        return cartPreviewItems.size();
    }

    @Step("Click on Total button to proceed to checkout")
    public void clickTotalButton() {
        waitAndClickElement(totalButton);
    }

    @Step("Check if cart preview is empty")
    public boolean isCartPreviewEmpty() {
        return cartPreviewItems.isEmpty();
    }
}
