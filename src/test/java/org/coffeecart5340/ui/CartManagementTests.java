package org.coffeecart5340.ui;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.coffeecart5340.ui.components.CartItemComponent;
import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.components.CupCardComponent;
import org.coffeecart5340.ui.components.TotalButtonMenuComponent;
import org.coffeecart5340.ui.modals.AddToCartModal;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

@Feature("Cart Management")
public class CartManagementTests extends BaseUiTestRunner {

    @DataProvider(name = "coffeeData")
    public Object[][] coffeeData() {
        return new Object[][] {
                { "Espresso", 10.0f, "$10.00 x 1" },
                { "Mocha", 8.0f, "$8.00 x 1" }
        };
    }

    @Test(priority = 1, dataProvider = "coffeeData")
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC01 & TC06: Verify adding coffee updates cart counter, hover preview, cart contents, and total price")
    public void verifyAddCoffeeToCartUpdatesContentsAndTotal(String coffeeName, float expectedPrice, String expectedUnitDesc) {
        MenuPage menuPage = new MenuPage(driver);

        Allure.step("Step 1: Verify cart counter shows 'cart (0)' before adding item", () ->
                softAssert.assertEquals(menuPage.getHeader().getCartText(), "cart (0)", "Cart should be empty initially")
        );

        Allure.step("Step 2: Click on " + coffeeName + " coffee cup", () ->
                menuPage.clickCoffeeCup(coffeeName)
        );

        Allure.step("Step 3: Verify cart counter incremented to 'cart (1)'", () ->
                softAssert.assertTrue(menuPage.getHeader().getCartText().contains("cart (1)"), "Cart counter should show 1 item")
        );

        Allure.step("Step 4: Verify cart preview via hover", () -> {
            TotalButtonMenuComponent totalButtonMenu = menuPage.getTotalButtonMenuComponent();
            totalButtonMenu.hoverOverTotalButton();

            softAssert.assertTrue(totalButtonMenu.isCartPreviewVisible(), "Cart preview should be visible");
            CartPreviewComponent previewItem = menuPage.getCartPreviewItemByName(coffeeName);
            softAssert.assertEquals(previewItem.getItemName(), coffeeName, "Cart preview should contain " + coffeeName);
        });

        Allure.step("Step 5: Navigate to Cart page and verify contents", () -> {
            CartPage cartPage = menuPage.goToCartPage();
            CartItemComponent cartItem = cartPage.getCartItemList().getItemByName(coffeeName);

            softAssert.assertEquals(cartItem.getItemName(), coffeeName, "Item name should match");
            softAssert.assertEquals(cartItem.getUnitDescText(), expectedUnitDesc, "Unit description should match");
            softAssert.assertEquals(cartItem.getOneItemPrice(), expectedPrice, "Item price should match");
            softAssert.assertEquals(cartItem.getQuantity(), 1, "Quantity should be 1");
            softAssert.assertEquals(cartItem.getTotalPrice(), expectedPrice, "Item total price should match");

            softAssert.assertTrue(cartItem.getPlusButton().isDisplayed(), "(+) button should be visible");
            softAssert.assertTrue(cartItem.getMinusButton().isDisplayed(), "(-) button should be visible");
            softAssert.assertTrue(cartItem.getDeleteButton().isDisplayed(), "(x) delete button should be visible");

            softAssert.assertTrue(cartPage.getTotalButton().getCheckoutButton().isEnabled(), "Checkout button should be enabled");
        });

        softAssert.assertAll();
    }

    @Test(priority = 2)
    @Description("TC-12: Verify that adding the same coffee multiple times from the Menu updates quantity in Cart")
    public void verifyUpdatesQuantityInCartFromMenuPage() {
        String coffeeName = "Espresso";
        MenuPage menuPage = new MenuPage(driver);

        menuPage.clickCoffeeCup(coffeeName);
        softAssert.assertTrue(menuPage.getHeader().getCartText().contains("1"), "Cart should display 1");

        menuPage.clickCoffeeCup(coffeeName);
        softAssert.assertTrue(menuPage.getHeader().getCartText().contains("2"), "Cart should display 2");

        CartPage cartPage = menuPage.goToCartPage();
        CartItemComponent espressoItem = cartPage.getCartItemList().getItemByName(coffeeName);

        softAssert.assertEquals(espressoItem.getQuantity(), 2, "Quantity should be 2");

        float expectedTotal = espressoItem.getOneItemPrice() * 2;
        softAssert.assertEquals(espressoItem.getTotalPrice(), expectedTotal, 0.01f, "Item total price is incorrect");
        softAssert.assertEquals(cartPage.getTotalButton().getTotalPrice(), expectedTotal, 0.01f, "Checkout total is incorrect");

        cartPage.clickDeleteButton(coffeeName);
        softAssert.assertAll();
    }

