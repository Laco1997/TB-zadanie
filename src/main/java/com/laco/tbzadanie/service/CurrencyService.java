package com.laco.tbzadanie.service;

import com.laco.tbzadanie.persistence.entity.Currency;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface CurrencyService {
    /**
     * Get all currencies
     * @return all currencies
     */
    List<Currency> getCurrency();

    /**
     * Convert currency
     * @param from - currency to be converted
     * @param price - value to be converted
     * @param to - desired currency
     * @return price of converted currency
     */
    @NotNull
    Double convertCurrency(@NotNull String from, @NotNull double price, @NotNull String to);
}
