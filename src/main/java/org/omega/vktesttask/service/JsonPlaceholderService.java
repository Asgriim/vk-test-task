package org.omega.vktesttask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class JsonPlaceholderService {

    @Value("${json.placeholder.path}")
    private String rootPath;

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> getRequest(String resource) {
        return restTemplate.getForEntity(rootPath + resource, String.class);
    }

    public ResponseEntity<String> postRequest(String resource, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(rootPath + resource, HttpMethod.POST, entity, String.class);
    }


    public ResponseEntity<String> putRequest(String resource, String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(rootPath + resource, HttpMethod.PUT, entity, String.class);
    }

    public ResponseEntity<String> deleteRequest(String resource) {
        HttpHeaders headers = new HttpHeaders();
        return restTemplate.exchange(rootPath + resource, HttpMethod.DELETE,new HttpEntity<String>("", headers),String.class);
    }




}
