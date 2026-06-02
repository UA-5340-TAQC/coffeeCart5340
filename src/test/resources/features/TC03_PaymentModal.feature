Feature: Payment details modal window behaviour
  As a customer
  I want to click the Total button to open a payment form
  Where I can enter my name and email to receive a payment link

  Background:
    Given I am on the Menu page
    And I add "Espresso" to the cart
    And I navigate to the Cart page
    And the Total button displays "Total: $10.00"

  Scenario: Clicking Total button opens Payment details modal
    When I click the Total button
    Then the payment modal is visible
    And the modal title is "Payment details"
    And the modal close button "×" is visible

  Scenario: Closing and reopening the Payment details modal
    When I click the Total button
    And I click the modal close button "×"
    Then the payment modal is not visible

    When I click the Total button again
    Then the payment modal is visible
    And the modal title is "Payment details"

  Scenario: Payment modal overlays the cart page content
    When I click the Total button
    Then the payment modal is visible
    And the modal title is "Payment details"
    And the modal overlays the cart page content
    And the cart item "Espresso" still exists in the cart

