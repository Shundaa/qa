package com.qa.app.controller;

import com.qa.app.dao.request.NewChapterRequest;
import com.qa.app.dao.request.NewLessonRequest;
import com.qa.app.dao.request.NewLessonsToChapterRequest;
import com.qa.app.dao.response.ChapterDao;
import com.qa.app.entities.Lesson;
import com.qa.app.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chapter")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ChapterDao>> getAllLessons() {
        return ResponseEntity.ok(chapterService.get());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ChapterDao> addNewLesson(@RequestBody NewChapterRequest request) {
        return ResponseEntity.ok(chapterService.newChapter(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ChapterDao> addNewLesson(@PathVariable Long id, @RequestBody NewChapterRequest request) {
        return ResponseEntity.ok(chapterService.edit(id, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ChapterDao> getOneLesson(@PathVariable Long id) {
        return ResponseEntity.ok(chapterService.get(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        chapterService.delete(id);
        return ResponseEntity.ok("Deleted");
    }

    @PostMapping("/{id}/add/lessons")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ChapterDao> addNewLesson(@PathVariable Long id,
                                                   @RequestBody NewLessonsToChapterRequest request) {
        return ResponseEntity.ok(chapterService.addNewLessonsToChapter(id, request));
    }

    @PostMapping("/{id}/create/lesson")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Lesson> createNewLesson(@PathVariable Long id,
                                                  @RequestBody NewLessonRequest request) {
        return ResponseEntity.ok(chapterService.createNewLessonsToChapter(id, request));
    }
}
