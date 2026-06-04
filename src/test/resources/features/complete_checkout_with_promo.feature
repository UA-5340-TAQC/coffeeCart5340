Feature: Complete Checkout Process with Promotional Offer
  As a customer
  I want to complete the checkout process after accepting a promotional offer
  So that I can purchase my selected drinks with a discount

  @Smoke @P21 @Regression
  Scenario: Successful end-to-end checkout after accepting promo offer and modifying cart
    When I add "Espresso" to cart
    Then the total button should display "$10.00"

    When I add "Cappuccino" to cart
    Then the total button should display "$29.00"

    When I add "Cafe Breve" to cart
    Then the promotional banner should be displayed

    When I click the "Yes, of course!" button
    Then the total button should display "$48.00"

    When I hover over the Total button
    Then the cart preview should be displayed
    And the cart preview should contain 4 items

    When I remove "Espresso" from the cart preview
    Then the total button should display "$38.00"

    When I click the total button
    Then the payment details modal should appear

    When I fill name "Test User" in the payment form
    And I fill email "test@example.com" in the payment form
    And I check the promotional messages checkbox
    And I submit the payment form
    Then a success message should appear
    And the success message should be "Thanks for your purchase. Please check your email for payment."