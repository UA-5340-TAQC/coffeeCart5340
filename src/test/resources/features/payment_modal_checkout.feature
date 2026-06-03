Feature: Payment Modal validations and submission

  Background:
    Given the user clicks on the "Espresso" coffee cup
    And the user navigates to the Cart Page
    When the user clicks the Checkout button on the Total Button component
    Then the Payment Modal opens

  Scenario: Verify payment form validation and successful submission without promo
    When the user clicks the Submit button with an empty form
    Then a browser validation error is expected for the empty Name field
    When the user enters the name "John Doe" and clicks Submit
    Then a browser validation error is expected for the empty Email field
    When the user enters the email "invalid-email" and clicks Submit
    Then a browser validation error is expected due to invalid Email format
    When the user fills in payment details with name "John Doe", email "john@example.com", leaves the promo checkbox unchecked, and submits the form
    Then a success snackbar appears containing the text "Thanks for your purchase. Please check your email for payment."

  Scenario: Verify successful checkout with promotional checkbox selected
    When the user fills in payment details with name "Jane Doe", email "jane@example.com", checks the promo checkbox, and submits the form
    Then a success snackbar appears containing the text "Thanks for your purchase. Please check your email for payment."