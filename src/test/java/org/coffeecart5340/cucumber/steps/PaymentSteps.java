package org.coffeecart5340.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.coffeecart5340.cucumber.hooks.CucumberHook;
import org.coffeecart5340.ui.modals.PaymentModal;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.utils.DriverManager;
import org.testng.Assert;

public class PaymentSteps {

    private final MenuPage menuPage;
    private PaymentModal paymentModal;

    private final CucumberHook cucumberHook;

    public PaymentSteps(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
        menuPage = new MenuPage(DriverManager.getDriver());
    }

    @Given("the user clicks on the {string} coffee cup")
    public void theUserClicksOnTheCoffeeCup(String coffeeName) {
        menuPage.clickCoffeeCup(coffeeName);
    }

    @When("the user navigates to the Cart Page")
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

    @Then("I verify that the payment modal is displayed")
    public void i_verify_that_the_payment_modal_is_displayed() {
        Assert.assertTrue(new PaymentModal(cucumberHook.getDriver()).isDisplayed(),
                "Payment modal should be displayed after clicking Checkout.");
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
    @When("I fill in the name field with {string}")
    public void i_fill_in_the_name_field_with(String string) {
        new PaymentModal(cucumberHook.getDriver()).enterName(string);
    }


    @When("I click on the submit button")
    public void i_click_on_the_submit_button() {
        new PaymentModal(cucumberHook.getDriver()).clickSubmit();
    }

    @Then("I verify that {string} is displayed in the name field")
    public void i_verify_that_name_is_displayed_in_the_name_field(String string) {
        Assert.assertEquals(string,
                new PaymentModal(cucumberHook.getDriver()).getNameValue(),
                "Expected name field to contain the entered value.");
    }

    @When("I fill in the email field with {string}")
    public void i_fill_in_the_email_field_with(String string) {
        new PaymentModal(cucumberHook.getDriver()).enterEmail(string);
    }

    @Then("I verify that {string} is displayed in the email field")
    public void i_verify_that_email_is_displayed_in_the_email_field(String string) {
        Assert.assertEquals(string,
                new PaymentModal(cucumberHook.getDriver()).getEmailValue(),
                "Expected email field to contain the entered value.");
    }

    @When("I click on the confirmation checkbox")
    public void i_click_on_the_confirmation_checkbox() {
        new PaymentModal(cucumberHook.getDriver()).clickPromotionCheckbox();
    }

    @Then("I verify that the checkbox is selected")
    public void i_verify_that_the_checkbox_is_selected() {
        Assert.assertTrue(new PaymentModal(cucumberHook.getDriver()).isPromotionCheckboxChecked(),
                "Expected promotion checkbox to be selected after clicking.");
    }

    @When("I click on the close button of the payment modal")
    public void i_click_on_the_close_button_of_the_payment_modal() {
        new PaymentModal(cucumberHook.getDriver()).clickCloseButton();
    }

    @Then("I verify that the payment modal is closed")
    public void i_verify_that_the_payment_modal_is_closed() {
        Assert.assertFalse(new PaymentModal(cucumberHook.getDriver()).isDisplayed(),
                "Payment modal should be closed after clicking the close button.");
    }

    @Then("the name field should be empty")
    public void the_name_field_should_be_empty() {
        Assert.assertTrue(new PaymentModal(cucumberHook.getDriver()).getNameValue().isEmpty(),
                "Expected name field to be empty after closing the modal.");
    }
    @Then("the email field should be empty")
    public void the_email_field_should_be_empty() {
        Assert.assertTrue(new PaymentModal(cucumberHook.getDriver()).getEmailValue().isEmpty(),
                "Expected email field to be empty after closing the modal.");
    }
    @Then("the confirmation checkbox should not be selected")
    public void the_confirmation_checkbox_should_not_be_selected() {
        Assert.assertFalse(new PaymentModal(cucumberHook.getDriver()).isPromotionCheckboxChecked(),
                "Expected promotion checkbox to be unselected after closing the modal.");
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