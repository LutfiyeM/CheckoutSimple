package com.nohs.lm.controller.discount;

import java.math.BigDecimal;

/**
 * Discount Interface for overall discount for basket total
 * This discount should be applied after item promotions applied
 * */
public interface BasketTotalDiscount extends BaseDiscount {

   /**
    * basketTotal parameter to check if the minimum basket total condition met
    * */
    BigDecimal apply(BigDecimal basketTotal);
}
