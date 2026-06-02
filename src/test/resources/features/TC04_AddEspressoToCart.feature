Feature: Adding Espresso to cart updates cart contents and total
  As a customer
  I want to add an item to my cart
  So I can review it and proceed to payment

  Background:
    Given I am on the Menu page
    And the "Espresso" product is visible and available

  Scenario: Adding Espresso to cart updates cart contents and total
    When I click on the "Espresso" coffee cup
    And I navigate to the Cart page
    Then I am redirected to the Cart page

    And the cart contains the following item:
      | name     | unitDesc   | total  |
      | Espresso | $10.00 x 1 | $10.00 |

    And the cart total displays "Total: $10.00"
    And the Total button is visible
    And the Total button is enabled

  Scenario Outline: Adding different coffee items updates cart contents and total
    When I click on the "<coffee>" coffee cup
    And I navigate to the Cart page
    Then the cart contains the following item:
      | name     | unitDesc          | total    |
      | <coffee> | <unitPrice> x 1   | <total>  |

    And the cart total displays "Total: <total>"
    And the Total button is visible
    And the Total button is enabled

    Examples:
      | coffee     | unitPrice | total   |
      | Espresso   | $10.00    | $10.00  |
      | Cappuccino | $19.00    | $19.00  |
      | Americano  | $12.00    | $12.00  |

  Scenario: Cart contents remain after navigating back to Menu page
    When I click on the "Espresso" coffee cup
    And I navigate to the Cart page
    And I navigate back to the Menu page
    Then the cart counter shows "cart (1)"
    And I navigate to the Cart page
    And the cart contains the following item:
      | name     | unitDesc   | total  |
      | Espresso | $10.00 x 1 | $10.00 |

