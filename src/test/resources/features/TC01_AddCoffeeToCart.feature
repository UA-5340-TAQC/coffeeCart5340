Feature: Adding coffee to cart from Menu page
  As a customer
  I want to click on any coffee drink on the Menu page
  And have it instantly added to my cart

  Background:
    Given I am on the menu page
    And I have an empty cart

  Scenario: Clicking Espresso on Menu page immediately adds it to cart
    When I click on any coffee cup "Espresso" on the menu to add it to the cart
    Then I verify that the quantity increases by 1 next to the Cart button

    When I open the cart page
    Then the cart should contain "Espresso"
    And the cart item "Espresso" displays unit description "$10.00 x 1"
    And the cart item "Espresso" displays subtotal "$10.00"
    And the cart total displays "Total: $10.00"
    And the cart item "Espresso" has increment button visible
    And the cart item "Espresso" has decrement button visible
    And the cart item "Espresso" has delete button visible