Feature: After completing an order, the cart should be empty.
  As a user, I want to ensure that after I complete an order, my cart is empty so that I can start fresh for my next purchase.

  Background:
    Given I am on the menu page
    And I have got an empty cart

  @Smoke @P240 @Regression @Dmytro-Syadro
  Scenario: Verify that the cart is empty after completing an order
    When I click on the coffee cups:
        | Espresso  |
        | Americano |
    Then I verify that the cart contains 2 selected products
    When I click on the 'Checkout' button
    Then I verify that the purchase modal is displayed
    When I fill in the name field with "test"
    Then I verify that name is displayed in the name field
    When I fill in the email field with "test@gmail.com"
    Then I verify that email is displayed in the email field
    When I click on the confirmation checkbox
    Then I verify that the checkbox is selected
    When I click on the "Submit" button
    Then I verify that the order confirmation message is displayed
    When I click the cart icon
    Then I verify that the cart is empty




