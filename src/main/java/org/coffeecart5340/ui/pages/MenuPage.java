package org.coffeecart5340.ui.pages;

import io.qameta.allure.Step;
import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MenuPage extends BasePage {

    @FindBy(css = "ul.cart-preview li.list-item")

    private List<WebElement> cartPreviewElements;

    public MenuPage(WebDriver driver) {
        super(driver);
    }

    public List<CartPreviewComponent> getCartPreviews() {
        return cartPreviewElements.stream().map(
                element -> new CartPreviewComponent(element)
        ).toList();
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