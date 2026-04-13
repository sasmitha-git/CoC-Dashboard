package com.punchibanda.coc.player.service;

import com.punchibanda.coc.common.exception.ExternalApiException;
import com.punchibanda.coc.common.exception.ResourceNotFoundException;
import com.punchibanda.coc.player.dto.PlayerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerService {

    @Value("${coc.api.base-url}")
    private String baseUrl;

    @Value("${coc.api.token}")
    private String apiToken;

    private final RestTemplate restTemplate;

    @Cacheable(value = "players", key = "#playerTag")
    public PlayerDTO getPlayer(String playerTag) {
        log.info("Cache MISS for player: {} — calling CoC API", playerTag);

        String tag = playerTag.startsWith("#")
                ? playerTag
                : "#" + playerTag;

        String encodedTag = tag.replace("#", "%23");
        String rawUrl = baseUrl + "/players/" + encodedTag;

        log.info("Calling CoC API URL: {}", rawUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<PlayerDTO> response = restTemplate.exchange(
                    URI.create(rawUrl),
                    HttpMethod.GET,
                    entity,
                    PlayerDTO.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException(
                    "Player not found with tag: " + playerTag);
        } catch (HttpClientErrorException e) {
            throw new ExternalApiException(
                    "CoC API error: " + e.getMessage());
        }
    }
}