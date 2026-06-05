@Regression @Promo @Lazur
Feature: Promotional offer triggering logic
  As a customer,
  I want the promotional offer to re-trigger if my cart quantity falls below the discount threshold and reaches it again,
  So that I don't miss out on special discounts if I change my mind about my cart contents.

  Background:
    Given I am on the menu page
    And I have an empty cart

  Scenario Outline: TC-056 Verify promotional offer triggers again after cart quantity drops
    When the user adds 3 "<first_coffee>" cups
    Then the promotional banner should be displayed
    When I click the "Nah" button
    And I navigate to the cart page
    Then the quantity of "<first_coffee>" on the cart page should be 3
    When I remove "<first_coffee>" from the cart
    Then the quantity of "<first_coffee>" on the cart page should be 2
    When I navigate to the menu page
    And I click on any coffee cup "<second_coffee>" on the menu to add it to the cart
    Then the promotional banner should be displayed

    Examples:
      | first_coffee | second_coffee |
      | Mocha        | Espresso      |
      | Americano    | Flat White    |
