@Smoke @Regression @Lazur
Feature: Item removal and total recalculation when decreasing cart quantity
  As a customer,
  I want to be able to completely remove an item from my cart by decreasing its quantity to zero using the minus button,
  So that I am not charged for items I no longer want and my total cost updates correctly to $0.00.

  Background:
    Given I am on the menu page
    And I have an empty cart

  Scenario Outline: TC-54 Verify item removal and total recalculation for <coffee_name>
    When I click on any coffee cup "<coffee_name>" on the menu to add it to the cart
    Then the total price is successfully updated to "<initial_price>"
    When I navigate to the cart page
    And I remove "<coffee_name>" from the cart
    Then I verify that the cart is empty
    When I navigate to the menu page
    Then the total price is successfully updated to "0.0"

    Examples:
      | coffee_name | initial_price |
      | Flat White  | 18.0          |
      | Espresso    | 10.0          |
      | Mocha       | 8.0           |
