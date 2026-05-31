package org.coffeecart5340.ui;

import io.qameta.allure.Description;
import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertTrue;

public class CartPreviewAndPromoTest extends BaseUiTestRunner {

    private static final String FIRST_COFFEE_NAME = "Espresso";
    private static final String SECOND_COFFEE_NAME = "Americano";

    @Test(priority = 1)
    @Description("TC-057: Verify promotional offers trigger after 3 initial cups and 2 subsequent cups, and validate cart preview updates")
    public void verifyCartPreviewUpdatesAndPromotionalOffers() {

        MenuPage menuPage = new MenuPage(driver);

        // --- Step 1: Add 3 cups and verify promo triggers (Precondition Check) ---
        menuPage.clickCupMultiply(FIRST_COFFEE_NAME, 3);
        
        assertTrue(menuPage.getDiscountModal().isDiscountMenuVisible(), 
                "Precondition failed: The first promotional pop-up did not appear after adding 3 cups.");

        // --- Step 2: Verify presence in cart via preview ---
        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();
        List<String> previewItemNames = getPreviewItemNames(menuPage);
        
        softAssert.assertTrue(previewItemNames.contains(FIRST_COFFEE_NAME), 
                FIRST_COFFEE_NAME + " is missing from the cart preview.");

        // --- Step 3: Accept first promo (Dynamic text extraction) ---
        String firstPromoText = menuPage.getDiscountModal().getDiscountText();
        String firstPromoCup = extractCoffeeNameFromPromo(firstPromoText);
        
        menuPage.getDiscountModal().clickYesButton();

        // --- Step 4: Add 2 more cups and verify second promo triggers ---
        menuPage.clickCupMultiply(SECOND_COFFEE_NAME, 2);
        
        softAssert.assertTrue(menuPage.getDiscountModal().isDiscountMenuVisible(), 
                "The second promotional pop-up did not appear after adding 2 additional cups (reaching 6 items total).");

        // --- Step 5: Verify presence of second batch in cart via preview ---
        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();
        previewItemNames = getPreviewItemNames(menuPage);
        
        softAssert.assertTrue(previewItemNames.contains(SECOND_COFFEE_NAME), 
                SECOND_COFFEE_NAME + " is missing from the cart preview.");
        
        boolean foundFirstPromo = previewItemNames.stream().anyMatch(name -> name.contains(firstPromoCup));
        softAssert.assertTrue(foundFirstPromo, 
                "First promo cup (" + firstPromoCup + ") is missing from the cart preview.");

        // --- Step 6: Accept second promo ---
        String secondPromoText = menuPage.getDiscountModal().getDiscountText();
        String secondPromoCup = extractCoffeeNameFromPromo(secondPromoText);
        
        menuPage.getDiscountModal().clickYesButton();

        // --- Step 7: Verify second promo cup in preview ---
        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();
        List<String> finalPreviewItemNames = getPreviewItemNames(menuPage);
        
        boolean foundSecondPromo = finalPreviewItemNames.stream().anyMatch(name -> name.contains(secondPromoCup));
        softAssert.assertTrue(foundSecondPromo, 
                "Second promo cup (" + secondPromoCup + ") is missing from the cart preview.");

        softAssert.assertAll();
    }

    /**
     * Helper method to dynamically extract the coffee name from texts like:
     * "It's your lucky day! Get an extra cup of Mocha for $4?"
     */
    private String extractCoffeeNameFromPromo(String promoText) {
        Pattern pattern = Pattern.compile("extra (.*?) for");
        Matcher matcher = pattern.matcher(promoText);
        
        if (matcher.find()) {
            String extractedName = matcher.group(1).trim();
            return extractedName.replace("cup of ", "").trim();
        }
        throw new RuntimeException("Could not extract coffee name from promotional text: " + promoText);
    }
    
    /**
     * Helper method to safely get a list of string names from the Cart Preview Components
     */
    private List<String> getPreviewItemNames(MenuPage menuPage) {
        return menuPage.getTotalButtonMenuComponent().getCartPreviewItems()
                .stream()
                .map(CartPreviewComponent::getItemName)
                .toList();
    }
}
