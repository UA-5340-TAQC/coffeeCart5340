Feature: Adding coffee to cart from Menu page
  As a customer
  I want to click on any coffee drink on the Menu page
  And have it instantly added to my cart
  So that I can quickly build my order

  Background:
    Given I am on the Menu page
    And the cart is empty with counter "cart (0)"

  Scenario: Clicking Espresso on Menu page immediately adds it to cart
    When I click on the "Espresso" coffee cup
    Then the cart counter updates to "cart (1)"

    When I click on the cart link "cart (1)"
    Then I am redirected to the Cart page

    And the cart contains the following item:
      | name     | unitDesc    | total   |
      | Espresso | $10.00 x 1  | $10.00  |

    And the cart item "Espresso" has increment button visible
    And the cart item "Espresso" has decrement button visible
    And the cart item "Espresso" has delete button visible