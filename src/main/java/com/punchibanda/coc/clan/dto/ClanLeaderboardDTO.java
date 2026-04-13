package com.punchibanda.coc.clan.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ClanLeaderboardDTO {
    private String clanTag;
    private String clanName;
    private int totalMembers;
    private List<ClanDTO.ClanMemberDTO> leaderboard;
    private String sortedBy;
}