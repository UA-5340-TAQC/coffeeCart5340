Feature: Promotional drink offer removal after deleting qualifying items

  Background:
    Given the Cart preview is empty

  Scenario: Promotional Mocha disappears when all qualifying products are removed
    When I add 3 "Espresso" to the cart
    Then the cart counter should display 3
    And the promotional banner should be displayed
    When I click the "Yes, of course!" button
    Then the cart counter should display 4
    When I remove all 3 non-promotional items from the cart
    Then the cart counter should display 0
    And the promotional banner should not be displayed
    And the beverage list should be empty

