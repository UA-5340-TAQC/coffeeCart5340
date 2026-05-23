package org.coffeecart5340.ui.pages;

import org.openqa.selenium.WebDriver;

public class GitHubPage extends BasePage {
    public GitHubPage(WebDriver driver) {
        super(driver);
    }

    public MenuPage navigateToMenuPage(){
        getHeader().clickMenuButton();
        return new MenuPage(driver);
    }

    public CartPage navigateToCartPage(){
        getHeader().clickCardButton();
        return new CartPage(driver);
    }
}
