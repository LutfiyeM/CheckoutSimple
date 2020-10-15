package com.nohs.lm.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemTest {

    /**
     * Model constructor should not allow to create instance if item code is null
     * */
    @Test
    void codeMustNotBeNull() {
        assertThatThrownBy(() -> new Item(null, "item name", BigDecimal.ONE))
                .isInstanceOf(NullPointerException.class);
    }

    /**
     * Model constructor should not allow to create instance if item name is null
     * */
    @Test
    void nameMustNotBeNull() {
        assertThatThrownBy(() -> new Item("001", null, BigDecimal.ONE))
                .isInstanceOf(NullPointerException.class);
    }

    /**
     * Model constructor should not allow to create instance if item price is null
     * */
    @Test
    void priceMustNotBeNull() {
        assertThatThrownBy(() -> new Item("001", "item name", null))
                .isInstanceOf(NullPointerException.class);
    }

    /**
     * Model should return correct value of code
     * */
    @Test
    void getCode() {
        final Item item = new Item("001", "Travel Card Holder", new BigDecimal("9.25"));
        assertThat(item.code())
                .isEqualTo("001");
    }

    /**
     * Model should return correct value of name
     * */
    @Test
    void getName() {
        final Item item = new Item("001", "Travel Card Holder", new BigDecimal("9.25"));
        assertThat(item.name())
                .isEqualTo("Travel Card Holder");
    }

    /**
     * Model should return correct value of price
     * */
    @Test
    void getPrice() {
        final Item item = new Item("001", "Travel Card Holder", new BigDecimal("9.25"));
        assertThat(item.price())
                .isEqualTo(new BigDecimal("9.25"));
    }
}
