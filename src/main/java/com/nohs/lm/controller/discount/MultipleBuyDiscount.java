package com.nohs.lm.controller.discount;

import com.nohs.lm.model.Item;

import java.math.BigDecimal;
import java.util.List;

/**
 * Discount Interface for item based promotions
 * such as multiple buy from same product or multiple product applicable like any 3 for 2
 * This discount should be applied before basket discounts
 */
public interface MultipleBuyDiscount extends BaseDiscount {

    /**
     * Check and calculate the discount
     * Parameters
     * basketItems : all the items in the basket
     * Return discount amount
     */
    BigDecimal apply(List<Item> basketItems);
}
