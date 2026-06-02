Feature: Promotional offer rejection

  Background:
    Given the Cart preview is empty

  Scenario: Promo drink is not added to cart after declining the offer
    When I add 1 "Espresso" to the cart
    And I add 1 "Espresso Macchiato" to the cart
    And I add 1 "Cappuccino" to the cart
    Then the cart counter should display 3
    And the promotional banner should be displayed
    When I click the "Nah, I'll skip." button
    Then the promotional banner should not be displayed
    And the cart counter should display 3

