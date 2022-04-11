package com.addi.challenge.internalsystem.scorecalculatorsystem.service;

import com.addi.challenge.internalsystem.scorecalculatorsystem.domain.JudicialRecord;
import com.addi.challenge.internalsystem.scorecalculatorsystem.domain.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

public class AsyncRemoteCallService {

    @Value("${judicial-records-service.url}")
    private String remoteJudicialRecordsServiceUrl;

    @Value("${national-registry-service.url}")
    private String remoteNationalRegistryServiceUrl;

    @Async
    public CompletableFuture<Person> findPersonByNationalIdentificationNumber(String nationalIdentificationNumber) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Person> responseEntity =
                restTemplate.exchange(remoteNationalRegistryServiceUrl + nationalIdentificationNumber,
                        HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), Person.class);
        return CompletableFuture.completedFuture(responseEntity.getBody());
    }

    @Async
    public CompletableFuture<JudicialRecord> findJudicialRecordByNationalIdentificationNumber(String nationalIdentificationNumber) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JudicialRecord> responseEntity =
                restTemplate.exchange(remoteJudicialRecordsServiceUrl + nationalIdentificationNumber,
                        HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), JudicialRecord.class);

        return CompletableFuture.completedFuture(responseEntity.getBody());
    }

}
