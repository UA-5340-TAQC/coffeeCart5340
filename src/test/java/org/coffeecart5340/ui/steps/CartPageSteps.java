package org.coffeecart5340.ui.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en_scouse.An;
import org.coffeecart5340.ui.components.CartItemListComponent;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.testng.Assert;

public class CartPageSteps {

    private MenuPage menuPage;
    private CartPage cartPage;

    @When("I open the cart page")
    public void OpenCartPage(){
        menuPage.goToCartPage();
    }

    @Then("the cart should contain {string}")
    public void DrinkInCartPage(String coffeeName){
        CartItemListComponent itemList = cartPage.getCartItemList();
        var names = itemList.getAllItemNames();

        Assert.assertTrue(names.contains(coffeeName), "The element must be " + coffeeName);
    }

    @When("I remove {string} from the cart")
    public void RemoveItemFromCart(String coffeeName){
        cartPage.getCartItemList().getItemByName(coffeeName).clickMinusButton();
    }

    @And("the cart should be empty")
    public void EmptyCartCheck(){
        Assert.assertEquals(cartPage.getNoItemText(), "No coffee, go add some." ,"the cart should be empty");
    }




}
