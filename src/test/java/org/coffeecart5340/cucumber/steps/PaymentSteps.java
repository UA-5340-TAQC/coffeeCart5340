package org.coffeecart5340.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.coffeecart5340.ui.modals.PaymentModal;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.utils.DriverManager;
import org.testng.Assert;

public class PaymentSteps {

    private MenuPage menuPage;
    private PaymentModal paymentModal;

    public PaymentSteps() {
        menuPage = new MenuPage(DriverManager.getDriver());
    }

    @Given("the user clicks on the {string} coffee cup")
    public void theUserClicksOnTheCoffeeCup(String coffeeName) {
        menuPage.clickCoffeeCup(coffeeName);
    }

    @And("the user navigates to the Cart Page")
    public void theUserNavigatesToTheCartPage() {
        menuPage.goToCartPage();
    }

    @When("the user clicks the Checkout button on the Total Button component")
    public void theUserClicksTheCheckoutButton() {
        menuPage.getTotalButton().clickCheckoutButton();
    }

    @Then("the Payment Modal opens")
    public void thePaymentModalOpens() {
        paymentModal = menuPage.getPaymentModal();
    }

    @When("the user clicks the Submit button with an empty form")
    public void theUserClicksSubmitWithEmptyForm() {
        paymentModal.clickSubmit();
    }

    @Then("a browser validation error is expected for the empty Name field")
    public void expectedErrorForEmptyNameField() {
        Assert.assertFalse(paymentModal.getNameValidationMessage().isEmpty(),
                "Expected browser validation error on empty Name field.");
    }

    @When("the user enters the name {string} and clicks Submit")
    public void theUserEntersNameAndClicksSubmit(String name) {
        paymentModal.enterName(name);
        paymentModal.clickSubmit();
    }

    @Then("a browser validation error is expected for the empty Email field")
    public void expectedErrorForEmptyEmailField() {
        Assert.assertFalse(paymentModal.getEmailValidationMessage().isEmpty(),
                "Expected browser validation error on empty Email field.");
    }

    @When("the user enters the email {string} and clicks Submit")
    public void theUserEntersEmailAndClicksSubmit(String email) {
        paymentModal.enterEmail(email);
        paymentModal.clickSubmit();
    }

    @Then("a browser validation error is expected due to invalid Email format")
    public void expectedErrorForInvalidEmailFormat() {
        Assert.assertFalse(paymentModal.getEmailValidationMessage().isEmpty(),
                "Expected browser validation error on invalid Email format.");
    }

    @When("the user fills in payment details with name {string}, email {string}, leaves the promo checkbox unchecked, and submits the form")
    public void theUserFillsDetailsWithoutPromo(String name, String email) {
        paymentModal.fillPaymentDetailsAndSubmit(name, email, false);
    }

    @Then("a success snackbar appears containing the text {string}")
    public void successSnackbarAppears(String expectedMessage) {
        String snackbarText = menuPage.getSnackbarText();
        Assert.assertTrue(snackbarText.contains(expectedMessage),
                "Expected success snackbar message to appear after valid checkout.");
    }

    @When("the user fills in payment details with name {string}, email {string}, checks the promo checkbox, and submits the form")
    public void theUserFillsDetailsWithPromo(String name, String email) {
        paymentModal.fillPaymentDetailsAndSubmit(name, email, true);
    }
}