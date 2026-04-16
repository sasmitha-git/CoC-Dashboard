package com.punchibanda.coc.player.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerDTO implements Serializable {

    private String tag;
    private String name;
    private String role;

    @JsonProperty("townHallLevel")
    private int townHallLevel;

    private int trophies;
    private int wins;
    private int donations;

    @JsonProperty("donationsReceived")
    private int donationsReceived;

    @JsonProperty("attackWins")
    private int attackWins;

    @JsonProperty("defenseWins")
    private int defenseWins;

    @JsonProperty("bestTrophies")
    private int bestTrophies;

    @JsonProperty("builderBaseTrophies")
    private int builderBaseTrophies;

    @JsonProperty("bestBuilderBaseTrophies")
    private int bestBuilderBaseTrophies;

    @JsonProperty("warStars")
    private int warStars;

    @JsonProperty("expLevel")
    private int expLevel;

    @JsonProperty("builderHallLevel")
    private int builderHallLevel;

    @JsonProperty("warPreference")
    private String warPreference;

    @JsonProperty("townHallWeaponLevel")
    private int townHallWeaponLevel;

    private LeagueDTO league;

    @JsonProperty("builderBaseLeague")
    private LeagueDTO builderBaseLeague;

    @JsonProperty("leagueTier")
    private LeagueDTO leagueTier;

    private ClanDTO clan;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LeagueDTO implements Serializable {
        private int id;
        private String name;

        @JsonProperty("iconUrls")
        private Map<String, String> iconUrls;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ClanDTO implements Serializable {
        private String tag;
        private String name;

        @JsonProperty("clanLevel")
        private int clanLevel;

        @JsonProperty("badgeUrls")
        private Map<String, String> badgeUrls;
    }
}
