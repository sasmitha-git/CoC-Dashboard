package com.punchibanda.coc.player.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyPlayerTokenResponseDTO implements Serializable {
    private String tag;
    private String token;
    private String status;
}
