package com.nohs.lm.controller.discount;

import com.nohs.lm.model.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class MultipleBuyDiscountImpl implements MultipleBuyDiscount {

    private final String itemCode;

    private final int countThreshold;

    private final BigDecimal priceAfterDiscount;

    public MultipleBuyDiscountImpl(String itemCode, int countThreshold, BigDecimal priceAfterDiscount) {
        this.itemCode = Objects.requireNonNull(itemCode, "amount must not be null");
        this.countThreshold = countThreshold;
        this.priceAfterDiscount = Objects.requireNonNull(priceAfterDiscount, "amount must not be null");
    }

    @Override
    public BigDecimal apply(List<Item> basket) {
        final long quantityOfPromotedItem = basket.stream()
                .map(Item::code)
                .filter(c -> c.equals(itemCode))
                .count();

        final BigDecimal itemPrice = basket.stream()
                .filter(it -> it.code().equals(itemCode))
                .map(Item::price)
                .findFirst()
                .orElse(BigDecimal.ZERO);

        if (quantityOfPromotedItem >= countThreshold) {
            return (itemPrice.subtract(priceAfterDiscount)).multiply(new BigDecimal(quantityOfPromotedItem));
        } else {
            return BigDecimal.ZERO;
        }
    }
}