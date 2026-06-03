Feature: After completing an order, the cart should be empty.
  As a user, I want to ensure that after I complete an order, my cart is empty so that I can start fresh for my next purchase.

  Background:
    Given I am on the menu page
    And I have an empty cart

  @Smoke @P240 @Regression @Dmytro-Syadro
  Scenario Outline: Verify that the cart is empty after completing an order
    When I click on the coffee cups:
        | Espresso  |
        | Americano |
    Then I verify that the coffee cup is added to the cart with quantity 2
    When I click on the total checkout button
    Then I verify that the payment modal is displayed
    When I fill in the name field with '<name>'
    Then I verify that '<name>' is displayed in the name field
    When I fill in the email field with '<gmail>'
    Then I verify that '<gmail>' is displayed in the email field
    When I click on the confirmation checkbox
    Then I verify that the checkbox is selected
    When I click on the submit button
    Then I verify that the order confirmation message is displayed
    When I navigate to the cart page
    Then I verify that the cart is empty

    Examples:
      | name  | gmail           |
      | Test1 | test@gmail.com  |
      | Test2 | test2@gmail.com |




