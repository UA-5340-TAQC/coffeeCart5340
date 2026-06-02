Feature: Adjusting item quantity in the shopping cart
  As a customer
  I want to adjust the quantity of each item in my cart
  So that I can order multiple drinks of the same type without re-adding from the menu

  Background:
    Given I am on the Menu page
    And I add "Espresso" to the cart
    And I navigate to the Cart page

  Scenario: Increasing and decreasing Espresso quantity in the cart
    Then the cart item "Espresso" displays unit description "$10.00 x 1"
    And the cart item "Espresso" displays subtotal "$10.00"

    When I click the "+" button 2 times for "Espresso"
    Then the cart item "Espresso" displays unit description "$10.00 x 3"
    And the cart item "Espresso" displays subtotal "$30.00"
    And the cart total displays "Total: $30.00"

    When I click the "-" button 1 time for "Espresso"
    Then the cart item "Espresso" displays unit description "$10.00 x 2"
    And the cart item "Espresso" displays subtotal "$20.00"
    And the cart total displays "Total: $20.00"

  Scenario Outline: Adjusting quantity for multiple coffee types
    Then the cart item "<coffee>" displays unit description "<unitPrice> x 1"

    When I click the "+" button 2 times for "<coffee>"
    Then the cart item "<coffee>" displays unit description "<unitPrice> x 3"
    And the cart total displays "Total: <totalAfterIncrease>"

    When I click the "-" button 1 time for "<coffee>"
    Then the cart item "<coffee>" displays unit description "<unitPrice> x 2"
    And the cart total displays "Total: <totalAfterDecrease>"

    Examples:
      | coffee     | unitPrice | totalAfterIncrease | totalAfterDecrease |
      | Espresso   | $10.00    | Total: $30.00      | Total: $20.00      |
      | Cappuccino | $19.00    | Total: $57.00      | Total: $38.00      |
      | Americano  | $12.00    | Total: $36.00      | Total: $24.00      |
