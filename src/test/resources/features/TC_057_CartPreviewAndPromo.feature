@Regression @CartPreview @Lazur
Feature: Cart preview dynamic updates with promotional offers
  As a customer,
  I want the cart preview to instantly and accurately reflect both the items I add and the promotional cups I accept,
  So that I always know exactly what is in my cart before checking out.

  Background:
    Given I am on the menu page
    And I have an empty cart

  Scenario Outline: TC-057 Verify cart preview updates correctly when accepting promotional offers
    When the user adds 3 "<first_batch>" cups
    Then the promotional banner should be displayed
    When the user hovers over the Total button
    Then the "<first_batch>" coffee is displayed in the cart preview
    When I dynamically accept the first promotional offer
    And the user adds 2 "<second_batch>" cups
    Then the promotional banner should be displayed
    When the user hovers over the Total button
    Then the "<second_batch>" coffee is displayed in the cart preview
    And the first saved promotional cup is displayed in the cart preview
    When I dynamically accept the second promotional offer
    And the user hovers over the Total button
    Then the second saved promotional cup is displayed in the cart preview

    Examples:
      | first_batch | second_batch |
      | Espresso    | Americano    |
