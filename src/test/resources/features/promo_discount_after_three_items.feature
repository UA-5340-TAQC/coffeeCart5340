Feature: ToDo

  Scenario: ToDo
    When the user adds the coffee cup {string} to the cart
    Then the promo message should not be displayed
    Then the promo message should be displayed
    When the user hovers over the checkout preview
    Then the checkout preview should be displayed
    Then the coffee item {string} should be displayed in the checkout preview
    And all coffee items are removed from the cart