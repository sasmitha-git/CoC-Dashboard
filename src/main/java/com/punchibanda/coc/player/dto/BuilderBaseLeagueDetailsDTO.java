package com.punchibanda.coc.player.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuilderBaseLeagueDetailsDTO implements Serializable {

    private int id;
    private String name;

    @JsonProperty("iconUrls")
    private Map<String, String> iconUrls;
}
