package org.coffeecart5340.ui.pages;

import io.qameta.allure.Step;
import org.coffeecart5340.ui.components.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MenuPage extends BasePage {

    @FindBy(css = "ul.cart-preview li.list-item")
    private List<WebElement> cartPreviewElements;

    @FindBy(xpath = "//li[.//div[contains(@class, 'cup-body')]]")
    private List<WebElement> cupCards;

    @FindBy(css = ".pay-container")
    private WebElement totalButtonContainer;

    @FindBy(className = "snackbar")
    private WebElement snackbar;


    // --- Constructor ---
    public MenuPage(WebDriver driver) {
        super(driver);
    }


    // --- Component Getters ---
    public TotalButtonComponent getTotalButton() {
        WebElement payContainerRoot = driver.findElement(By.className("pay-container"));
        return new TotalButtonComponent(payContainerRoot);
    }

    public DiscountComponent getDiscountModal() {
        WebElement root = driver.findElement(By.className("promo"));
        return new DiscountComponent(root);
    }

    public List<CartPreviewComponent> getCartPreviews() {
        return cartPreviewElements.stream().map(CartPreviewComponent::new).toList();
    }

    public CupCardComponent getCupCardByName(String name) {
        return cupCards.stream().map(CupCardComponent::new)
                .filter(card -> card.getCupName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Cup card not found: " + name));
    }

    public TotalButtonMenuComponent getTotalButtonMenuComponent() {
        return new TotalButtonMenuComponent(driver, totalButtonContainer);
    }

    public PaymentModalComponent getPaymentModal() {
        WebElement modalRoot = driver.findElement(By.cssSelector("form[aria-label='Payment form']"));
        return new PaymentModalComponent(driver, modalRoot);
    }

    @Step("Navigating to GitHub page")
    public GitHubPage goToGitHubPage() {
        return getHeader().clickGitHubButton();
    }

    @Step("Navigating to cart page")
    public CartPage goToCartPage() {
        return getHeader().clickCardButton();
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
        return getCartPreviews().stream()
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
