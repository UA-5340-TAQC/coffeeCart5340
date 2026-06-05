Feature: Payment details modal window behaviour
  As a customer
  I want to click the Total button to open a payment form

  Background:
    Given I am on the menu page
    When I click on any coffee cup "Espresso" on the menu to add it to the cart
    And I open the cart page

  Scenario: Clicking Total button opens Payment details modal
    When the user clicks the checkout button
    Then the payment modal is visible
    And the payment modal title is "Payment details"
    And the payment modal close button is visible

  Scenario: Closing and reopening the Payment details modal
    When the user clicks the checkout button
    And the user closes the payment modal
    Then the payment modal is not visible
    When the user clicks the checkout button
    Then the payment modal is visible
    And the payment modal title is "Payment details"

  Scenario: Payment modal overlays the cart page content
    When the user clicks the checkout button
    Then the payment modal is visible
    And the payment modal title is "Payment details"
    And the payment modal close button is visible
    And the cart should contain "Espresso"
