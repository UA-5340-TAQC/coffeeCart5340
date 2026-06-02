package org.coffeecart5340.steps;

import io.cucumber.java.bs.A;
import io.cucumber.java.en.*;
import org.coffeecart5340.hooks.CucumberHook;
import org.coffeecart5340.ui.enumData.HighlightedStyles;
import org.coffeecart5340.ui.modals.AddToCartModal;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class ConfirmPurchaseModalSteps {

    private final AddToCartModal addToCartModal;

    public ConfirmPurchaseModalSteps(CucumberHook cucumberHook) {
        addToCartModal = new AddToCartModal(cucumberHook.getDriver());
    }

    @When("I hover over the Yes button")
    public void i_hover_over_the_yes_button() {
        addToCartModal.hoverOverYesButton();
    }

    @When("I click the Yes button")
    public void i_click_the_yes_button() {
        addToCartModal.clickYesButton();
    }

    @When("I click the No button")
    public void i_click_the_no_button() {
        addToCartModal.clickNoButton();
    }

    @Then("I verify that the confirmation modal appears with Yes and No buttons")
    public void i_verify_that_the_confirmation_modal_appears_with_yes_and_no_buttons() {
        Assert.assertTrue(addToCartModal.isButtonYesDisplayed(),
                "Yes button is not displayed in the confirmation modal");
        Assert.assertTrue(addToCartModal.isButtonNoDisplayed(),
                "No button is not displayed in the confirmation modal");
    }

    @Then("I verify that the Yes button is highlighted")
    public void i_verify_that_the_yes_button_is_highlighted() {
        Assert.assertTrue(addToCartModal.getYesButtonStyle().contains(HighlightedStyles.GOLDEN.getStyle()),
                "Yes button is not highlighted");
    }

    @Then("I verify that the No button is highlighted")
    public void i_verify_that_the_no_button_is_highlighted() {
        Assert.assertTrue(addToCartModal.getNoButtonStyle().contains(HighlightedStyles.GOLDEN.getStyle()),
                "No button is not highlighted");
    }
}