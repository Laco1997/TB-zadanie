package com.laco.tbzadanie.persistence.repository;

import com.laco.tbzadanie.persistence.entity.Currency;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.laco.tbzadanie.persistence.entity.CurrencyFactory.generateCurrency;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CurrencyRepositoryTest {

    @Autowired
    private CurrencyRepository currencyRepository;
    private final Currency currency = generateCurrency();

    @BeforeEach
    void setUp() {
        currencyRepository.save(currency);
    }

    @AfterEach
    void tearDown() {
        currencyRepository.deleteAll();
    }

    @Test
    void findCurrenciesByCode() {
        final Currency expectedCurrency = currencyRepository.findCurrenciesByCode(currency.getCode());
        assertTrue(new ReflectionEquals(expectedCurrency, "uuid").matches(currency));
    }

    @Test
    void findCurrenciesByEmptyCode() {
        final Currency expectedCurrency = currencyRepository.findCurrenciesByCode("");
        assertFalse(new ReflectionEquals(expectedCurrency, "uuid").matches(currency));
    }

    @Test
    void findCurrenciesByInvalidCode() {
        final Currency expectedCurrency = currencyRepository.findCurrenciesByCode("123");
        assertFalse(new ReflectionEquals(expectedCurrency, "uuid").matches(currency));
    }
}