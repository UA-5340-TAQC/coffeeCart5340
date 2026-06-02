package org.coffeecart5340.ui.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.utils.DriverManager;
import org.testng.Assert;

import java.util.List;

public class MenuPageSteps {
    private MenuPage menuPage;

    public MenuPageSteps() {
        menuPage = new MenuPage(DriverManager.getDriver());
    }

    @And("the Cart preview is empty")
    public void theCartPreviewIsEmptyBefore() {
        Assert.assertTrue(menuPage.getTotalButtonMenuComponent().isCartPreviewEmpty(),
                "cart preview should be empty before adding any items");
    }

    @When("I add {int} {string} to the cart")
    public void AddCuptoTheCart(String coffeeName,int quantity){
        menuPage.clickCupMultiply(coffeeName, quantity);
    }

    @Then("the cart counter should display {int}")
    public void CountDrink(int expectedCount){
        Assert.assertEquals(menuPage.getTotalButtonMenuComponent().getCartPreviewItemCount(), expectedCount, "Cart preview item count does not match expected quantity.");
    }

    @And("the promotional banner should be displayed")
    public void PromoBannerEnabledCheck(){
        Assert.assertTrue(menuPage.getDiscountModal().isDiscountMenuVisible(),"the promotional banner should be displayed");
    }

    @Then("the promotional banner should not be displayed")
    public void PromoBannerNotEnabled(){
        Assert.assertFalse(menuPage.getDiscountModal().isDiscountMenuVisible(),"the promotional banner should be displayed");
    }

    @When("I click the {string} button")
    public void ClickPromoButton(String choice){
        if (choice=="Yes, of course!"){
            menuPage.getDiscountModal().clickYesButton();
        }
        else menuPage.getDiscountModal().clickNoButton();
    }

    @And("the beverage list should be empty")
    public void DrinkListEmpty(){
        Assert.assertEquals(menuPage.getTotalButtonMenuComponent().getCartPreviewItemCount(),0, "the beverage list should be empty");
    }

    @When("I remove all {int} non-promotional items from the cart")
    public void RemoveNonPromotionalItems(int count){
        List<CartPreviewComponent> items = menuPage.getCartPreviews();
        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();
        for (int i = 0; i < count; i++){
            items.get(1).clickMinus();
        }
    }

    @Then("I hover over the Total button")
    public void hoverOverTotalButton(){
        menuPage.getTotalButton().hoverOverButton();
    }

    @When("the \"Total: $8.00\" button should be visible")
    public void TotalButtonCheck(){
        Assert.assertTrue(menuPage.getTotalButton().isTotalButtonEnabled(), "the \"Total: $8.00\" button should be visible");
    }

    @Then("the cart preview should be displayed")
    public void MenuComponentVisible(){
        Assert.assertTrue(menuPage.getTotalButtonMenuComponent().isCartPreviewVisible(),"the cart preview should be displayed");
    }

    @And("the quick cart preview should contain {string}")
    public void PreviewContainsDrink(String coffeeName) {
        Assert.assertTrue(menuPage.getTotalButtonMenuComponent().getCartPreviewItems().contains(coffeeName),"the quick cart preview should contain");
    }

    @When("I move the cursor away from the Total button and the preview area")
    public void MoveCursorAwayFromTotalButton(){
        menuPage.clickCoffeeCup("Espresso");
    }

    @Then("the cart preview should not be displayed")
    public void MenuComponentNotVisible(){
        Assert.assertFalse(menuPage.getTotalButtonMenuComponent().isCartPreviewVisible(),"the cart preview should not be displayed");

    }


}
