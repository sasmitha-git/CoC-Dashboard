package com.punchibanda.coc.clan.service;

import com.punchibanda.coc.clan.client.ClanClient;
import com.punchibanda.coc.clan.dto.ClanDTO;
import com.punchibanda.coc.clan.dto.ClanLeaderboardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ClanService {

    private final ClanClient clanClient;

    public ClanDTO getClan(String clanTag) {
        return clanClient.fetchClanData(clanTag);
    }

    public ClanLeaderboardDTO getLeaderboard(String clanTag, String sortBy) {
        ClanDTO clan = clanClient.fetchClanData(clanTag);

        List<ClanDTO.ClanMemberDTO> members = clan.getMemberList();

        Comparator<ClanDTO.ClanMemberDTO> comparator = switch (sortBy.toLowerCase()) {
            case "donations" -> Comparator.comparingInt(
                    ClanDTO.ClanMemberDTO::getDonations).reversed();
            case "townhall" -> Comparator.comparingInt(
                    ClanDTO.ClanMemberDTO::getTownHallLevel).reversed();
            default -> Comparator.comparingInt(
                    ClanDTO.ClanMemberDTO::getTrophies).reversed();
        };

        List<ClanDTO.ClanMemberDTO> sorted = members.stream()
                .sorted(comparator)
                .toList();

        return ClanLeaderboardDTO.builder()
                .clanTag(clan.getTag())
                .clanName(clan.getName())
                .totalMembers(clan.getMembers())
                .leaderboard(sorted)
                .sortedBy(sortBy)
                .build();
    }
}
