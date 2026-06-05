package org.coffeecart5340.ui.pages;

import io.qameta.allure.Step;
import org.coffeecart5340.ui.components.CartItemListComponent;
import org.coffeecart5340.ui.components.DiscountComponent;
import org.coffeecart5340.ui.components.TotalButtonComponent;
import org.coffeecart5340.ui.modals.PaymentModal;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    private static final By MODAL = By.cssSelector("div.modal");
    private static final By MODAL_TITLE = By.cssSelector("div.modal h1");
    private static final By MODAL_CLOSE = By.cssSelector("div.modal button.close");

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
        if (totalButton == null) {
            totalButton = new TotalButtonComponent(driver, totalButtonContainer);
        }
        return totalButton;
    }

    public PaymentModal getPaymentModal() {
        if (paymentModal == null) {
            paymentModal = new PaymentModal(driver);
        }
        return paymentModal;
    }

    public DiscountComponent getDiscount() {
        if (discountComponent == null) {
            discountComponent = new DiscountComponent(driver, discountModalRoot);
        }
        return discountComponent;
    }


    @Step("Clicking plus button {quantity} times for item: {name}")
    public CartPage clickPlusButtonMultiply(int quantity, String name){
        for (int iii = 1; iii <= quantity; iii++) {
            getCartItemList().getItemByName(name).clickPlusButton();
        }
        return this;
    }

    @Step("Clicking minus button {quantity} times for item: {name}")
    public CartPage clickMinusButtonMultiply(int quantity, String name){
        for (int iii = 1; iii <= quantity; iii++) {
            getCartItemList().getItemByName(name).clickMinusButton();
        }
        return this;
    }

    @Step("Clicking total delete button for item: {name}")
    public CartPage clickDeleteButton(String name){
        getCartItemList().getItemByName(name).clickDeleteButton();
        return this;
    }

    @Step("Clicking plus button for item: {name}")
    public CartPage clickPlusButtonByName(String name){
        getCartItemList().getItemByName(name).clickPlusButton();
        return this;
    }

    @Step("Clicking minus button for item: {name}")
    public CartPage clickMinusButtonByName(String name){
        getCartItemList().getItemByName(name).clickMinusButton();
        return this;
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

    public boolean isPaymentModalDisplayed() {
        return !driver.findElements(MODAL).isEmpty();
    }

    public String getPaymentModalTitle() {
        return driver.findElement(MODAL_TITLE).getText().trim();
    }

    public boolean isPaymentModalCloseButtonDisplayed() {
        return !driver.findElements(MODAL_CLOSE).isEmpty();
    }

    public void clickPaymentModalCloseButton() {
        waitAndClickElement(MODAL_CLOSE);
    }

}
