Feature: Quick Cart Preview
  As a user, I want to hover over the checkout button to quickly see my items

  Background:
    Given I am on the menu page
    And I have got an empty cart

  @TC-20 @Petro-Derlytsia
  Scenario: Verify that hovering over the Total button displays the quick cart preview on Desktop
    When I click on any coffee cup "Mocha" on the menu to add it to the cart
    Then I verify the appearance of the checkout button "Total: $8.00"
    When I move the mouse cursor over the "Total" button without clicking
    Then I verify the appearance and contents of the quick cart preview showing added item
    When I move the mouse cursor away from the "Total" button and the popup area
    Then I verify the quick cart preview popup disappears from the screen