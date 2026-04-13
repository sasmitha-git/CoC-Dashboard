package com.punchibanda.coc.clan.controller;

import com.punchibanda.coc.clan.dto.ClanDTO;
import com.punchibanda.coc.clan.dto.ClanLeaderboardDTO;
import com.punchibanda.coc.clan.service.ClanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clans")
@RequiredArgsConstructor
public class ClanController {

    private final ClanService clanService;

    @GetMapping("/{tag}")
    public ResponseEntity<ClanDTO> getClan(@PathVariable String tag) {
        return ResponseEntity.ok(clanService.getClan(tag));
    }

    @GetMapping("/{tag}/leaderboard")
    public ResponseEntity<ClanLeaderboardDTO> getLeaderboard(
            @PathVariable String tag,
            @RequestParam(defaultValue = "trophies") String sortBy) {
        return ResponseEntity.ok(clanService.getLeaderboard(tag, sortBy));
    }
}