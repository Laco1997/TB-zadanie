package com.laco.tbzadanie.controller;

import com.laco.tbzadanie.persistence.entity.Currency;
import com.laco.tbzadanie.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = "/currency")
    @ResponseStatus(HttpStatus.OK)
    public List<Currency> getCurrency() {
        return currencyService.getCurrency();
    }

    @GetMapping(value = "/currency/convert")
    @ResponseStatus(HttpStatus.OK)
    public Double convertCurrency(@RequestParam String from,
                                   @RequestParam double price,
                                   @RequestParam String to) {
        return currencyService.convertCurrency(from, price, to);
    }

}
