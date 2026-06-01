Feature: Header navigation

  Scenario: TC-14 Verify that user can navigate between Menu and Cart pages using header links
    Given the user is on the Coffee Cart menu page
    Then the cart header link should be visible
    When the user clicks the cart link in the header
    Then the cart page URL should contain "/cart"
    And the empty cart message "No coffee, go add some." should be displayed
    When the user clicks the menu link in the header
    Then the coffee card "Espresso" should be displayed on the menu page
    And the menu page URL should match the base URL