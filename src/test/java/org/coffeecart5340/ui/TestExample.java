package org.coffeecart5340.ui;

import org.coffeecart5340.ui.components.ListItemComponent;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class TestExample extends BaseUiTestRunner {

    @Test
    public void headerTest() {
        MenuPage menuPage = new MenuPage(driver);
        menuPage.getHeader()
                .clickCardButton()
                .getHeader()
                .clickGitHubButton()
                .getHeader()
                .clickMenuButton();

    }

    @Test
    public void testListItemComponent() {

        MenuPage menuPage = new MenuPage(driver);

        menuPage.addFirstItemToCart();
        List<ListItemComponent> items = menuPage.getCardItems();
        assertFalse(items.isEmpty(), "Expected to find items in the cart.");

        ListItemComponent firstItem = items.getFirst();

        firstItem.increment();
        firstItem.waitForQuantity(2);
        assertEquals(firstItem.getQuantity(),2, "Expected quantity to be 2 after increment");    }

}
