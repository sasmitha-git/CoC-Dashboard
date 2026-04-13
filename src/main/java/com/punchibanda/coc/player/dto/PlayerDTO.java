package com.punchibanda.coc.player.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerDTO implements Serializable {

    private String tag;
    private String name;

    @JsonProperty("townHallLevel")
    private int townHallLevel;

    private int trophies;
    private int wins;
    private int donations;

    @JsonProperty("attackWins")
    private int attackWins;

    @JsonProperty("defenseWins")
    private int defenseWins;

    private LeagueDTO league;
    private ClanDTO clan;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LeagueDTO implements Serializable {
        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ClanDTO implements Serializable {
        private String tag;
        private String name;
    }
}