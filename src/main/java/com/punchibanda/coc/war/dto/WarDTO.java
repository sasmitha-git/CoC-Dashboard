package com.punchibanda.coc.war.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WarDTO implements Serializable {

    private String state;
    private int teamSize;

    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("endTime")
    private String endTime;

    private WarClanDTO clan;
    private WarClanDTO opponent;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WarClanDTO implements Serializable {
        private String tag;
        private String name;
        private int stars;
        private int attacks;

        @JsonProperty("destructionPercentage")
        private double destructionPercentage;

        private List<WarMemberDTO> members;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WarMemberDTO implements Serializable {
        private String tag;
        private String name;

        @JsonProperty("mapPosition")
        private int mapPosition;

        @JsonProperty("townhallLevel")
        private int townhallLevel;

        private List<AttackDTO> attacks;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AttackDTO implements Serializable {
        @JsonProperty("defenderTag")
        private String defenderTag;
        private int stars;

        @JsonProperty("destructionPercentage")
        private int destructionPercentage;
    }
}