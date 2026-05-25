package org.coffeecart5340.ui.pages;

import io.qameta.allure.Step;
import org.coffeecart5340.ui.components.CartItemComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    @FindBy(xpath = ".//li[@class='list-header']/following-sibling::li")
    private List<WebElement> cartItemsRoot;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    private List<CartItemComponent> getCartItems(){
        List<CartItemComponent> cartItems = new ArrayList<>();
        for(WebElement cartItemRoot : cartItemsRoot) {
            cartItems.add(new CartItemComponent(cartItemRoot));
        }
        return cartItems;
    }

    public CartItemComponent getCartItemByName(String name){
        return getCartItems()
                .stream()
                .filter(item -> item.getItemName().equals(name))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Item not found: " + name ));
    }

    @Step("Clicking plus button {quantity} times for item: {name}")
    public CartPage clickPlusButtonMultiply(int quantity, String name){
        for(int iii = 1; iii <= quantity; iii++) {
            getCartItemByName(name).clickPlusButton();
        }
        return new CartPage(driver);
    }

    @Step("Clicking minus button {quantity} times for item: {name}")
    public CartPage clickMinusButtonMultiply(int quantity, String name){
        for(int iii = 1; iii <= quantity; iii++){
            getCartItemByName(name).clickMinusButton();
        }
        return new CartPage(driver);
    }

    @Step("Clicking total delete button for item: {name}")
    public CartPage clickDeleteButton(String name){
        getCartItemByName(name).clickDeleteButton();
        return new CartPage(driver);
    }

    @Step("Clicking plus button for item: {name}")
    public CartPage clickPlusButtonByName(String name){
        getCartItemByName(name).clickPlusButton();
        return new CartPage(driver);
    }

    @Step("Clicking minus button for item: {name}")
    public CartPage clickMinusButtonByName(String name){
        getCartItemByName(name).clickMinusButton();
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