    @Test(priority = 3)
    @Description("TC-02: Verify cart item quantity updates using '+' and '-' buttons on Cart page")
    public void verifyCartItemQuantityButtonsOnCartPage() {
        String coffeeName = "Espresso";
        MenuPage menuPage = new MenuPage(driver);
        CartPage cartPage = menuPage.clickCoffeeCup(coffeeName).goToCartPage();

        Allure.step("Step 1: Click '+' twice — quantity should become 3", () -> {
            cartPage.clickPlusButtonMultiply(2, coffeeName);
            CartItemComponent cartItem = cartPage.getCartItemList().getItemByName(coffeeName);
            softAssert.assertEquals(cartItem.getQuantity(), 3, "Quantity should be 3");
            softAssert.assertEquals(cartItem.getTotalPrice(), 30.0f, "Item subtotal should be $30.00");
            softAssert.assertEquals(cartPage.getTotalButton().getTotalPrice(), new BigDecimal("30.00"), "Cart total should be $30.00");
        });

        Allure.step("Step 2: Click '-' once — quantity should become 2", () -> {
            cartPage.clickMinusButtonByName(coffeeName);
            CartItemComponent cartItem = cartPage.getCartItemList().getItemByName(coffeeName);
            softAssert.assertEquals(cartItem.getQuantity(), 2, "Quantity should be 2");
            softAssert.assertEquals(cartItem.getTotalPrice(), 20.0f, "Item subtotal should be $20.00");
            softAssert.assertEquals(cartPage.getTotalButton().getTotalPrice(), new BigDecimal("20.00"), "Cart total should be $20.00");
        });

        softAssert.assertAll();
    }

    @Test(priority = 4)
    @Description("TC-54: Verify item removal and total recalculation when decreasing quantity to zero via minus button")
    public void verifyItemRemovalWhenDecreasingQuantityToZero() {
        String coffeeName = "Flat White";
        MenuPage menuPage = new MenuPage(driver);
        menuPage.clickCoffeeCup(coffeeName);

        CartPage cartPage = menuPage.goToCartPage();
        cartPage.clickMinusButtonByName(coffeeName);

        softAssert.assertEquals(cartPage.getNoItemText(), "No coffee, go add some.", "Empty cart text not displayed");
        softAssert.assertFalse(cartPage.cartListIsDisplayed(), "Cart list should not be displayed");

        MenuPage returnedMenuPage = cartPage.goToMenuPage();
        softAssert.assertEquals(returnedMenuPage.getTotalButton().getTotalPrice(), 0.0, "Total amount should be $0.00");

        softAssert.assertAll();
    }

    @Test(priority = 5)
    public void EmptyCartPageCheckTest() {
        MenuPage menuPage = new MenuPage(driver);
        menuPage.clickCoffeeCup("Espresso");
        menuPage.clickCoffeeCup("Cappuccino");

        List<CartPreviewComponent> items = menuPage.getCartPreviews();
        int countItems = 0;

        for (CartPreviewComponent item : items) {
            countItems += item.getItemAmount();
        }

        softAssert.assertEquals(countItems, 2, "The count of items must be 2");

        CartPage cartPage = menuPage.goToCartPage();
        var itemList = cartPage.getCartItemList();
        var names = itemList.getAllItemNames();

        softAssert.assertEquals(names.get(1), "Espresso", "the second element must be Espresso");
        softAssert.assertEquals(names.get(0), "Cappuccino", "the first element must be Cappuccino");

        var itemsAll = itemList.getAllItems();
        for (CartItemComponent item : itemsAll) {
            item.clickMinusButton();
        }

        softAssert.assertEquals(cartPage.getNoItemText(), "No coffee, go add some.", "the message must be : No coffee, go add some.");
        softAssert.assertAll();
    }

    @Test(priority = 6)
    public void verifyAddingCoffeeViaRightClickOnTheCard() {
        MenuPage menuPage = new MenuPage(driver);
        var price = menuPage.getCupCardByName("Espresso").getCupPrice();

        menuPage.contextClickCoffeeCup("Espresso");
        AddToCartModal addToCartModal = new AddToCartModal(driver);
        addToCartModal.hoverOverYesButton();
        addToCartModal.clickYesButton();

        softAssert.assertEquals(menuPage.getHeader().getCartCount(), 1, "Cart badge count did not increase");

        CartPage cartPage = menuPage.goToCartPage();
        softAssert.assertTrue(cartPage.getCartItemList().getAllItemNames().contains("Espresso"), "Espresso is missing");
        softAssert.assertEquals(cartPage.getCartItemList().getCalculatedTotalPrice(), (double) price, "Total price is incorrect");

        cartPage.getCartItemList().clearCart();
        softAssert.assertAll();
    }

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
