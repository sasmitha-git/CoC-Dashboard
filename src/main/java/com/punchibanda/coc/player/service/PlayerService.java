package com.punchibanda.coc.player.service;

import com.punchibanda.coc.common.exception.ExternalApiException;
import com.punchibanda.coc.common.exception.ResourceNotFoundException;
import com.punchibanda.coc.player.dto.BuilderBaseLeagueDetailsDTO;
import com.punchibanda.coc.player.dto.PlayerDTO;
import com.punchibanda.coc.player.dto.VerifyPlayerTokenRequestDTO;
import com.punchibanda.coc.player.dto.VerifyPlayerTokenResponseDTO;
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
        log.info("Cache MISS for player: {} - calling CoC API", playerTag);

        String tag = playerTag.startsWith("#") ? playerTag : "#" + playerTag;
        String encodedTag = tag.replace("#", "%23");

        String playerUrl = baseUrl + "/players/" + encodedTag;
        log.info("Calling CoC API URL: {}", playerUrl);

        HttpEntity<String> entity = createEntity();

        try {
            ResponseEntity<PlayerDTO> response = restTemplate.exchange(
                    URI.create(playerUrl),
                    HttpMethod.GET,
                    entity,
                    PlayerDTO.class
            );

            PlayerDTO player = response.getBody();

            if (player != null
                    && player.getBuilderBaseLeague() != null
                    && player.getBuilderBaseLeague().getId() > 0
                    && player.getBuilderBaseLeague().getIconUrls() == null) {

                enrichBuilderBaseLeague(player, entity);
            }

            return player;

        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Player not found with tag: " + playerTag);
        } catch (HttpClientErrorException e) {
            throw new ExternalApiException("CoC API error: " + e.getMessage());
        }
    }

    private void enrichBuilderBaseLeague(PlayerDTO player, HttpEntity<String> entity) {
        int leagueId = player.getBuilderBaseLeague().getId();
        String leagueUrl = baseUrl + "/builderbaseleagues/" + leagueId;

        log.info("Calling Builder Base League API URL: {}", leagueUrl);

        try {
            ResponseEntity<BuilderBaseLeagueDetailsDTO> leagueResponse = restTemplate.exchange(
                    URI.create(leagueUrl),
                    HttpMethod.GET,
                    entity,
                    BuilderBaseLeagueDetailsDTO.class
            );

            BuilderBaseLeagueDetailsDTO leagueDetails = leagueResponse.getBody();

            if (leagueDetails != null) {
                player.getBuilderBaseLeague().setIconUrls(leagueDetails.getIconUrls());

                if (player.getBuilderBaseLeague().getName() == null) {
                    player.getBuilderBaseLeague().setName(leagueDetails.getName());
                }
            }
        } catch (HttpClientErrorException e) {
            log.warn("Failed to enrich builder base league {}: {}", leagueId, e.getMessage());
        }
    }


    public void verifyPlayerToken(String playerTag, String apiToken) {
        String tag = playerTag.startsWith("#") ? playerTag : "#" + playerTag;
        String encodedTag = tag.replace("#", "%23");
        String verifyUrl = baseUrl + "/players/" + encodedTag + "/verifytoken";

        HttpEntity<VerifyPlayerTokenRequestDTO> entity =
                new HttpEntity<>(new VerifyPlayerTokenRequestDTO(apiToken), createHeaders());

        try {
            ResponseEntity<VerifyPlayerTokenResponseDTO> response = restTemplate.exchange(
                    URI.create(verifyUrl),
                    HttpMethod.POST,
                    entity,
                    VerifyPlayerTokenResponseDTO.class
            );

            VerifyPlayerTokenResponseDTO body = response.getBody();

            if (body == null || !"ok".equalsIgnoreCase(body.getStatus())) {
                throw new ExternalApiException("Player API token verification failed");
            }
        } catch (HttpClientErrorException e) {
            throw new ExternalApiException("Player API token verification failed");
        }
    }



    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        return headers;
    }

    private HttpEntity<String> createEntity() {
        return new HttpEntity<>(createHeaders());
    }
}
