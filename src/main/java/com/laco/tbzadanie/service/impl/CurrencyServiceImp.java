package com.laco.tbzadanie.service.impl;

import com.laco.tbzadanie.model.APICurrency;
import com.laco.tbzadanie.persistence.entity.Currency;
import com.laco.tbzadanie.persistence.repository.CurrencyRepository;
import com.laco.tbzadanie.service.CurrencyService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${API_KEY}")
    private String apiKey;

    @Value("${API_HOST}")
    private String apiHost;

    @Value("${API_URI}")
    private String apiUri;

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
        headers.set("X-RapidAPI-Key", apiKey);
        headers.set("X-RapidAPI-Host", apiHost);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<APICurrency> response = restTemplate.exchange(
                apiUri, HttpMethod.GET, requestEntity, APICurrency.class);

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
