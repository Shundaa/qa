package com.qa.app.controller;

import com.qa.app.dao.request.NewChapterRequest;
import com.qa.app.dao.request.NewChapterToCourseRequest;
import com.qa.app.dao.request.NewCourseRequest;
import com.qa.app.dao.response.CourseDao;
import com.qa.app.dao.response.UserDaoWithoutClassRegistration;
import com.qa.app.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CourseDao>> getAllLessons() {
        return ResponseEntity.ok(courseService.get());
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CourseDao> addNewLesson(@RequestBody NewCourseRequest request) {
        return ResponseEntity.ok(courseService.newLesson(request));
    }

    @PostMapping("/{id}/add/chapter")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CourseDao> addNewLessonToCourse(@PathVariable Long id,
                                                          @RequestBody NewChapterToCourseRequest request) {
        return ResponseEntity.ok(courseService.addNewChapterToCourse(id, request));
    }

    @PostMapping("/{id}/create/chapter")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CourseDao> createNewLessonToCourse(@PathVariable Long id,
                                                             @RequestBody NewChapterRequest request) {
        return ResponseEntity.ok(courseService.createNewChapterToCourse(id, request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CourseDao> addNewLesson(@PathVariable Long id, @RequestBody NewCourseRequest request) {
        return ResponseEntity.ok(courseService.edit(id, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CourseDao> getOneLesson(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.get(id));
    }

    @GetMapping("/{id}/users")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UserDaoWithoutClassRegistration>> getAllUserOfCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getAllUserOfCourse(id));
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CourseDao>> searchCoursess(@RequestParam("name") String name) {
        return ResponseEntity.ok(courseService.searchCourses(name));
    }

    @GetMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CourseDao>> searchCourses(@RequestParam("name") String name) {
        return ResponseEntity.ok(courseService.searchCourses(name));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
