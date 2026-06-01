Feature: Coffee cup details and cart functionality

  Scenario: TC-43 Verify that Espresso Macchiato cup is displayed with correct data and can be added to the cart
    Given the user is on the Coffee Cart menu page
    When the user finds the coffee cup "Espresso Macchiato"
    Then the coffee cup should be visible
    And the coffee cup name should be "Espresso Macchiato"
    And the coffee cup price should be "$12.00"
    When the user clicks the coffee cup
    Then the cart counter should display "cart (1)"
    And the coffee cup "Espresso Macchiato" is removed from the cart