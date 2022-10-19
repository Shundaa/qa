package com.qa.app.controller;

import com.qa.app.dao.response.LeaderDao;
import com.qa.app.entities.User;
import com.qa.app.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/score")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @GetMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<LeaderDao> scoreOnePointByLoggedUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(scoreService.score(user));
    }

    @GetMapping("/add/email")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<LeaderDao> scoreOnePointByEmail(@RequestParam String email) {
        return ResponseEntity.ok(scoreService.score(email));
    }
}
