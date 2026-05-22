package org.coffeecart5340.ui.pages;

import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.components.ListItemComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class MenuPage extends BasePage {

    private final By menuItemsLocator = By.xpath("//div[@class='cup']");
    private final By totalButtonLocator = By.cssSelector("button[data-test='checkout']");
    private final By cartListLocator = By.cssSelector("li.list-item");
    @FindBy(css = "ul.cart-preview li.list-item")
    private List<WebElement> cartPreviewElements;

    public MenuPage(WebDriver driver) {
        super(driver);
    }

    public List<CartPreviewComponent> getCartPreviews() {
        return cartPreviewElements.stream().map(element -> new CartPreviewComponent(element)).toList();
    }

    public void addFirstItemToCart() {
        List<WebElement> items = driver.findElements(menuItemsLocator);
        if (!items.isEmpty()) {
            WebElement firstItem = items.getFirst();
            waitAndClickElement(firstItem);
        }
    }

    public List<ListItemComponent> getCardItems() {
        WebElement totalButton = driver.findElement(totalButtonLocator);
        actions.moveToElement(totalButton).perform();

        wait.until(d -> !driver.findElements(cartListLocator).isEmpty());

        List<ListItemComponent> items = new ArrayList<>();
        List<WebElement> elements = driver.findElements(cartListLocator);
        for (WebElement element : elements) {
            items.add(new ListItemComponent(element));
        }
        return items;
    }
}