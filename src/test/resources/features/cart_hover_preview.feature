Feature: Cart Hover Preview

  Scenario: Verify that hovering over the Total button shows the cart preview with correct items
    Given the cart preview is empty before adding any items
    When the user adds 2 "Espresso" cups
    Then the total price is successfully updated to "20.00"
    When the user hovers over the Total button
    Then the cart preview becomes visible
    And the cart preview is no longer empty
    And the "Espresso" coffee is displayed in the cart preview
    And the quantity of "Espresso" in the preview matches the expected quantity of 2