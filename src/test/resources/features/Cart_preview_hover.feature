Feature: Cart Preview accurately reflects added item and quantity upon hover

  Background:
    Given the Cart preview is empty

  Scenario: Verify that the Cart Preview accurately reflects the added item and its quantity upon hover
    When the user adds 2 "Espresso" cups
    Then the total price is successfully updated to "20.00"
    When the user hovers over the Total button
    Then the cart preview becomes visible
    And the cart preview is no longer empty
    And the "Espresso" coffee is displayed in the cart preview
    And the quantity of "Espresso" in the preview matches the expected quantity of 2
