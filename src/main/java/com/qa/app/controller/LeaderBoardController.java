package com.qa.app.controller;

import com.qa.app.dao.response.LeaderDao;
import com.qa.app.service.LeaderBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leaderBoard")
@RequiredArgsConstructor
public class LeaderBoardController {

    private final LeaderBoardService leaderBoardService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<LeaderDao>> getTop5() {
        return ResponseEntity.ok(leaderBoardService.getTop5());
    }

    @GetMapping("/{courseId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<LeaderDao>> getTop5ByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(leaderBoardService.getTop5ByCourse(courseId));
    }
}
