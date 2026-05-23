package org.coffeecart5340.ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class GitHubPage extends BasePage {
    public GitHubPage(WebDriver driver) {
        super(driver);
    }

    @Step("Navigating to menu page")
    public MenuPage navigateToMenuPage(){
        return getHeader().clickMenuButton();
    }

    @Step("Navigating to cart page")
    public CartPage navigateToCartPage(){
        return getHeader().clickCardButton();
    }
}
