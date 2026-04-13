package com.punchibanda.coc.player.controller;

import com.punchibanda.coc.player.dto.PlayerDTO;
import com.punchibanda.coc.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/{tag}")
    public ResponseEntity<PlayerDTO> getPlayer(@PathVariable String tag) {
        return ResponseEntity.ok(playerService.getPlayer(tag));
    }
}