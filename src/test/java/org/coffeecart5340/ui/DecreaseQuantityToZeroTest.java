package org.coffeecart5340.ui;

import io.qameta.allure.Description;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;

public class DecreaseQuantityToZeroTest extends BaseUiTestRunner {

    private static final String COFFEE_NAME = "Flat White";
    private static final BigDecimal EXPECTED_INITIAL_TOTAL = new BigDecimal("18.00");
    private static final BigDecimal EXPECTED_FINAL_TOTAL = new BigDecimal("0.00");
    private static final String EXPECTED_EMPTY_CART_TEXT = "No coffee, go add some.";

    @Test(priority = 1)
    @Description("TC-54: Verify item removal and total recalculation when decreasing quantity to zero via minus button")
    public void verifyItemRemovalAndTotalRecalculationWhenDecreasingQuantityToZero() {
        
        MenuPage menuPage = new MenuPage(driver);

        // Step 1: Click on any coffee cup on the Menu page
        menuPage.clickCoffeeCup(COFFEE_NAME);
        
        BigDecimal actualInitialTotal = menuPage.getTotalButton().getTotalPrice();
        softAssert.assertEquals(actualInitialTotal, EXPECTED_INITIAL_TOTAL, 
                "The cart total is not updated to $18.00 on the Menu page");

        // Step 2: Navigate to the Cart page
        CartPage cartPage = menuPage.goToCartPage();
        
        softAssert.assertTrue(cartPage.cartListIsDisplayed(), 
                "The cart list is not displayed after adding an item to the cart");
        
        int actualQuantity = cartPage.getCartItemList().getItemByName(COFFEE_NAME).getQuantity();
        softAssert.assertEquals(actualQuantity, 1, 
                "The selected item is not displayed in the list with quantity 1");
        
        softAssert.assertAll(); 
        softAssert = new SoftAssert();

        // Step 3: Click the "-" (minus) button for the added item
        cartPage.clickMinusButtonByName(COFFEE_NAME);
        
        softAssert.assertEquals(cartPage.getNoItemText(), EXPECTED_EMPTY_CART_TEXT, 
                "The empty cart text is not displayed after clicking the minus button");
        softAssert.assertFalse(cartPage.cartListIsDisplayed(), 
                "The cart list is still displayed after decreasing quantity to zero");

        MenuPage returnedMenuPage = cartPage.goToMenuPage();
        
        BigDecimal actualFinalTotal = returnedMenuPage.getTotalButton().getTotalPrice();
        softAssert.assertEquals(actualFinalTotal, EXPECTED_FINAL_TOTAL, 
                "The Total amount is not updated to $0.00 after decreasing quantity to zero");

        softAssert.assertAll();
    }
}