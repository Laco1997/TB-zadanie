package com.laco.tbzadanie.persistence.entity;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class CurrencyFactory {
    public static final UUID uuid = UUID.fromString("fbc36b44-9d2f-4789-91cf-c334b9d877e4");
    public static final String code = "USD";
    public static final double price = 1.091123;

    public static Currency generateCurrency() {
        return generateCurrency(
                uuid,
                code,
                price
        );
    }

    public static Currency generateCurrency(UUID uuid, String code, double price) {
        return Currency.builder()
                .uuid(uuid)
                .code(code)
                .price(price)
                .build();
    }
}
