package org.coffeecart5340.cucumber.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.coffeecart5340.cucumber.hooks.CucumberHook;
import org.coffeecart5340.ui.enumData.HighlightedStyles;
import org.coffeecart5340.ui.modals.AddToCartModal;
import org.testng.Assert;

public class ConfirmPurchaseModalSteps {

    //    private final AddToCartModal addToCartModal;
    private final CucumberHook cucumberHook;

    public ConfirmPurchaseModalSteps(CucumberHook cucumberHook) {
//        addToCartModal = new AddToCartModal(cucumberHook.getDriver());
        this.cucumberHook = cucumberHook;
    }

    @When("I hover over the Yes button")
    public void i_hover_over_the_yes_button() {
        new AddToCartModal(cucumberHook.getDriver()).hoverOverYesButton();
    }

    @When("I click the Yes button")
    public void i_click_the_yes_button() {
        new AddToCartModal(cucumberHook.getDriver()).clickYesButton();
    }

    @When("I click the No button")
    public void i_click_the_no_button() {
        new AddToCartModal(cucumberHook.getDriver()).clickNoButton();
    }

    @Then("I verify that the confirmation modal appears with Yes and No buttons")
    public void i_verify_that_the_confirmation_modal_appears_with_yes_and_no_buttons() {
        AddToCartModal addToCartModal = new AddToCartModal(cucumberHook.getDriver());
        Assert.assertTrue(addToCartModal.isButtonYesDisplayed(), "Yes button is not displayed in the confirmation modal");
        Assert.assertTrue(addToCartModal.isButtonNoDisplayed(), "No button is not displayed in the confirmation modal");
    }

    @Then("I verify that the Yes button is highlighted")
    public void i_verify_that_the_yes_button_is_highlighted() {
        Assert.assertTrue(new AddToCartModal(cucumberHook.getDriver()).getYesButtonStyle().contains(HighlightedStyles.GOLDEN.getStyle()),
                "Yes button is not highlighted");
    }

    @Then("I verify that the No button is highlighted")
    public void i_verify_that_the_no_button_is_highlighted() {
        Assert.assertTrue(new AddToCartModal(cucumberHook.getDriver()).getNoButtonStyle().contains(HighlightedStyles.GOLDEN.getStyle()),
                "No button is not highlighted");
    }
}