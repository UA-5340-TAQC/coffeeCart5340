package org.coffeecart5340.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

@Epic("Coffee Cart Application")
@Feature("Checkout Process")
public class CheckoutWithPromoTest extends BaseUiTestRunner {

    private MenuPage menuPage;
    private final String customerName = "Test User";
    private final String customerEmail = "test@example.com";
    private final float discountPrice = 4.00f;

    private final Map<String, Float> coffeeItems = Map.of(
        "Espresso", 10.00f,
        "Cappuccino", 19.00f,
        "Cafe Breve", 15.00f
    );

    @BeforeMethod
    public void setUpTest() {
        menuPage = new MenuPage(driver);
    }

    @Test
    @Story("End-to-end checkout with promo offer")
    @Description("Verify successful end-to-end checkout process after accepting promotional offer and modifying cart via hover preview")
    public void testSuccessfulCheckoutWithPromo() {
        for (String coffeeName : coffeeItems.keySet()) {
            menuPage.getCupCardByName(coffeeName).clickCup();
        }

        Assert.assertTrue(menuPage.getDiscountModal().isDiscountMenuVisible(),
                "Promo modal should appear after adding third item");

        menuPage.getDiscountModal().clickYesButton();
        
        float expectedTotal = coffeeItems.values().stream().reduce(0f, Float::sum) + discountPrice;
        Assert.assertEquals(menuPage.getTotalButton().getTotalPrice(), expectedTotal);

        menuPage.getTotalButton().hoverOverButton();

        float espressoPrice = coffeeItems.get("Espresso");
        menuPage.getCartPreviewItemByName("Espresso").clickMinus();
        Assert.assertEquals(menuPage.getTotalButton().getTotalPrice(), expectedTotal - espressoPrice);

        menuPage.getTotalButton().clickCheckoutButton();

        menuPage.getPaymentModal().enterName(customerName);
        menuPage.getPaymentModal().enterEmail(customerEmail);
        menuPage.getPaymentModal().clickPromotionCheckbox();
        menuPage.getPaymentModal().clickSubmitButton();

        String expectedMessage = "Thanks for your purchase. Please check your email for payment.";
        Assert.assertEquals(menuPage.getSnackbarText(), expectedMessage);
    }
}