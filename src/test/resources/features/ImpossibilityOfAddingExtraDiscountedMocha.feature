Feature: Impossibility of adding an extra discounted Mocha to the cart
  As a user, I want to ensure that when I add a discounted Mocha to the cart after adding 3 random items, I cannot add another one at the same discounted price, so that I can only benefit from the discount once per order.

  Background:
    Given I am on the menu page
    And I have an empty cart

  @Smoke @P250 @Regression @Dmytro-Syadro
  Scenario: Verify impossibility of adding extra discounted Mocha after adding 3 cups of coffee to the cart
    When I click on the coffee cups:
      | Espresso   |
      | Americano  |
      | Cappuccino |
    Then I verify that the lucky modal day appears
    When I click on the Yes button
    Then I verify that the lucky modal discount disapears
    When I hover over the total checkout button
    Then I verify that checkout menu appears with added items
    And I verify that the + button is disabled for the "Mocha" coffee cup
    And I verify that 3 cups of coffee and 1 discounted Mocha are added to the cart
    When I navigate to the cart page
    Then I verify that added 4 types of coffee including discounted Mocha are present in the cart
    And I verify that the total checkout is counted correctly with 0.5 discount for the Mocha
    And I verify that the adding feature is disabled for the "Mocha" coffee cup on the Cart page
