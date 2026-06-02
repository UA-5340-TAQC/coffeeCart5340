package org.coffeecart5340.ui;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.enumData.CoffeeType;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

@Epic("Coffee Cart Application")
@Feature("Promo Discount")
public class PromoDiscountTests extends BaseUiTestRunner {

    private static final String FIRST_COFFEE_NAME = "Mocha";
    private static final String SECOND_COFFEE_NAME = "Espresso";
    private MenuPage menuPage;
    private static final List<String> Three_coffees = List.of("Espresso", "Espresso Macchiato", "Cappuccino");

    private static final String coffee = CoffeeType.ESPRESSO.getCoffee();



    @Test(priority = 1)
    @Description("TC-056: Verify promotional offer triggers again if cart quantity falls below threshold and reaches multiple of 3 again")
    public void verifyPromotionalOfferTriggersAgainAfterCartQuantityDrop() {
        menuPage = new MenuPage(driver);

        // --- PRECONDITIONS (Using Hard Asserts) ---

        // Step 1: Click three times on any coffee cups
        menuPage.clickCupMultiply(FIRST_COFFEE_NAME, 3);
        
        assertTrue(menuPage.getDiscountModal().isDiscountMenuVisible(), 
                "Precondition failed: Promotional pop-up did not appear after adding 3 cups.");

        // Step 2: Click "Nah I`ll skip." and navigate to Cart
        menuPage.getDiscountModal().clickNoButton();
        
        assertFalse(menuPage.getDiscountModal().isDiscountMenuVisible(), 
                "Precondition failed: Discount modal did not close after clicking 'No'.");

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

    @Test
    public void PromoCheckTest(){
        menuPage = new MenuPage(driver);
        for (int i = 0; i < 3; i++)
        {
            menuPage.clickCoffeeCup("Espresso");
        }
        boolean isDiscountVisible=menuPage.getDiscountModal().isDiscountMenuVisible();
        Assert.assertTrue(isDiscountVisible,"the discount banner must be visible");
        menuPage.getDiscountModal().clickYesButton();

        List<CartPreviewComponent> items = menuPage.getCartPreviews();
        int countItems = 0;

        for (CartPreviewComponent item : items) {
            countItems += item.getItemAmount();
        }

        Assert.assertEquals(countItems, 4, "The count of items must be 4");

        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();
        for (int i = 0; i < 3; i++){
            items.get(1).clickMinus();
        }
        Assert.assertTrue(items.isEmpty(), "The items count must be 0");
    }


    @Test
    public void PromoDisagreeTest() {
        menuPage = new MenuPage(driver);

        menuPage.clickCoffeeCup("Espresso");
        menuPage.clickCoffeeCup("Cappuccino");
        menuPage.clickCoffeeCup("Espresso Macchiato");

        List<CartPreviewComponent> items = menuPage.getCartPreviews();
        int countItems = 0;

        for (CartPreviewComponent item : items) {
            countItems += item.getItemAmount();
        }

        Assert.assertEquals(countItems, 3, "The count of items must be 3");

        boolean isDiscountVisible=menuPage.getDiscountModal().isDiscountMenuVisible();
        Assert.assertTrue(isDiscountVisible,"the discount banner must be visible");
        menuPage.getDiscountModal().clickNoButton();

        boolean isDiscountNotVisible=menuPage.getDiscountModal().isDiscountMenuVisible();
        Assert.assertFalse(isDiscountNotVisible,"the discount banner must not be visible");

        items = menuPage.getCartPreviews();
        countItems = 0;

        for (CartPreviewComponent item : items) {
            countItems += item.getItemAmount();
        }

        Assert.assertEquals(countItems, 3, "The count of items must be 3");
    }

    @Test
    @Description("TC-31 - Verify that promo drink is added to cart after clicking agree button")
    public void verifyPromoDrink() {
        MenuPage menuPage = new MenuPage(driver);

        for (String coffeeName : Three_coffees) {
            menuPage.clickCoffeeCup(coffeeName);
        }

        String cartTextAfterThreeAdds = menuPage.getHeader().getCartText();
        softAssert.assertTrue(
                cartTextAfterThreeAdds.contains("3"),
                "Cart counter should display 3 after adding three coffees, but was: " + cartTextAfterThreeAdds
        );

        softAssert.assertTrue(
                menuPage.getDiscountModal().isDiscountMenuVisible(),
                "Promotional pop-up should appear after adding the third coffee item"
        );

        menuPage.getDiscountModal().clickYesButton();

        String cartTextAfterPromo = menuPage.getHeader().getCartText();
        softAssert.assertTrue(
                cartTextAfterPromo.contains("4"),
                "Cart counter should display 4 after accepting promo Mocha, but was: " + cartTextAfterPromo
        );

        softAssert.assertAll();
    }

    private CartPage cartPage;

    @Test(priority = 1)
    public void verifyImpossibilityOfAddingExtraDiscountedMochaCoffeeAfterAdding3CupsOfCoffeeToTheCart() {
        menuPage = new MenuPage(driver);

        var espressoPrice = menuPage.getCupCardByName("Espresso").getCupPrice();
        var mochaPrice = menuPage.getCupCardByName("Mocha").getCupPrice();
        var discount = 0.5;

        menuPage.clickCupMultiply("Espresso", 3);

        menuPage.getDiscountModal().clickYesButton();

        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();

        var previewItems = menuPage.getTotalButtonMenuComponent().getCartPreviewItems();

        var discountedMochaPreviewItem = previewItems.stream()
                .filter(item -> item.getItemName().contains("Mocha"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Discounted Mocha not found in cart preview"));

        softAssert.assertFalse(
                discountedMochaPreviewItem.isPlusButtonAvailable(),
                "The '+' button should not be displayed/enabled for the discounted Mocha in the cart preview"
        );

        softAssert.assertEquals(previewItems.size(), 2, "There should be exactly 2 distinct item types in the cart preview");

        var espressoPreviewItem = previewItems.stream()
                .filter(item -> item.getItemName().contains("Espresso"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Espresso not found in cart preview"));

        softAssert.assertEquals(espressoPreviewItem.getItemAmount(), 3, "Espresso quantity should be 3 in preview");
        softAssert.assertEquals(discountedMochaPreviewItem.getItemAmount(), 1, "Mocha quantity should be 1 in preview");

        menuPage.goToCartPage();

        CartPage cartPage = new CartPage(driver);

        var cartItems = cartPage.getCartItemList().getAllItems();
        softAssert.assertEquals(cartItems.size(), 2, "There should be exactly 2 distinct item types in the main cart list");

        var discountedMochaCartItem = cartPage.getCartItemList().getItemByName("(Discounted) Mocha");
        var espressoCartItem = cartPage.getCartItemList().getItemByName("Espresso");

        softAssert.assertNotNull(discountedMochaCartItem, "Discounted Mocha should be present in the cart list");
        softAssert.assertNotNull(espressoCartItem, "Espresso should be present in the cart list");

        double expectedTotal = (espressoPrice * 3) + (mochaPrice * discount);
        softAssert.assertEquals(
                cartPage.getCartItemList().getCalculatedTotalPrice(),
                expectedTotal,
                "Total price calculation is incorrect"
        );

        softAssert.assertFalse(
                discountedMochaCartItem.isPlusButtonAvailable(),
                "The '+' button should not be displayed/enabled for the discounted Mocha on the Cart Page"
        );

        cartPage.getCartItemList().clearCart();

        softAssert.assertAll();
    }

    @Story("Discount appears after 3 cups")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a discount section appears automatically after adding three cups of the same coffee product to the cart.")
    @Issue("https://github.com/UA-5340-TAQC/coffeeCart5340/issues/48")
    @Tag("Regression")
    @Tag("Smoke")
    @Test(priority = 1)
    public void verifyDiscountAppearsAfterThreeCupsOnTheCartPage() {
        menuPage = new MenuPage(driver);
        CartPage cartPage = menuPage.clickCoffeeCup(coffee)
                .goToCartPage()
                .clickPlusButtonMultiply(2, coffee);

        softAssert.assertEquals(cartPage.getCartItemList().getAllItems().size(), 3,
                "The cart list does not contain 3 items after adding 2 cups of coffee to the cart");

        softAssert.assertTrue(cartPage.getCartItemList().getAllItems().stream().allMatch(
                        item -> item.getItemName().equals(coffee)),
                "Not all items in the cart are the expected coffee product");

        softAssert.assertEquals(cartPage.getCartItemList().getItemByName(coffee).getQuantity(), 3,
                "The quantity of the coffee item in the cart is not 3 after adding 2 cups of coffee to the cart");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(cartPage.getDiscount().isDiscountMenuVisible(),
                "The discount component is not displayed after adding 3 cups of coffee to the cart");
        softAssert.assertAll();
    }

}
