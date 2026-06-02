Feature: Adding an item to the cart using right-click context menu
  As a user, I want to be able to add items to my cart by right-clicking on them and selecting "Add to Cart" from the context menu, so that I can quickly add items without having to left-click first.

  Background:
    Given I am on the menu page
    And I have got an empty cart

  @Smoke @P220 @Regression @Dmytro-Syadro
  Scenario: Verify adding a cup of coffee to the cart using right-click on the card
    When I right-click on the "Espresso" coffee cup
    Then I verify that the confirmation modal appears with Yes and No buttons
    When I click the Yes button
    Then I verify that the quantity increases by 1 next to the Cart button
    When I hover over the Yes button
    Then I verify that the Yes button is highlighted
    When I navigate to the "Cart" page
    Then I verify that the "Espresso" coffee cup is added to the cart with quantity 1
    And I verify that the total price is updated accordingly
    And I verify that item is present in the list of items in the cart

