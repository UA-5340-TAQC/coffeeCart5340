package org.coffeecart5340.ui;

import org.coffeecart5340.ui.components.CupCardComponent;
import org.coffeecart5340.ui.components.ListItemMenuComponent;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

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

        String productName = "Espresso";
        menuPage.getCupCardByName(productName).clickCup();
        List<WebElement> rawItems = driver.findElements(By.cssSelector("ul.cart-preview li.list-item"));
        List<ListItemMenuComponent> items = rawItems.stream().map(element -> new ListItemMenuComponent(driver, element)).toList();
        assertFalse(items.isEmpty(), "Expected to find items in the cart.");

        ListItemMenuComponent item = null;
        for (ListItemMenuComponent it : items) {
            if (it.getItemName().equals(productName)) {
                item = it;
                break;
            }
        }
        assertNotNull(item, "Added product not found in cart preview");

        int initialQty = item.getQuantity();
        String expectedInfo = String.format("Item: %s, Quantity: %d", productName, initialQty);
        assertEquals(item.getItemInfo(), expectedInfo,
                "Item info string should match expected format");

        assertTrue(initialQty >= 0, "Initial quantity should be >= 0");

        item.increment();
        item.waitForQuantity(initialQty + 1);
        assertEquals(item.getQuantity(), initialQty + 1, "Quantity should increase by 1 after increment");

        String updatedInfo = String.format("Item: %s, Quantity: %d", productName, initialQty + 1);
        assertEquals(item.getItemInfo(), updatedInfo);

        item.setQuantity(3);
        item.waitForQuantity(3);
        assertEquals(item.getQuantity(), 3);

        assertEquals(item.getItemInfo(),
                String.format("Item: %s, Quantity: %d", productName, 3));

        item.removeItems(2);
        item.waitForQuantity(1);
        assertEquals(item.getQuantity(), 1);

        item.setQuantity(initialQty);
        item.waitForQuantity(initialQty);


        assertEquals(item.getItemInfo(),
                String.format("Item: %s, Quantity: %d", productName, initialQty));
    }
}

