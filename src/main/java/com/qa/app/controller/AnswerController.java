package com.qa.app.controller;

import com.qa.app.dao.request.AnswerRequest;
import com.qa.app.dao.response.AnswerDao;
import com.qa.app.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<AnswerDao>> getAllLessons() {
        return ResponseEntity.ok(answerService.get());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AnswerDao> addNewLesson(@RequestBody AnswerRequest request) {
        return ResponseEntity.ok(answerService.newAnswer(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AnswerDao> addNewLesson(@PathVariable Long id, @RequestBody AnswerRequest request) {
        return ResponseEntity.ok(answerService.edit(id, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AnswerDao> getOneLesson(@PathVariable Long id) {
        return ResponseEntity.ok(answerService.get(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        answerService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
