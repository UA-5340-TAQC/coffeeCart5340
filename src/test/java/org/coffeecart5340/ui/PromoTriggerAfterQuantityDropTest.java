package org.coffeecart5340.ui;

import io.qameta.allure.Description;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PromoTriggerAfterQuantityDropTest extends BaseUiTestRunner {

    private static final String FIRST_COFFEE_NAME = "Mocha";
    private static final String SECOND_COFFEE_NAME = "Espresso";

    @Test(priority = 1)
    @Description("TC-056: Verify promotional offer triggers again if cart quantity falls below threshold and reaches multiple of 3 again")
    public void verifyPromotionalOfferTriggersAgainAfterCartQuantityDrop() {
        
        MenuPage menuPage = new MenuPage(driver);

        // --- PRECONDITIONS (Using Hard Asserts) ---

        // Step 1: Click three times on any coffee cups
        menuPage.clickCupMultiply(FIRST_COFFEE_NAME, 3);
        
        assertTrue(menuPage.getDiscountModal().isDiscountMenuVisible(), 
                "Precondition failed: Promotional pop-up did not appear after adding 3 cups.");

        // Step 2: Click "Nah I`ll skip." and navigate to Cart
        menuPage.getDiscountModal().clickNoButton();
        CartPage cartPage = menuPage.goToCartPage();
        
        int initialQuantity = cartPage.getCartItemList().getItemByName(FIRST_COFFEE_NAME).getQuantity();
        assertEquals(initialQuantity, 3, 
                "Precondition failed: Cart does not have 3 items after dismissing the promo.");


        // --- ACTUAL TEST STEPS (Using inherited SoftAssert) ---

        // Step 3: Navigate to Cart page and click the "-" button once
        cartPage.clickMinusButtonByName(FIRST_COFFEE_NAME);
        
        int updatedQuantity = cartPage.getCartItemList().getItemByName(FIRST_COFFEE_NAME).getQuantity();
        softAssert.assertEquals(updatedQuantity, 2, 
                "The cart quantity did not decrease to 2 after clicking the minus button.");

        // Step 4: Navigate back to the Menu page and add 1 more coffee cup
        MenuPage returnedMenuPage = cartPage.goToMenuPage();
        returnedMenuPage.clickCoffeeCup(SECOND_COFFEE_NAME);
        
        softAssert.assertTrue(returnedMenuPage.getDiscountModal().isDiscountMenuVisible(), 
                "The Promotional pop-up did not trigger again after reaching 3 items in the cart.");

        softAssert.assertAll();
    }
}
