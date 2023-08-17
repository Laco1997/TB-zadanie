package com.laco.tbzadanie.service.impl;

import com.laco.tbzadanie.persistence.entity.Currency;
import com.laco.tbzadanie.persistence.repository.CurrencyRepository;
import com.laco.tbzadanie.service.CurrencyService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.laco.tbzadanie.persistence.entity.CurrencyFactory.generateCurrency;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class CurrencyServiceImpTest {
    @Mock
    private CurrencyRepository currencyRepository;
    private CurrencyService currencyService;
    private RestTemplate restTemplate;
    private final Currency currency = generateCurrency();

    @BeforeEach
    void setUp() {
        currencyService = new CurrencyServiceImp(currencyRepository, restTemplate);
    }

    @Test
    void getCurrency() {
        List<Currency> currencies = new ArrayList<>();
        currencies.add(currency);
        when(currencyRepository.findAll()).thenReturn(currencies);

        List<Currency> expected = currencyService.getCurrency();
        verify(currencyRepository).findAll();
        Assertions.assertThat(expected).containsOnly(currency);
    }

    @Test
    void convertCurrency() {
        when(currencyRepository.findCurrenciesByCode(currency.getCode())).thenReturn(currency);

        String from = "EUR";
        double price = 1234.5;
        String to = "USD";

        double convertedPrice = currencyService.convertCurrency(from, price, to);
        double desiredCurrency = currency.getPrice();
        assertEquals(convertedPrice, price * desiredCurrency);
    }

    @Test
    void convertCurrencyInvalidCode() {
        String from = "EUR";
        double price = 1234.5;
        String to = "123";

        when(currencyRepository.findCurrenciesByCode(to)).thenThrow(NullPointerException.class);

        currencyService.convertCurrency(from, price, to);
        assertThrows(Exception.class, null);
    }
}