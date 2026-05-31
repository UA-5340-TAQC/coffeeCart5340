package org.coffeecart5340.ui;

import org.coffeecart5340.ui.components.CupCardComponent;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;

public class EspressoMacchiatoCupTest extends BaseUiTestRunner {

    @Test
    public void verifyEspressoMacchiatoCupIsDisplayedWithCorrectDataAndCanBeAddedToCart() {

        String productName = "Espresso Macchiato";

        MenuPage menuPage = new MenuPage(driver);

        CupCardComponent espressoMacchiatoCup =
                menuPage.getCupCardByName(productName);

        softAssert.assertTrue(
                espressoMacchiatoCup.isCupDisplayed(),
                "Espresso Macchiato cup should be visible on the page"
        );

        softAssert.assertEquals(
                espressoMacchiatoCup.getCupName(),
                productName,
                "Cup name should be displayed as Espresso Macchiato"
        );

        softAssert.assertEquals(
                espressoMacchiatoCup.getCupPriceText(),
                "$12.00",
                "Cup price should be displayed as $12.00"
        );

        espressoMacchiatoCup.clickCup();

        softAssert.assertEquals(
                menuPage.getHeader().getCartText(),
                "cart (1)",
                "Cart counter should display cart (1)"
        );

        // Postcondition
        CartPage cartPage = menuPage.goToCartPage();
        cartPage.clickDeleteButton(productName);

        softAssert.assertAll();
    }
}
