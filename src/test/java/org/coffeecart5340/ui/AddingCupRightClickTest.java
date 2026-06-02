package org.coffeecart5340.ui;

import org.coffeecart5340.ui.modals.AddToCartModal;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;

public class AddingCupRightClickTest extends BaseUiTestRunner {
    private MenuPage menuPage;
    private CartPage cartPage;
    private AddToCartModal addToCartModal;

    @Test(priority = 1)
    public void verifyAddingACupOfCoffeeToTheCartUsingTheRightClickOnTheCard() {
        menuPage = new MenuPage(driver);

        var price = menuPage.getCupCardByName("Espresso").getCupPrice();

        menuPage.contextClickCoffeeCup("Espresso");

        addToCartModal = new AddToCartModal(driver);

        addToCartModal.hoverOverYesButton();

        addToCartModal.clickYesButton();

        softAssert.assertEquals(
                menuPage.getHeader().getCartCount(),
                1,
                "Cart badge count did not increase after adding an item"
        );

        menuPage.goToCartPage();

        cartPage = new CartPage(driver);

        softAssert.assertTrue(
                cartPage.getCartItemList().getAllItemNames().contains("Espresso"),
                "Espresso is missing from the cart items list"
        );

        softAssert.assertEquals(
                cartPage.getCartItemList().getCalculatedTotalPrice(),
                (double) price,
                "Calculated total price does not match the added item price"
        );

        cartPage.getCartItemList().clearCart();

        softAssert.assertAll();
    }
}