# Coupon API Documentation

This project is a RESTful API to manage and apply discount coupons for
an e-commerce platform.

------------------------------------------------------------------------

## 1. Implemented Use Cases

  ----------------------------------------------------------------------------
  **Type**              **Description**                **Example**
  --------------------- ------------------------------ -----------------------
  **Cart-wise discount  Discount on the total cart     `SAVE20`: 20% off on
  (percentage)**        value.                         cart total.

  **Cart-wise discount  Flat amount off the cart       `FLAT100`: ₹100 off if
  (flat)**              total.                         total ≥ ₹500.

  **Product-wise        Discount applied to specific   `OFF200`: ₹200 off
  discount**            product(s).                    Product ID 101.

  **Buy X Get Y         Free items or discount for     `BUY2GET1`: Buy 2, get
  (BxGy)**              purchasing a quantity          1 free.
                        threshold.                     

  **Active/Inactive**   Coupon can be disabled without Admin sets active =
                        deletion.                      false.

  **Date constraints**  Coupon validity between        `NEWYEAR50`: Active
                        startDate and endDate.         from Jan 1 to Jan 31.
  ----------------------------------------------------------------------------

------------------------------------------------------------------------

## 2. Planned Use Cases (Not Yet Implemented)

  ------------------------------------------------------------------------------
  **Type**                **Description**                **Example**
  ----------------------- ------------------------------ -----------------------
  **User-specific         Coupon valid only for certain  `WELCOME50` valid for
  coupons**               users or email domains.        new users only.

  **Usage limit (per      Coupon usable `n` times by     `SAVE5`: Can be applied
  user)**                 each user.                     5 times per user.

  **Usage limit           Coupon usable `n` times across `FLASH50`: First 100
  (global)**              platform.                      users only.

  **Minimum cart value**  Coupon only applicable when    `BIGSAVE`: 15% off on
                          cart total crosses a           cart ≥ ₹2000.
                          threshold.                     

  **Maximum discount      Even if percentage discount is `SAVE20`: Max ₹500 off.
  cap**                   high, cap the max discount.    

  **Payment method        Coupon applicable only for     `CARDONLY`: 10% off if
  constraints**           specific payment methods.      paid by credit card.

  **Category-wise         Only products under a category `ELECTRO10`: 10% off
  discounts**             are eligible.                  electronics.

  **First order only**    Coupon valid only for user's   `FIRST50`: 50% off
                          first order.                   first order.

  **Subscription-only**   Coupons restricted to          `PREMIUM25`: 25% off
                          premium/subscribed users.      for premium members.

  **Combination rules**   Prevent stacking of coupons or Cannot use `SAVE10` and
                          allow certain combinations.    `NEWYEAR20` together.

  **Location-based**      Coupon valid only in specific  `DELHI5`: 5% off for
                          cities or countries.           Delhi users.

  **Product quantity      Coupon applies only if minimum `BUY5OFF`: ₹100 off if
  limits**                quantity of a product is in    buying 5+ of a product.
                          cart.                          

  **Cashback coupons**    Instead of direct discount,    `CASHBACK50`: Get ₹50
                          user gets cashback balance.    cashback in wallet.

  **Referral-based        Coupons unlocked via referral  `REFER100`: ₹100 off
  coupons**               program.                       for referrer and
                                                         referee.

  **Dynamic pricing       AI-driven or demand-based      `DYNAMIC10`: Adjust
  rules**                 coupon logic.                  discount during sale.
  ------------------------------------------------------------------------------

------------------------------------------------------------------------

## 3. Edge Cases

-   Coupon is **expired** or **inactive**\
-   Coupon **code doesn't exist**\
-   Cart **total is less than the required minimum**\
-   Coupon applied to **wrong product/category**\
-   **Multiple coupons** applied incorrectly or conflicting\
-   **Concurrency issue** when two users apply a limited-use coupon
    simultaneously\
-   **Floating-point precision errors** in discount calculations\
-   Applying coupon when cart **is empty**\
-   Handling **deleted products** tied to product-wise coupons

------------------------------------------------------------------------

## 4. Constraints

-   Each coupon must have:
    -   Unique code\
    -   Start and end date\
    -   Active flag\
-   Maximum number of coupons per cart = **1** (current
    implementation).\
-   Discounts should **never make the payable amount negative**.

------------------------------------------------------------------------

## 5. Known Limitations

-   No support for:
    -   User authentication or personalization\
    -   Stacking of multiple coupons\
    -   Real-time inventory checks for BxGy offers\
-   Minimal validation and logging (to be enhanced).

------------------------------------------------------------------------

## 6. How to Test

### Create a Coupon

``` bash
POST http://localhost:8080/coupons
Content-Type: application/json

{
  "code": "SAVE20",
  "type": "cart-wise",
  "details": "{"discountPercent":20}",
  "startDate": "2025-09-01T00:00:00",
  "endDate": "2025-12-31T23:59:59",
  "active": true
}
```

### Apply Coupon

``` bash
POST http://localhost:8080/coupons/apply?code=SAVE20&cartTotal=1000
```

Response:

``` json
800.0
```

------------------------------------------------------------------------
