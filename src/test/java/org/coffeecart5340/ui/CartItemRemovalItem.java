package org.coffeecart5340.ui;

import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CartItemRemovalItem extends BaseUiTestRunner {

    private MenuPage menuPage;

    @Test(priority = 1)
    public void verifyRemovingItemFromTheCartUpdatesTheTotalAndCartState() {
        menuPage = new MenuPage(driver);
        CartPage cartPage = menuPage
                .clickCoffeeCup("Espresso")
                .goToCartPage();
        softAssert.assertTrue(cartPage.cartListIsDisplayed(),
                "The cart list is not displayed after adding an item to the cart");
        softAssert.assertEquals(cartPage.getCartItemList().getAllItems().size(),
                1,
                "The cart item is not added");
        softAssert.assertAll();
        cartPage.clickDeleteButton("Espresso");
        softAssert = new SoftAssert();
        softAssert.assertEquals(cartPage.getNoItemText(),
                "No coffee, go add some.", "The cart item is not removed");
        softAssert.assertFalse(cartPage.cartListIsDisplayed(),
                "The cart list is still displayed after removing the only item in the cart");

        softAssert.assertAll();
    }
}
