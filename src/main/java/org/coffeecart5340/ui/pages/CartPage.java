package org.coffeecart5340.ui.pages;

import io.qameta.allure.Step;
import org.coffeecart5340.ui.components.CartItemComponent;
import org.coffeecart5340.ui.components.DiscountComponent;
import org.coffeecart5340.ui.components.TotalButtonComponent;
import org.coffeecart5340.ui.modals.PaymentModal;
import org.coffeecart5340.ui.components.CartItemListComponent;
import org.openqa.selenium.NoSuchElementException;
import org.coffeecart5340.ui.components.TotalButtonComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = "div.list > div > ul")
    private WebElement cartListRoot;

    @FindBy(className = "promo")
    private WebElement discountModalRoot;

    @FindBy(css = ".pay-container")
    private WebElement totalButtonContainer;

    @FindBy(xpath = "//p")
    private WebElement noItemText;

    private TotalButtonComponent totalButton;
    private DiscountComponent discountComponent;
    private PaymentModal paymentModal;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartItemListComponent getCartItemList(){
        return new CartItemListComponent(driver, cartListRoot);
    }
    public Boolean cartListIsDisplayed(){
        try {
            return cartListRoot.isDisplayed();
        } catch (NoSuchElementException e){
            return false;
        }
    }

    public TotalButtonComponent getTotalButton() {
        if(totalButton == null){
            return new TotalButtonComponent(driver, totalButtonContainer);
        }
        return totalButton;
    }

    public PaymentModal getPaymentModal() {
        if(paymentModal == null){
            return new PaymentModal(driver);
        }
        return paymentModal;
    }

    public DiscountComponent getDiscount() {
        if(discountComponent == null){
            return new DiscountComponent(driver, discountModalRoot);
        }
        return discountComponent;
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

    public String getNoItemText(){
        waitUntilElementIsVisible(noItemText);
        return noItemText.getText();
    }

    public TotalButtonComponent getTotalButton() {
        return new TotalButtonComponent(driver.findElement(By.className("pay-container")));
    }


}
