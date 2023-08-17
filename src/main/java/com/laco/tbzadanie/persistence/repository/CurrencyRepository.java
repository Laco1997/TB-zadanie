package com.laco.tbzadanie.persistence.repository;

import com.laco.tbzadanie.persistence.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findCurrenciesByCode(String code);
}
