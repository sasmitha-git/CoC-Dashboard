package com.punchibanda.coc.war.client;

import com.punchibanda.coc.common.exception.ExternalApiException;
import com.punchibanda.coc.common.exception.ResourceNotFoundException;
import com.punchibanda.coc.war.dto.WarDTO;
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
public class WarClient {

    @Value("${coc.api.base-url}")
    private String baseUrl;

    @Value("${coc.api.token}")
    private String apiToken;

    private final RestTemplate restTemplate;

    @Cacheable(value = "wars", key = "#clanTag")
    public WarDTO fetchCurrentWar(String clanTag) {
        log.info("Cache MISS for war: {} — calling CoC API", clanTag);

        String tag = clanTag.startsWith("#") ? clanTag : "#" + clanTag;
        String rawUrl = baseUrl + "/clans/" + tag.replace("#", "%23")
                + "/currentwar";

        log.info("Calling CoC API URL: {}", rawUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<WarDTO> response = restTemplate.exchange(
                    URI.create(rawUrl),
                    HttpMethod.GET,
                    entity,
                    WarDTO.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException(
                    "No war data found for clan: " + clanTag);
        } catch (HttpClientErrorException.Forbidden e) {
            throw new ResourceNotFoundException(
                    "War log is private for clan: " + clanTag);
        } catch (HttpClientErrorException e) {
            throw new ExternalApiException(
                    "CoC API error: " + e.getMessage());
        }
    }
}