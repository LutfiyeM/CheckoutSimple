package com.nohs.lm.controller;

import com.nohs.lm.controller.checkout.Checkout;
import com.nohs.lm.controller.discount.BaseDiscount;
import com.nohs.lm.controller.discount.BasketTotalDiscountImpl;
import com.nohs.lm.controller.discount.MultipleBuyDiscountImpl;
import com.nohs.lm.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CheckoutTest {

    private Checkout checkout;

    /**
     * Checkout should not crash or produce error for null value of item
     * */
    @Test()
    void shouldNotScanNullableItem() {
        checkout = new Checkout(new ArrayList<>());
        checkout.scan(null);

        assertThat(checkout.getBasketTotalAfterDiscount()).isEqualTo(0);
    }

    /**
     * Group of tests for checkout without discount
     * */
    @Nested
    class CheckoutWithoutDiscount {

        @BeforeEach
        void setup() {
            checkout = new Checkout(new ArrayList<>());
        }

        /**
         * checkout should return correct total for single item
         * */
        @Test
        void shouldCalculateTotal() {
            checkout.scan(itemTravelCardHolder());

            assertThat(checkout.getBasketTotalAfterDiscount()).isEqualTo(9.25D);
        }

        /**
         * checkout should return correct total for multiple items
         * */
        @Test
        void shouldCalculateTotalWithMultipleItems() {
            checkout.scan(itemTravelCardHolder());
            checkout.scan(itemPersonalisedCufflinks());
            checkout.scan(itemKidsTShirt());

            assertThat(checkout.getBasketTotalAfterDiscount()).isEqualTo(74.2D);
        }
    }

    /**
     * Group of tests for checkout with discount
     * */
    @Nested
    class CheckoutWithDiscount {
        private Checkout checkout;

        @BeforeEach
        void setup() {
            List<BaseDiscount> discounts = new ArrayList<>();
            discounts.add(new BasketTotalDiscountImpl(new BigDecimal("60"), new BigDecimal("10")));
            discounts.add(new MultipleBuyDiscountImpl("001", 2, new BigDecimal("8.50")));
            checkout = new Checkout(discounts);
        }

        /**
         * checkout should return correct total for test scenario 1
         * */
        @Test
        void basketTotalDiscountShouldBeApplied() {
            checkout.scan(itemTravelCardHolder());
            checkout.scan(itemPersonalisedCufflinks());
            checkout.scan(itemKidsTShirt());

            assertThat(checkout.getBasketTotalAfterDiscount()).isEqualTo(66.78);
        }

        /**
         * checkout should return correct total for test scenario 2
         * */
        @Test
        void multipleBuyDiscountShouldBeApplied() {
            checkout.scan(itemTravelCardHolder());
            checkout.scan(itemKidsTShirt());
            checkout.scan(itemTravelCardHolder());

            assertThat(checkout.getBasketTotalAfterDiscount()).isEqualTo(36.95);
        }

        /**
         * checkout should return correct total for test scenario 3
         * */
        @Test
        void multipleDiscountShouldBeApplied() {
            checkout.scan(itemTravelCardHolder());
            checkout.scan(itemPersonalisedCufflinks());
            checkout.scan(itemTravelCardHolder());
            checkout.scan(itemKidsTShirt());

            assertThat(checkout.getBasketTotalAfterDiscount()).isEqualTo(73.76);
        }
    }

    private static Item itemTravelCardHolder() {
        return new Item("001", "Travel Card Holder", new BigDecimal("9.25"));
    }

    private static Item itemPersonalisedCufflinks() {
        return new Item("002", "Personalised cufflinks", new BigDecimal("45"));
    }

    private static Item itemKidsTShirt() {
        return new Item("003", "Kids T-shirt", new BigDecimal("19.95"));
    }

}
