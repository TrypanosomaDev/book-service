package com.example.bookservice.services;

import com.example.bookservice.properties.GeneralProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ObjectMapper objectMapper;
    private final GeneralProperties generalProperties;

    private HttpEntity<Void> prepareEntity(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }

    public ResponseEntity<String> hasPermission(String token, String role) throws HttpClientErrorException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try{
            response = restTemplate.exchange(
                    String.format(generalProperties.getAuthServiceUrl(), role),
                    HttpMethod.GET, prepareEntity(token), String.class);
        }
        catch(HttpStatusCodeException e){
            throw new HttpClientErrorException(e.getStatusCode());
        }
        catch(RestClientException e){
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

    public String getUsername(String token, String role) throws HttpClientErrorException, JsonProcessingException {
        ResponseEntity<String> response = hasPermission(token, role);
        String jsonString = response.getBody();

        assert jsonString != null;
        Map<String, Object> map = objectMapper.readValue(jsonString, new TypeReference<>(){});
        return (String)map.get("username");
    }
}