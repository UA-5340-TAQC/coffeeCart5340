package org.coffeecart5340.ui.pages;

import io.qameta.allure.Step;
import org.coffeecart5340.ui.components.*;
import org.coffeecart5340.ui.modals.PaymentModal;
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

    @FindBy(className = "pay-container")
    private WebElement payContainerRoot;

    @FindBy(className = "snackbar")
    private WebElement snackbar;

    @FindBy(className = "promo")
    private WebElement discountModalRoot;


    private DiscountComponent discountComponent;
    private TotalButtonComponent totalButton;
    private PaymentModal paymentModal;


    // --- Constructor ---
    public MenuPage(WebDriver driver) {
        super(driver);
    }


    // --- Component Getters ---
    public TotalButtonComponent getTotalButton() {
        return new TotalButtonComponent(driver, payContainerRoot);
    }

    public DiscountComponent getDiscountModal() {
        if(discountComponent == null){
            return new DiscountComponent(driver, discountModalRoot);
        }
        return discountComponent;
    }

    public PaymentModal getPaymentModal() {
        if(paymentModal == null){
            return new PaymentModal(driver);
        }
        return paymentModal;
    }

    public List<CartPreviewComponent> getCartPreviews() {
        return cartPreviewElements.stream().map(element -> new CartPreviewComponent(driver, element)).toList();
    }

    public CupCardComponent getCupCardByName(String name) {
        return cupCards.stream().map(element -> new CupCardComponent(driver, element))
                .filter(card -> card.getCupName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Cup card not found: " + name));
    }

    public List<CupCardComponent> getAllCupCards() {
        return cupCards.stream()
                .map(element -> new CupCardComponent(driver, element))
                .toList();
    }

    public TotalButtonMenuComponent getTotalButtonMenuComponent() {
        return new TotalButtonMenuComponent(driver, totalButtonContainer);
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
        getCupCardByName(coffeeName).clickCup();
        return this;
    }

    @Step("Opening {coffeeName} \"Add to cart\" modal")
    public MenuPage contextClickCoffeeCup(String coffeeName) {
        getCupCardByName(coffeeName).rightClickCup();
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

    public MenuPage clickCupMultiply(String name, int times) {
        for(int iii = 1; iii <= times; iii++) {
            getCupCardByName(name).clickCup();
        }
        return this;
    }

    public boolean isSnackbarVisible() {
        try {
            return snackbar.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
