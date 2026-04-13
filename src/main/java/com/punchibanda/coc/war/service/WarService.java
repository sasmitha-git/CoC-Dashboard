package com.punchibanda.coc.war.service;

import com.punchibanda.coc.war.client.WarClient;
import com.punchibanda.coc.war.dto.WarDTO;
import com.punchibanda.coc.war.dto.WarSummaryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WarService {

    private final WarClient warClient;

    public WarDTO getCurrentWar(String clanTag) {
        return warClient.fetchCurrentWar(clanTag);
    }

    public WarSummaryDTO getWarSummary(String clanTag) {
        WarDTO war = warClient.fetchCurrentWar(clanTag);

        String result = "ongoing";
        if (war.getState().equals("warEnded")) {
            int ourStars = war.getClan().getStars();
            int theirStars = war.getOpponent().getStars();
            double ourDestruction = war.getClan().getDestructionPercentage();
            double theirDestruction = war.getOpponent().getDestructionPercentage();

            if (ourStars > theirStars) result = "win";
            else if (ourStars < theirStars) result = "loss";
            else if (ourDestruction > theirDestruction) result = "win";
            else if (ourDestruction < theirDestruction) result = "loss";
            else result = "draw";
        }

        return WarSummaryDTO.builder()
                .state(war.getState())
                .result(result)
                .teamSize(war.getTeamSize())
                .startTime(war.getStartTime())
                .endTime(war.getEndTime())
                .clanTag(war.getClan().getTag())
                .clanName(war.getClan().getName())
                .clanStars(war.getClan().getStars())
                .clanDestruction(war.getClan().getDestructionPercentage())
                .opponentTag(war.getOpponent().getTag())
                .opponentName(war.getOpponent().getName())
                .opponentStars(war.getOpponent().getStars())
                .opponentDestruction(war.getOpponent().getDestructionPercentage())
                .build();
    }
}