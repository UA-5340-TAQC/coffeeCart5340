Feature: Promotional offer re-triggers after cart quantity drops below threshold and rises again

  Background:
    Given the Cart preview is empty

  Scenario: Verify promotional offer triggers again if cart quantity falls below threshold and reaches multiple of 3 again
    When the user adds 3 "Mocha" cups
    Then the promotional banner should be displayed
    When I click the "Nah, I'll skip." button
    Then the promotional banner should not be displayed
    And the header cart counter should display 3
    When I navigate to the cart page
    And I remove "Mocha" from the cart
    Then the cart item "Mocha" quantity should be 2
    When I navigate back to the menu page
    And the user adds 1 "Espresso" cups
    Then the header cart counter should display 3
    And the promotional banner should be displayed
