package org.coffeecart5340.ui.pages;

import java.util.List;

import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.components.DiscountComponent;
import org.coffeecart5340.ui.components.TotalButtonComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class MenuPage extends BasePage {

    // --- Locators ---
    @FindBy(css = "ul.cart-preview li.list-item")
    private List<WebElement> cartPreviewElements;

    @FindBy(className = "pay-container")
    private WebElement payContainerRoot;

    @FindBy(className = "snackbar")
    private WebElement snackbar;

    @FindBy(className = "promo")
    private WebElement discountModalRoot;


    // --- Constructor ---
    public MenuPage(WebDriver driver) {
        super(driver);
    }


    // --- Component Getters ---
    public TotalButtonComponent getTotalButton() {
        return new TotalButtonComponent(driver, payContainerRoot);
    }

    public DiscountComponent getDiscountModal() {
        return new DiscountComponent(driver, discountModalRoot);
    }

    public List<CartPreviewComponent> getCartPreviews() {
        return cartPreviewElements.stream().map(
                element -> new CartPreviewComponent(driver, element)
        ).toList();
    }


    // --- Page Actions ---
    
    @Step("Adding {coffeeName} to cart")
    public MenuPage clickCoffeeCup(String coffeeName) {
        // The app replaces spaces with hyphens in the data-cy attribute (e.g., "Cafe Breve" -> "Cafe-Breve")
        String formattedName = coffeeName.trim().replace(" ", "-");
        By cupLocator = By.cssSelector("div[data-cy=\"" + formattedName + "\"]");
        
        waitAndClickElement(cupLocator);
        return this;
    }

    public CartPreviewComponent getCartPreviewItemByName(String name) {
        return getCartPreviews()
                .stream()
                .filter(item -> item.getItemName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart preview item not found: " + name));
    }

    @Step("Getting success snackbar text")
    public String getSnackbarText() {
        waitUntilElementIsVisible(snackbar);
        return snackbar.getText();
    }
}
