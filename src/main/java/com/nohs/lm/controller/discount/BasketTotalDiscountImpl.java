package com.nohs.lm.controller.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class BasketTotalDiscountImpl implements BasketTotalDiscount {

    private final BigDecimal threshold;

    private final BigDecimal discountPercentage;

    public BasketTotalDiscountImpl(BigDecimal threshold, BigDecimal percentage) {
        this.threshold = Objects.requireNonNull(threshold, "amount must not be null");
        this.discountPercentage = Objects.requireNonNull(percentage, "percentage must not be null");
    }

    @Override
    public BigDecimal apply(BigDecimal basketTotal) {
        return (basketTotal.compareTo(threshold) > 0) ?
                basketTotal
                        .divide(new BigDecimal(100), 6, RoundingMode.HALF_UP)
                        .multiply(discountPercentage)
                : BigDecimal.ZERO;
    }
}
