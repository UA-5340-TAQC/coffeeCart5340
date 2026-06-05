Feature: Adjusting item quantity in the shopping cart
  As a customer
  I want to adjust the quantity of each item in my cart

  Background:
    Given I am on the menu page
    When I click on any coffee cup "Espresso" on the menu to add it to the cart
    And I open the cart page

  Scenario: Increasing and decreasing Espresso quantity in the cart
    Then the cart item "Espresso" displays unit description "$10.00 x 1"
    And the cart item "Espresso" displays subtotal "$10.00"
    And the cart total displays "Total: $10.00"

    When I click the plus button 2 times for "Espresso"
    Then the cart item "Espresso" displays unit description "$10.00 x 3"
    And the cart item "Espresso" displays subtotal "$30.00"
    And the cart total displays "Total: $30.00"

    When I click the minus button 1 times for "Espresso"
    Then the cart item "Espresso" displays unit description "$10.00 x 2"
    And the cart item "Espresso" displays subtotal "$20.00"
    And the cart total displays "Total: $20.00"