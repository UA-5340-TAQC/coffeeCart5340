Feature: Promotional offers trigger after 3 initial cups and 2 subsequent cups, with cart preview validation

  Background:
    Given the Cart preview is empty

  Scenario: Verify promotional offers trigger after 3 initial cups and 2 subsequent cups, and validate cart preview updates
    When the user adds 3 "Espresso" cups
    Then the promotional banner should be displayed
    When the user hovers over the Total button
    Then the cart preview becomes visible
    And the "Espresso" coffee is displayed in the cart preview
    And the quantity of "Espresso" in the preview matches the expected quantity of 3
    When I accept the promotional offer
    Then the promotional banner should not be displayed
    When the user adds 2 "Americano" cups
    Then the promotional banner should be displayed
    When the user hovers over the Total button
    Then the cart preview becomes visible
    And the "Americano" coffee is displayed in the cart preview
    And the quantity of "Americano" in the preview matches the expected quantity of 2
    When I accept the promotional offer
    Then the promotional banner should not be displayed
    When the user hovers over the Total button
    Then the cart preview becomes visible
    And the cart preview contains a discounted promo cup
