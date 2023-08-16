package com.laco.tbzadanie.service.impl;

import com.laco.tbzadanie.model.APICurrency;
import com.laco.tbzadanie.persistence.entity.Currency;
import com.laco.tbzadanie.persistence.repository.CurrencyRepository;
import com.laco.tbzadanie.service.CurrencyService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CurrencyServiceImp implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate;

    public CurrencyServiceImp(CurrencyRepository currencyRepository,
                              RestTemplate restTemplate) {
        this.currencyRepository = currencyRepository;
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void createDefaultCurrency() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", "b841965cfdmsh7e730a08179f009p18cbe6jsn4d4f625e95ad");
        headers.set("X-RapidAPI-Host", "exchangerate-api.p.rapidapi.com");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String apiUrl = "https://exchangerate-api.p.rapidapi.com/rapid/latest/EUR";

        ResponseEntity<APICurrency> response = restTemplate.exchange(
                apiUrl, HttpMethod.GET, requestEntity, APICurrency.class);

        APICurrency apiCurrency = response.getBody();

        for(Map.Entry<String, Double> entry : apiCurrency.getRates().entrySet()){
            Currency currency = new Currency();
            currency.setCode(entry.getKey());
            currency.setPrice(entry.getValue());
            currencyRepository.save(currency);
        }

//        TODO IN CASE OF API FAILURE
//        Currency currency1 = new Currency(
//                "Australia",
//                "AUD",
//                1.7308
//        );
//
//        Currency currency2 = new Currency(
//                "Canada",
//                "CAD",
//                1.5091
//        );
//
//        currencyRepository.save(currency1);
//        currencyRepository.save(currency2);
    }

    @Override
    public List<Currency> getCurrency() {
        return currencyRepository.findAll();
    }

    @Override
    public Double convertCurrency(String from, double price, String to) {
        Currency currencyToConvert = currencyRepository.findCurrenciesByCode(to);
        double targetPrice = currencyToConvert.getPrice();
        return price * targetPrice;
    }
}
