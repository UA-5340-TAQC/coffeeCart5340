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

import java.util.List;

@Epic("Coffee Cart Application")
@Feature("Promo Discount")
public class PromoDiscountTest extends BaseUiTestRunner {

    private MenuPage menuPage;

    @BeforeMethod
    public void setUpTest() {
        menuPage = new MenuPage(driver);
    }

    @Test
    @Story("Promo appears after adding 3 items")
    @Description("Verify that promo message appears only after adding the third coffee item and all items are displayed in checkout preview")
    public void testPromoAppearsOnlyAfterThirdItem() {
        String[] testCoffees = {"Espresso", "Cappuccino", "Cafe Latte"};

        for (int i = 0; i < testCoffees.length; i++) {
            menuPage.getCupCardByName(testCoffees[i]).clickCup();

            if (i < 2) {
                Assert.assertFalse(menuPage.getDiscountModal().isDiscountMenuVisible(),
                        "Promo message should not be displayed after " + (i + 1) + " item(s)");
            } else {
                Assert.assertTrue(menuPage.getDiscountModal().isDiscountMenuVisible(),
                        "Promo message should be displayed after third item");
            }
        }

        menuPage.getTotalButton().hoverOverButton();

        Assert.assertEquals(menuPage.getCartPreviews().size(), 3,
                "Checkout preview should show 3 items");

        for (String expectedItem : testCoffees) {
            boolean itemFound = menuPage.getCartPreviews().stream()
                    .anyMatch(item -> item.getItemName().equals(expectedItem));
            Assert.assertTrue(itemFound, "Checkout preview should contain " + expectedItem);
        }
    }
}