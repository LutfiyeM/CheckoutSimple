package com.nohs.lm.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {

    private final String code;
    private final String name;
    private final BigDecimal price;

    public Item(String code, String name, BigDecimal price) {
        this.code = Objects.requireNonNull(code, "code must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.price = Objects.requireNonNull(price, "price must not be null");
    }

    public String code() {
        return code;
    }

    public String name() {
        return name;
    }

    public BigDecimal price() {
        return price;
    }
}
