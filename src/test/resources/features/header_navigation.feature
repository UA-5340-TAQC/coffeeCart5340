Feature: Promo message after adding third coffee item

  Scenario: TC-13 Verify that promo message appears only after adding the third coffee item

    Given the user is on the Coffee Cart menu page

    When the user adds the coffee cup "Espresso" to the cart
    Then the promo message should not be displayed

    When the user adds the coffee cup "Cappuccino" to the cart
    Then the promo message should not be displayed

    When the user adds the coffee cup "Cafe Latte" to the cart
    Then the promo message should be displayed

    When the user hovers over the checkout preview
    Then the checkout preview should be displayed

    And the coffee item "Espresso" should be displayed in the checkout preview
    And the coffee item "Cappuccino" should be displayed in the checkout preview
    And the coffee item "Cafe Latte" should be displayed in the checkout preview

    And all coffee items are removed from the cart