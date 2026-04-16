package com.punchibanda.coc.clan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClanDTO implements Serializable {

    private String tag;
    private String name;
    private String description;

    @JsonProperty("clanLevel")
    private int clanLevel;

    @JsonProperty("clanPoints")
    private int clanPoints;

    private int members;

    @JsonProperty("warWins")
    private int warWins;

    @JsonProperty("warFrequency")
    private String warFrequency;

    @JsonProperty("requiredTrophies")
    private int requiredTrophies;

    @JsonProperty("badgeUrls")
    private Map<String, String> badgeUrls;

    private LocationDTO location;

    @JsonProperty("memberList")
    private List<ClanMemberDTO> memberList;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LocationDTO implements Serializable {
        private String name;
    }

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
    public static class ClanMemberDTO implements Serializable {
        private String tag;
        private String name;
        private String role;
        private int trophies;
        private int donations;

        @JsonProperty("donationsReceived")
        private int donationsReceived;

        @JsonProperty("townHallLevel")
        private int townHallLevel;

        @JsonProperty("expLevel")
        private int expLevel;

        private LeagueDTO league;

        @JsonProperty("leagueTier")
        private LeagueDTO leagueTier;

        @JsonProperty("builderBaseLeague")
        private LeagueDTO builderBaseLeague;
    }
}
