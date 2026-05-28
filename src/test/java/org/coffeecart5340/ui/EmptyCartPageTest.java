package org.coffeecart5340.ui;

import org.coffeecart5340.ui.components.CartItemComponent;
import org.coffeecart5340.ui.components.CartItemListComponent;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.openqa.selenium.remote.NewSessionPayload;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class EmptyCartPageTest extends BaseUiTestRunner {

    private MenuPage menuPage;

    @Test
    public void EmptyCartPageCheckTest(){
        menuPage = new MenuPage(driver);
        menuPage.clickCoffeeCup("Espresso");
        menuPage.clickCoffeeCup("Cappuccino");

        List<CartPreviewComponent> items = menuPage.getCartPreviews();
        int countItems = 0;

        for (CartPreviewComponent item : items) {
            countItems += item.getItemAmount();
        }

        Assert.assertEquals(countItems, 2, "The count of items must be 2");

        CartPage cartPage = menuPage.goToCartPage();
        CartItemListComponent itemList = cartPage.getCartItemList();
        var names = itemList.getAllItemNames();

        Assert.assertTrue(names.get(1).equals("Espresso"),"the second element must be Espresso");
        Assert.assertTrue(names.get(0).equals("Cappuccino"),"the first element must be Cappuccino");

        var itemsAll = itemList.getAllItems();
        for (CartItemComponent item : itemsAll)
        {
            item.clickMinusButton();
        }
        Assert.assertTrue(cartPage.getNoItemText().equals("No coffee, go add some."), "the message must be : No coffee, go add some.");

    }
}
