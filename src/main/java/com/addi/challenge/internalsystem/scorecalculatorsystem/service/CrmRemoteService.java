package com.addi.challenge.internalsystem.scorecalculatorsystem.service;

import com.addi.challenge.internalsystem.scorecalculatorsystem.domain.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CrmRemoteService {

    @Value("${crm-service-url}")
    private String remoteCrmServiceUrl;

    public List<Person> findAll() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Person>> responseEntity =
                restTemplate.exchange(
                        remoteCrmServiceUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Person>>() {
                        }
                );
        return responseEntity.getBody();
    }

}
