package com.qa.app.controller;

import com.qa.app.dao.request.AnswerRequest;
import com.qa.app.dao.request.NewAnswersToQuizRequest;
import com.qa.app.dao.request.QuizRequest;
import com.qa.app.dao.response.QuizDao;
import com.qa.app.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<QuizDao>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.get());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<QuizDao> addNewQuizzes(@RequestBody QuizRequest request) {
        return ResponseEntity.ok(quizService.newLesson(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<QuizDao> addNewQuizzes(@PathVariable Long id, @RequestBody QuizRequest request) {
        return ResponseEntity.ok(quizService.edit(id, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<QuizDao> getOneQuiz(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.get(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        quizService.delete(id);
        return ResponseEntity.ok("Deleted");
    }

    @PostMapping("/{id}/add/answer")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<QuizDao> addNewAnswer(@PathVariable Long id,
                                                @RequestBody NewAnswersToQuizRequest request) {
        return ResponseEntity.ok(quizService.addNewAnswersToQuiz(id, request));
    }

    @PostMapping("/{id}/create/answer")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<QuizDao> createNewAnswer(@PathVariable Long id,
                                                   @RequestBody AnswerRequest request) {
        return ResponseEntity.ok(quizService.createNewAnswersToQuiz(id, request));
    }
}
