package com.devsu.hackerearth.backend.account.service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devsu.hackerearth.backend.account.service.client.dto.ClientDto;

@Service
public class ClientServiceClient {
    private final RestTemplate restTemplate;

    @Value("${client.service.url}")
    private String clientServiceUrl;

    public ClientServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ClientDto geClient(Long clientId) {
        try {
            return restTemplate.getForObject(
                    clientServiceUrl + "/api/clients/" + clientId, ClientDto.class);
        } catch (Exception ex) {
            throw new RuntimeException("Error while fetching client with id: " + clientId);
        }
    }
}
