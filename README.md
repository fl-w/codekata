# codekata

This holds my solutions to the CodeKata problems.


## kata09:

```
http://codekata.com/kata/kata09-back-to-the-checkout/

```

### Design Approach
The checkout system was developed with the goal of decoupling the pricing rules from the checkout logic, thereby enhancing flexibility and allowing for the easy addition of new pricing strategies in the future.


### Core Components

    Checkout: The main class that clients interact with. It processes scans of items (SKUs) and calculates the total price based on the current pricing rules.

    PriceCatalog: Holds the unit pricing and ongoing promotions; associating SKUs with their promotions. It allows dynamic addition of pricing.

    Promo: An interface for different pricing promotions. Implementations define how the price for an item (or items) is calculated, supporting individual prices as well as promotions.

    MultiBuyPromo: Implements the Promo interface to handle special promotions like "three for a dollar" or "buy two, get one free".

## How to run

* Clone this repository
* Make sure you are using JDK 17+ and Gradle 6.8.x
* You can build the project and run the tests by running:

```
gradle test

```
