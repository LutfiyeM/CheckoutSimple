Checkout Simple
====================
This project is simple checkout system.
 
This project contains a basic Item model, 2 promotion implementation and checkout controller with unit tests. 

## Dependencies
- junit-jupiter-engine
- assertj

## Requirements
- Java 8

## Products:
Sample product information listed below ;

|Product code   | Name                      | Price     |
|:-------------:|:-------------------------:|----------:|
| 001           | Travel Card Holder        | £9.25     |
| 002           | Personalised cufflinks    | £45.00    |
| 003           | Kids T-shirt              | £19.95    |

### Implemented promotions
2 promotion type has been implemented and applied in test scenarios.

1. If you spend over £60, then you get 10% off your purchase
2. If you buy 2 or more travel card holders then the price drops to £8.50.

### Build
`mvn clean package`

# Tests
22 test cases have been implemented in the following files ;
 - ItemTest
 - BasketTotalDiscountTest
 - MultipleBuyDiscountTest
 - CheckoutTest
 
# License.
 All code is under Apache License
 
 