package com.punchibanda.coc.clan.client;

import com.punchibanda.coc.clan.dto.ClanDTO;
import com.punchibanda.coc.common.exception.ExternalApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClanClient {

    @Value("${coc.api.base-url}")
    private String baseUrl;

    @Value("${coc.api.token}")

    private String apiToken;

    private final RestTemplate restTemplate;

    @Cacheable(value = "clans", key = "#clanTag")
    public ClanDTO fetchClanData(String clanTag) {
        log.info("Cache MISS for clan: {} — calling CoC API", clanTag);

        String tag = clanTag.startsWith("#") ? clanTag : "#" + clanTag;
        String rawUrl = baseUrl + "/clans/" + tag.replace("#", "%23");
        log.info("Calling CoC API URL: {}", rawUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

       try {
           ResponseEntity<ClanDTO> response = restTemplate.exchange(
                   URI.create(rawUrl),
                   HttpMethod.GET,
                   entity,
                   ClanDTO.class
           );
           return response.getBody();
       } catch (HttpClientErrorException e) {
           throw new ExternalApiException("CoC API error: " + e.getMessage());
       }
    }
}