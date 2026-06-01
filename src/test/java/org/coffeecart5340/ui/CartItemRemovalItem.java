package org.coffeecart5340.ui;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.coffeecart5340.ui.enumData.CoffeeType;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("Coffee Cart Application")
@Feature("Cart Functionality")
@Owner("Dmytro Syadro")
@Tag("UI")
public class CartItemRemovalItem extends BaseUiTestRunner {

    private MenuPage menuPage;

    private static final String coffee = CoffeeType.ESPRESSO.getCoffee();

    @Story("Removing item from cart")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("https://github.com/UA-5340-TAQC/coffeeCart5340/issues/7")
    @Tag("Regression")
    @Description("Verify that removing the only product from the cart updates the cart state to empty and displays the appropriate message.")
    @Test(priority = 1)
    public void verifyRemovingItemFromTheCartUpdatesTheTotalAndCartState() {
        menuPage = new MenuPage(driver);
        CartPage cartPage = menuPage
                .clickCoffeeCup(coffee)
                .goToCartPage();

        softAssert.assertTrue(cartPage.cartListIsDisplayed(),
                "The cart list is not displayed after adding an item to the cart");
        softAssert.assertEquals(cartPage.getCartItemList().getAllItems().size(),
                1,
                "The cart item is not added");
        softAssert.assertAll();

        cartPage.clickDeleteButton(CoffeeType.ESPRESSO.getCoffee());

        softAssert = new SoftAssert();
        softAssert.assertEquals(cartPage.getNoItemText(),
                "No coffee, go add some.", "The cart item is not removed");

        softAssert.assertFalse(cartPage.cartListIsDisplayed(),
                "The cart list is still displayed after removing the only item in the cart");

        softAssert.assertAll();
    }
}
