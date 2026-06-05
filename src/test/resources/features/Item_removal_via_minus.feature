Feature: Item removal and total recalculation when decreasing quantity to zero

  Background:
    Given the Cart preview is empty

  Scenario: Verify item removal and total recalculation when decreasing quantity to zero via minus button
    When the user adds 1 "Flat White" cups
    Then the total price is successfully updated to "18.00"
    When I open the cart page
    Then the cart should contain "Flat White"
    And the quantity of "Flat White" in the cart should be 1
    When I remove "Flat White" from the cart
    Then the cart should be empty
    When I navigate back to the menu page
    And the total price on the menu page should be "0.00"
