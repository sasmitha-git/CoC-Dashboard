package com.punchibanda.coc.clan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

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

    @JsonProperty("memberList")
    private List<ClanMemberDTO> memberList;

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
    }
}