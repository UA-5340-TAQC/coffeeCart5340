Feature: Quick cart preview

  Background:
    Given the Cart preview is empty

  Scenario: Display quick cart preview when hovering over the Total button
    When I add 1 "Mocha" to the cart
    Then the "Total: $8.00" button should be visible
    When I hover over the Total button
    Then the cart preview should be displayed
    And the quick cart preview should contain "Mocha"
    When I move the cursor away from the Total button and the preview area
    Then the cart preview should not be displayed

