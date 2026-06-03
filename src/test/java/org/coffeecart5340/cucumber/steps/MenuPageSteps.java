package org.coffeecart5340.cucumber.steps;

import io.cucumber.java.en.*;
import lombok.Getter;
import org.coffeecart5340.cucumber.hooks.CucumberHook;
import org.coffeecart5340.ui.pages.MenuPage;
import org.testng.Assert;

import java.util.List;

public class MenuPageSteps {

    @Getter
    private final CucumberHook cucumberHook;

    public MenuPageSteps(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    @Given("I am on the menu page")
    public void i_am_on_the_menu_page() {
        cucumberHook.getDriver().get("/");
    }

    @Given("I have an empty cart")
    public void i_have_an_empty_cart() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewEmpty(),
                "cart preview should be empty before adding any items");
    }

    @When("I right-click on the {string} coffee cup")
    public void i_right_click_on_the_coffee_cup(String string) {
        new MenuPage(cucumberHook.getDriver()).getCupCardByName(string)
                .rightClickCup();
    }

    @When("I click on the coffee cups:")
    public void i_click_on_the_coffee_cups(List<String> coffeeNames) {
        MenuPage menuPage = new MenuPage(cucumberHook.getDriver());
        for(String coffeeName : coffeeNames){
            menuPage.clickCoffeeCup(coffeeName);
        }
    }

    @Then("I verify that the lucky modal day appears")
    public void i_verify_that_the_lucky_modal_day_appears() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getDiscountModal().isDiscountMenuVisible(),
                "Lucky day modal should be displayed after right-clicking on a coffee cup");
    }

    @When("I click on the Yes button")
    public void i_click_on_the_yes_button() {
        new MenuPage(cucumberHook.getDriver()).getDiscountModal().clickYesButton();
    }
    @Then("I verify that the lucky modal discount disapears")
    public void i_verify_that_the_lucky_modal_discount_disapears() {
        Assert.assertFalse(new MenuPage(cucumberHook.getDriver()).getDiscountModal().isDiscountMenuVisible(),
                "Lucky day modal should not be displayed after clicking the Yes button");
    }
    @When("I hover over the total checkout button")
    public void i_hover_over_the_total_checkout_button() {
        new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().hoverOverTotalButton();
    }
    @Then("I verify that checkout menu appears with added items")
    public void i_verify_that_checkout_menu_appears_with_added_items() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewVisible(),
                "Cart preview should be visible after hovering over the total checkout button");

    }

    @When("I navigate to the cart page")
    public void i_navigate_to_the_page() {
        new MenuPage(cucumberHook.getDriver()).goToCartPage();
    }


}
