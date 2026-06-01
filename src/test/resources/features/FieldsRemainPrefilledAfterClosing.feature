Feature: Prefilled Fields After Closing the payment modal
  As a user, I want to ensure that if I fill in the fields in the purchase modal and then close it, the information I entered remains prefilled when I reopen the modal.

  Background:
    Given I am on the menu page
    And I have got an empty cart

  @Smoke @P145 @Regression @Dmytro-Syadro
    Scenario Outline: Verify that all detail fields remain pre-filled after clicking on the close button of the purchase modal
    When I click on the '<coffee>' coffee cup
    Then I verify that the product adds to the total checkout
    When I click on the total checkout button
    Then I verify that the payment modal is displayed
    When I fill in the name field with '<name>'
    Then I verify that name is displayed in the name field
    When I fill in the email field with '<email>'
    Then I verify that email is displayed in the email field
    When I click on the confirmation checkbox
    Then I verify that the checkbox is selected
    When I click on the "Close" button of the payment modal
    Then I verify that the payment modal is closed
    When I refresh the page
    And I click on the total checkout button
    Then the name field should be empty
    And the email field should be empty
    And the confirmation checkbox should not be selected

    Examples:
      | coffee     | name  | email           |
      | Espresso   | Test1 | test1@gmail.com |
      | Cappuccino | Test2 | test2@gmail.com |
      | Americano  | Test3 | test3@gmail.com |


