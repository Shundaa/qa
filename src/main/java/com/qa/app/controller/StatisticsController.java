package com.qa.app.controller;

import com.qa.app.dao.request.EditAllStatistics;
import com.qa.app.dao.response.UserDao;
import com.qa.app.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/add/coin/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDao> addCoin(@PathVariable Long id) {
        return ResponseEntity.ok(statisticsService.addCoin(id));
    }

    @GetMapping("/add/refill/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDao> addRefill(@PathVariable Long id) {
        return ResponseEntity.ok(statisticsService.addRefill(id));
    }

    @GetMapping("/add/victory/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDao> addVictory(@PathVariable Long id) {
        return ResponseEntity.ok(statisticsService.addVictory(id));
    }

    @GetMapping("/add/bug/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDao> addBug(@PathVariable Long id) {
        return ResponseEntity.ok(statisticsService.addBug(id));
    }

    @GetMapping("/remove/coin/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDao> removeCoin(@PathVariable Long id) {
        return ResponseEntity.ok(statisticsService.removeCoin(id));
    }

    @GetMapping("/remove/refill/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDao> remove(@PathVariable Long id) {
        return ResponseEntity.ok(statisticsService.removeRefill(id));
    }

    @GetMapping("/remove/victory/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDao> removeVictory(@PathVariable Long id) {
        return ResponseEntity.ok(statisticsService.removeVictory(id));
    }

    @GetMapping("/remove/bug/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDao> removeBug(@PathVariable Long id) {
        return ResponseEntity.ok(statisticsService.removeBug(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDao> getStatistics(@PathVariable Long id) {
        return ResponseEntity.ok(statisticsService.getStatistics(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDao> editAllStatistics(@PathVariable Long id,
                                                     @RequestBody EditAllStatistics editAllStatistics) {
        return ResponseEntity.ok(statisticsService.editAllStatistics(id, editAllStatistics));
    }
}
