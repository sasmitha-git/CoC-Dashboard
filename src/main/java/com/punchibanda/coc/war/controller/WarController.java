package com.punchibanda.coc.war.controller;

import com.punchibanda.coc.war.dto.WarDTO;
import com.punchibanda.coc.war.dto.WarSummaryDTO;
import com.punchibanda.coc.war.service.WarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wars")
@RequiredArgsConstructor
public class WarController {

    private final WarService warService;

    @GetMapping("/{clanTag}/current")
    public ResponseEntity<WarDTO> getCurrentWar(
            @PathVariable String clanTag) {
        return ResponseEntity.ok(warService.getCurrentWar(clanTag));
    }

    @GetMapping("/{clanTag}/summary")
    public ResponseEntity<WarSummaryDTO> getWarSummary(
            @PathVariable String clanTag) {
        return ResponseEntity.ok(warService.getWarSummary(clanTag));
    }
}