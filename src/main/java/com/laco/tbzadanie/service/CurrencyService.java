package com.laco.tbzadanie.service;

import com.laco.tbzadanie.persistence.entity.Currency;

import java.util.List;

public interface CurrencyService {
    List<Currency> getCurrency();

    Double convertCurrency(String from, double price, String to);
}
