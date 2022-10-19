package com.qa.app.controller;

import com.qa.app.dao.request.LessonDao;
import com.qa.app.dao.request.NewLessonRequest;
import com.qa.app.dao.request.NewQuizzesToLessonRequest;
import com.qa.app.dao.request.QuizRequest;
import com.qa.app.dao.response.QuizDao;
import com.qa.app.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<LessonDao>> getAllLessons() {
        return ResponseEntity.ok(lessonService.get());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<LessonDao> addNewLesson(@RequestBody NewLessonRequest request) {
        return ResponseEntity.ok(lessonService.newLesson(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<LessonDao> addNewLesson(@PathVariable Long id, @RequestBody NewLessonRequest request) {
        return ResponseEntity.ok(lessonService.edit(id, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<LessonDao> getOneLesson(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.get(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        lessonService.delete(id);
        return ResponseEntity.ok("Deleted");
    }

    @PostMapping("/{id}/add/quizzes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<LessonDao> addNewLesson(@PathVariable Long id,
                                                  @RequestBody NewQuizzesToLessonRequest request) {
        return ResponseEntity.ok(lessonService.newQuizzesToLesson(id, request));
    }

    @PostMapping("/{id}/create/quizzes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<QuizDao> createNewLesson(@PathVariable Long id,
                                                   @RequestBody QuizRequest request) {
        return ResponseEntity.ok(lessonService.createQuizzesToLesson(id, request));
    }
}
