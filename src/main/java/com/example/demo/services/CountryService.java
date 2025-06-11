package com.example.demo.services;

import com.example.demo.api.response.CountryResponse;
import com.example.demo.cache.AppCache;
import com.example.demo.constants.Placeholder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CountryService {

//    @Value("${country.api.key}")
//    public String countryAPI;



    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public List<CountryResponse> getCountry(String country){
        String finalAPI = appCache.appCache.get(AppCache.keys.country_api.toString()).replace(Placeholder.country, country);
//        ResponseEntity<CountryResponse> response = restTemplate.exchange(countryAPI, HttpMethod.GET, null, CountryResponse.class);

        ResponseEntity<List<CountryResponse>> response = restTemplate.exchange(
                finalAPI,
                HttpMethod.GET,
                null,
//                List<CountryResponse>
                new ParameterizedTypeReference<List<CountryResponse>>() {}
        );

//        CountryResponse body = response.getBody();


        return response.getBody();
    }


}
