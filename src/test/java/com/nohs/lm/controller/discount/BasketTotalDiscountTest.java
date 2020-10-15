package com.nohs.lm.controller.discount;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BasketTotalDiscountTest {

    /**
     * Discount constructor should not allow to create instance if threshold is null
     * */
    @Test
    void thresholdMustNotBeNull() {
        assertThatThrownBy(() -> new BasketTotalDiscountImpl(null, BigDecimal.TEN))
                .isInstanceOf(NullPointerException.class);
    }

    /**
     * Discount constructor should not allow to create instance if discount percentage is null
     * */
    @Test
    void discountPercentageMustNotBeNull() {
        assertThatThrownBy(() -> new BasketTotalDiscountImpl(new BigDecimal(60), null))
                .isInstanceOf(NullPointerException.class);
    }

    /**
     * Discount should not apply if threshold value is less than threshold
     * */
    @Test
    void shouldNotApplyDiscountIfBasketTotalLestThanThreshold() {
        final BasketTotalDiscountImpl discount = new BasketTotalDiscountImpl(new BigDecimal(60), BigDecimal.TEN);
        final BigDecimal result = discount.apply(new BigDecimal(59));

        assertThat(result).isEqualTo(BigDecimal.ZERO);
    }

    /**
     * Discount should not apply if basket total value is equal to threshold
     * */
    @Test
    void shouldNotApplyDiscountIfBasketTotalEqualToThreshold() {
        final BasketTotalDiscountImpl discount = new BasketTotalDiscountImpl(new BigDecimal(60), BigDecimal.TEN);
        final BigDecimal result = discount.apply(new BigDecimal(60));

        assertThat(result).isEqualTo(BigDecimal.ZERO);
    }

    /**
     * Discount should apply if basket total value is greater than threshold
     * Check if the discount value is correct
     * */
    @Test
    void shouldApplyDiscountIfFinalValueIsMoreThanDiscountAmount() {
        final BasketTotalDiscountImpl discount = new BasketTotalDiscountImpl(new BigDecimal(60), BigDecimal.TEN);

        final BigDecimal result = discount.apply(new BigDecimal(61));

        assertThat(result).isEqualTo(new BigDecimal("6.100000"));
    }

}
