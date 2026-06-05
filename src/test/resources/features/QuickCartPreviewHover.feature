Feature: Quick Cart Preview
  As a user, I want to hover over the checkout button to quickly see my items

  Background:
    Given I am on the menu page
    And I have an empty cart

  @TC-20 @Petro-Derlytsia
  Scenario: Verify that hovering over the Total button displays the quick cart preview on Desktop
    When I click on any coffee cup "Mocha" on the menu to add it to the cart
    Then I verify the appearance of the checkout button "Total: $8.00"
    When I move the mouse cursor over the "Total" button without clicking
    Then I verify the appearance and contents of the quick cart preview showing added item
    When I move the mouse cursor away from the "Total" button and the popup area
    Then I verify the quick cart preview popup disappears from the screen

  @TC-19 @Petro-Derlytsia
  Scenario: Verify that double-clicking on a coffee title translates it to Chinese
    When I locate a specific coffee item "Espresso" on the menu
    Then I verify the initial language of the coffee title is "Espresso"
    When I perform a double-click action exactly on the text of the coffee title
    Then I verify the language of the clicked coffee title immediately changes to "特浓咖啡"
    When I double-click again on the text of same coffee title
    Then I verify the language of the clicked coffee title immediately changes to "Espresso"
    And I check the titles of the other coffee items on the menu remain in English
