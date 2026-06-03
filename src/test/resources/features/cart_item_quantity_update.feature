Feature: Cart item quantity update

  Scenario: TC-12 Verify that adding the same coffee multiple times updates quantity in Cart

    Given the user is on the Coffee Cart menu page

    When the user adds the coffee cup "Espresso" to the cart
    Then the cart counter should display "cart (1)"

    When the user adds the coffee cup "Espresso" to the cart
    Then the cart counter should display "cart (2)"

    When the user navigates to the cart page

    Then the coffee item "Espresso" should be displayed in the cart
    And the coffee item "Espresso" quantity should be 2
    And the total price should be calculated correctly for 2 items

    And the coffee cup "Espresso" is removed from the cart