Feature: Cart item removal

  Background:
    Given the Cart preview is empty

  Scenario: Cart becomes empty after removing all added coffee items
    When I add 1 "Espresso" to the cart
    And I add 1 "Cappuccino" to the cart
    Then the cart counter should display 2
    When I open the cart page
    Then the cart should contain "Espresso"
    And the cart should contain "Cappuccino"
    When I remove "Espresso" from the cart
    Then the cart should contain "Cappuccino"
    When I remove "Cappuccino" from the cart
    Then the cart should be empty
    And the cart counter should display 0
