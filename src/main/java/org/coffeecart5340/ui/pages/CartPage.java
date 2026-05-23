package org.coffeecart5340.ui.pages;

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

    public CartPage clickPlusButtonMultiply(int quantity, String name){
        for(int iii = 1; iii <= quantity; iii++) {
            getCartItemByName(name).clickPlusButton();
        }
        return new CartPage(driver);
    }

    public CartPage clickMinusButtonMultiply(int quantity, String name){
        for(int iii = 1; iii <= quantity; iii++){
            getCartItemByName(name).clickMinusButton();
        }
        return new CartPage(driver);
    }

    public CartPage clickDeleteButton(String name){
        getCartItemByName(name).clickDeleteButton();
        return new CartPage(driver);
    }

    public CartPage clickPlusButtonByName(String name){
        getCartItemByName(name).clickPlusButton();
        return new CartPage(driver);
    }

    public CartPage clickMinusButtonByName(String name){
        getCartItemByName(name).clickMinusButton();
        return new CartPage(driver);
    }

    public GitHubPage goToGitHubPage(){
        getHeader().clickGitHubButton();
        return new GitHubPage(driver);
    }

    public MenuPage goToMenuPage(){
        getHeader().clickMenuButton();
        return new MenuPage(driver);
    }

}