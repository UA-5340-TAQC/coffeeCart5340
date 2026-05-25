package org.coffeecart5340.ui.pages;

import io.qameta.allure.Step;
import org.coffeecart5340.ui.components.CartItemListComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {

    @FindBy(css = "div.list > div > ul")
    private WebElement cartListRoot;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartItemListComponent getCartItemList(){
        return new CartItemListComponent(driver, cartListRoot);
    }

    @Step("Clicking plus button {quantity} times for item: {name}")
    public CartPage clickPlusButtonMultiply(int quantity, String name){
        for(int iii = 1; iii <= quantity; iii++) {
            getCartItemList().getItemByName(name).clickPlusButton();
        }
        return new CartPage(driver);
    }

    @Step("Clicking minus button {quantity} times for item: {name}")
    public CartPage clickMinusButtonMultiply(int quantity, String name){
        for(int iii = 1; iii <= quantity; iii++){
            getCartItemList().getItemByName(name).clickMinusButton();
        }
        return new CartPage(driver);
    }

    @Step("Clicking total delete button for item: {name}")
    public CartPage clickDeleteButton(String name){
        getCartItemList().getItemByName(name).clickDeleteButton();
        return new CartPage(driver);
    }

    @Step("Clicking plus button for item: {name}")
    public CartPage clickPlusButtonByName(String name){
        getCartItemList().getItemByName(name).clickPlusButton();
        return new CartPage(driver);
    }

    @Step("Clicking minus button for item: {name}")
    public CartPage clickMinusButtonByName(String name){
        getCartItemList().getItemByName(name).clickMinusButton();
        return new CartPage(driver);
    }

    @Step("Navigating to GitHub page")
    public GitHubPage goToGitHubPage(){
        return getHeader().clickGitHubButton();
    }

    @Step("Navigating to menu page")
    public MenuPage goToMenuPage(){
        return getHeader().clickMenuButton();
    }
}