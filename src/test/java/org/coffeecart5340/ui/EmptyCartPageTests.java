package org.coffeecart5340.ui;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.coffeecart5340.ui.components.CartItemComponent;
import org.coffeecart5340.ui.components.CartItemListComponent;
import org.coffeecart5340.ui.enumData.CoffeeType;
import org.coffeecart5340.ui.modals.PaymentModal;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.pages.MenuPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class EmptyCartPageTests extends BaseUiTestRunner {

    private static final String ESPRESSO = CoffeeType.ESPRESSO.getCoffee();
    private static final String CAPPUCCINO = "Cappuccino";
    private static final String ESPRESSO_MACCHIATO = "Espresso Macchiato";
    private static final String NO_ITEMS_MESSAGE = "No coffee, go add some.";
    private static final String TEST_NAME = "test";
    private static final String TEST_EMAIL = "test@gmail.com";
    private static final String PAYMENT_SUCCESS_MESSAGE = "Thanks for your purchase. Please check your email for payment.";

    private MenuPage menuPage;

    @BeforeMethod
    public void setUp() {
        menuPage = new MenuPage(driver);
    }

    @Story("Cart state validation")
    @Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    @Description("Verify that adding two items updates cart count to 2 and removing all items one by one displays the empty cart message.")
    @Test
    public void verifyCartShowsEmptyMessageAfterRemovingAllItems() {
        menuPage.clickCoffeeCup(ESPRESSO);
        menuPage.clickCoffeeCup(CAPPUCCINO);

        List<CartPreviewComponent> previews = menuPage.getCartPreviews();
        int totalCount = previews.stream().mapToInt(CartPreviewComponent::getItemAmount).sum();
        Assert.assertEquals(totalCount, 2, "The count of items must be 2");

        CartPage cartPage = menuPage.goToCartPage();
        CartItemListComponent itemList = cartPage.getCartItemList();
        List<String> names = itemList.getAllItemNames();

        Assert.assertEquals(names.get(0), CAPPUCCINO, "The first element must be Cappuccino");
        Assert.assertEquals(names.get(1), ESPRESSO, "The second element must be Espresso");

        for (CartItemComponent item : itemList.getAllItems()) {
            item.clickMinusButton();
        }

        Assert.assertEquals(cartPage.getNoItemText(), NO_ITEMS_MESSAGE,
                "The message must be: " + NO_ITEMS_MESSAGE);
    }

    @Story("Removing item from cart")
    @Severity(SeverityLevel.CRITICAL)
    @Issue("https://github.com/UA-5340-TAQC/coffeeCart5340/issues/7")
    @Tag("Regression")
    @Description("Verify that removing the only product from the cart updates the cart state to empty and displays the appropriate message.")
    @Test(priority = 1)
    public void verifyRemovingItemFromTheCartUpdatesTheTotalAndCartState() {
        CartPage cartPage = menuPage
                .clickCoffeeCup(ESPRESSO)
                .goToCartPage();

        softAssert.assertTrue(cartPage.cartListIsDisplayed(),
                "The cart list is not displayed after adding an item to the cart");
        softAssert.assertEquals(cartPage.getCartItemList().getAllItems().size(), 1,
                "The cart item is not added");
        softAssert.assertAll();

        cartPage.clickDeleteButton(ESPRESSO);

        softAssert = new SoftAssert();
        softAssert.assertEquals(cartPage.getNoItemText(), NO_ITEMS_MESSAGE,
                "The cart item is not removed");
        softAssert.assertFalse(cartPage.cartListIsDisplayed(),
                "The cart list is still displayed after removing the only item in the cart");
        softAssert.assertAll();
    }

    @Story("Checkout flow")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("Regression")
    @Description("Verify that after completing a successful purchase the cart becomes empty and the success message is displayed.")
    @Test(priority = 2)
    public void verifyCartIsEmptyAfterSuccessfulPurchase() {
        menuPage.clickCoffeeCup(ESPRESSO);
        menuPage.clickCoffeeCup(ESPRESSO_MACCHIATO);

        menuPage.getTotalButtonMenuComponent().clickTotalButton();

        PaymentModal paymentModal = new PaymentModal(driver);
        paymentModal.fillPaymentDetailsAndSubmit(TEST_NAME, TEST_EMAIL, true);

        softAssert.assertEquals(menuPage.getSnackbarText(), PAYMENT_SUCCESS_MESSAGE,
                "Success message text is incorrect or missing!");
        softAssert.assertEquals(menuPage.getHeader().getCartCount(), 0,
                "Cart count should be 0 after purchase");

        CartPage cartPage = menuPage.goToCartPage();

        softAssert.assertFalse(cartPage.cartListIsDisplayed(),
                "Cart list should not be displayed after purchase");
        softAssert.assertEquals(cartPage.getNoItemText(), NO_ITEMS_MESSAGE,
                "Empty cart message is incorrect after purchase");
        softAssert.assertAll();
    }
}