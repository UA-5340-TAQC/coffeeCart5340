Feature: Adding Espresso updates cart contents and total
  As a customer
  I want to add an item to my cart
  So I can review it and proceed to payment

  Background:
    Given I am on the menu page

  Scenario: Adding Espresso to cart updates cart contents and total
    When I click on any coffee cup "Espresso" on the menu to add it to the cart
    Then I verify that the quantity increases by 1 next to the Cart button

    When I open the cart page
    Then the cart should contain "Espresso"
    And the cart item "Espresso" displays unit description "$10.00 x 1"
    And the cart total displays "Total: $10.00"
    And the Total button is visible
    And the Total button is enabled
