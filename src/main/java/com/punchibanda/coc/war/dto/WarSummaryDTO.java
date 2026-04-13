package com.punchibanda.coc.war.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WarSummaryDTO {
    private String state;
    private String result;
    private int teamSize;
    private String startTime;
    private String endTime;
    private String clanTag;
    private String clanName;
    private int clanStars;
    private double clanDestruction;
    private String opponentTag;
    private String opponentName;
    private int opponentStars;
    private double opponentDestruction;
}