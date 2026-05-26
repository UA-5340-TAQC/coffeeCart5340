package org.coffeecart5340.ui;

import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;

public class CartItemRemovalItem extends BaseUiTestRunner {

    private MenuPage menuPage;
    @Test(priority = 4)
    public void VerifyRemovingItemFromTheCartUpdatesTheTotalAndCartState(){
        menuPage = new MenuPage(driver);
        CartPage cartPage = menuPage.clickCoffeeCup("Espresso")
                .goToCartPage()
                .clickDeleteButton("Espresso");
        softAssert.assertEquals(cartPage.getNoItemText(), "No coffee, go add some.",
                "The cart item is not removed");
        softAssert.assertEquals(cartPage.getCartItems().size(), 0,
                "The cart item is not removed");

        softAssert.assertAll();
    }
}
