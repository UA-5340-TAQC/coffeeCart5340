package org.coffeecart5340.ui.pages;

import io.qameta.allure.Step;
import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.components.CupCardComponent;
import org.openqa.selenium.NoSuchElementException;
import org.coffeecart5340.ui.components.TotalButtonMenuComponent;
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

    public MenuPage(WebDriver driver) {
        super(driver);
    }

    public List<CartPreviewComponent> getCartPreviews() {
        return cartPreviewElements.stream()
                .map(CartPreviewComponent::new)
                .toList();
    }

    public CupCardComponent getCupCardByName(String name) {
        return cupCards.stream()
                .map(CupCardComponent::new)
                .filter(card -> card.getCupName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Cup card not found: " + name));
    }

    public TotalButtonMenuComponent getTotalButtonMenuComponent() {
        return new TotalButtonMenuComponent(driver, totalButtonContainer);
    }

    @Step("Navigating to GitHub page")
    public GitHubPage goToGitHubPage(){
        return getHeader().clickGitHubButton();
    }

    @Step("Navigating to cart page")
    public CartPage goToCartPage(){
        return getHeader().clickCardButton();
    }
}