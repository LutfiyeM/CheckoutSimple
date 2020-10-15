package com.nohs.lm.controller.discount;

import com.nohs.lm.model.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MultipleBuyDiscountTest {

    /**
     * Discount constructor should not allow to create instance if item code is null
     * */
    @Test
    void itemCodeMustNotBeNull() {
        assertThatThrownBy(() -> new MultipleBuyDiscountImpl(null, 0, BigDecimal.TEN))
                .isInstanceOf(NullPointerException.class);
    }

    /**
     * Discount constructor should not allow to create instance if price after discount value is null
     * */
    @Test
    void priceAfterDiscountMustNotBeNull() {
        assertThatThrownBy(() -> new MultipleBuyDiscountImpl("001", 0, null))
                .isInstanceOf(NullPointerException.class);
    }

    /**
     * Discount should not apply if minimum quantity requirement met
     * */
    @Test
    void shouldNotApplyDiscountIfItemCountLessThenThreshold() {
        final MultipleBuyDiscountImpl discount = new MultipleBuyDiscountImpl("001", 2, new BigDecimal(8.50));
        final List<Item> basketItems = new ArrayList<>();
        basketItems.add(itemTravelCardHolder());
        final BigDecimal result = discount.apply(basketItems);

        assertThat(itemTravelCardHolder().price().subtract(result)).isEqualTo(new BigDecimal(9.25).setScale( 2, RoundingMode.HALF_UP));
    }

    /**
     * Discount should apply if item count is equal to threshold
     * */
    @Test
    void shouldApplyDiscountIfBasketTotalEqualToThreshold() {
        final MultipleBuyDiscountImpl discount = new MultipleBuyDiscountImpl("001", 2, new BigDecimal(8.50));
        final List<Item> basketItems = new ArrayList<>();
        basketItems.add(itemTravelCardHolder());
        basketItems.add(itemTravelCardHolder());
        final BigDecimal result = discount.apply(basketItems);

        assertThat(itemTravelCardHolder().price().multiply(new BigDecimal(2)).subtract(result)).isEqualTo(new BigDecimal(17.00).setScale( 2, RoundingMode.HALF_UP));
    }

    /**
     * Discount should apply if item count is greater than the threshold
     * */
    @Test
    void shouldApplyDiscountIfBasketTotalGreaterThanThreshold() {
        final MultipleBuyDiscountImpl discount = new MultipleBuyDiscountImpl("001", 2, new BigDecimal(8.50));
        final List<Item> basketItems = new ArrayList<>();
        basketItems.add(itemTravelCardHolder());
        basketItems.add(itemTravelCardHolder());
        basketItems.add(itemTravelCardHolder());
        final BigDecimal result = discount.apply(basketItems);

        assertThat(itemTravelCardHolder().price().multiply(new BigDecimal(3)).subtract(result)).isEqualTo(new BigDecimal(25.50).setScale( 2, RoundingMode.HALF_UP));
    }


    private static Item itemTravelCardHolder() {
        return new Item("001", "Travel Card Holder", new BigDecimal("9.25"));
    }
}
