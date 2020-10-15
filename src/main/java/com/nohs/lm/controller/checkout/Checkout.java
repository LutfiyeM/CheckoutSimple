package com.nohs.lm.controller.checkout;

import com.nohs.lm.controller.discount.BaseDiscount;
import com.nohs.lm.controller.discount.BasketTotalDiscount;
import com.nohs.lm.controller.discount.MultipleBuyDiscount;
import com.nohs.lm.model.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Checkout {

    private final List<Item> items;
    private final List<BaseDiscount> discounts;

    public Checkout(List<BaseDiscount> discounts) {
        this.items = new ArrayList<>();
        this.discounts = discounts;
    }

    /**
     * Add new item to basket
     */
    public void scan(Item item) {
        if (item != null)
            items.add(item);
    }

    /**
     * Get Basket total before discounts applied
     */
    private BigDecimal getBasketTotalBeforeDiscount(List<Item> basketItems) {
        return basketItems.stream()
                .map(Item::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //region Discount

    /**
     * Apply Discounts which are inherited from MultipleBuyDiscount
     * This discount effects only related items' prices
     */
    private BigDecimal applyMultipleBuyDiscounts(List<Item> basketItems) {
        List<MultipleBuyDiscount> multipleBuyDiscounts = discounts.stream()
                .filter(MultipleBuyDiscount.class::isInstance)
                .map(MultipleBuyDiscount.class::cast)
                .collect(Collectors.toList());

        return multipleBuyDiscounts
                .stream()
                .map(it -> it.apply(basketItems))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Apply Discounts which are inherited from BasketDiscount
     * This discount effects directly basket total
     */
    private BigDecimal applyBasketDiscounts(BigDecimal total) {
        List<BasketTotalDiscount> basketTotalDiscounts = discounts.stream()
                .filter(BasketTotalDiscount.class::isInstance)
                .map(BasketTotalDiscount.class::cast)
                .collect(Collectors.toList());

        return basketTotalDiscounts
                .stream()
                .map(it -> it.apply(total))
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
    }

    /**
     * get sum of all discounts applied to the basket
     */
    private BigDecimal getTotalDiscount(List<Item> basketItems) {
        final BigDecimal totalBeforeDiscount = getBasketTotalBeforeDiscount(basketItems);
        final BigDecimal basketItemsDiscountTotal = applyMultipleBuyDiscounts(basketItems);
        final BigDecimal basketTotalDiscount = applyBasketDiscounts(totalBeforeDiscount.subtract(basketItemsDiscountTotal));

        return basketItemsDiscountTotal.add(basketTotalDiscount);
    }
    // endregion

    /**
     * Get Basket total after discounts applied
     */
    public Double getBasketTotalAfterDiscount() {
        final List<Item> copyOfBasketItems = new ArrayList<>(items);
        BigDecimal basketTotalBeforeDiscount = getBasketTotalBeforeDiscount(copyOfBasketItems);
        BigDecimal totalDiscount = getTotalDiscount(copyOfBasketItems);

        BigDecimal basketTotalAfterDiscount = basketTotalBeforeDiscount
                .subtract(totalDiscount)
                .setScale(2, BigDecimal.ROUND_HALF_EVEN);

        return basketTotalAfterDiscount.doubleValue();
    }
}
