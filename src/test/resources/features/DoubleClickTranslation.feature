Feature: Coffee title translation
  As a user, I want to double-click on a coffee name to see its translation

  Background:
    Given I am on the menu page
    And I have an empty cart

  @TC-19 @Petro-Derlytsia
  Scenario: Verify that double-clicking on a coffee title translates it to Chinese
    When I locate a specific coffee item "Espresso" on the menu
    Then I verify the initial language of the coffee title is "Espresso"
    When I perform a double-click action exactly on the text of the coffee title
    Then I verify the language of the clicked coffee title immediately changes to "特浓咖啡"
    When I double-click again on the text of same coffee title
    Then I verify the language of the clicked coffee title immediately changes to "Espresso"
    And I check the titles of the other coffee items on the menu remain in English