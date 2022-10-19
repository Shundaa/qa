package com.qa.app.controller;

import com.qa.app.dao.request.NewCourseRequest;
import com.qa.app.dao.request.ReturnIsOpenLessonDao;
import com.qa.app.dao.request.UpdatePassword;
import com.qa.app.dao.request.UserRegistrationRequest;
import com.qa.app.dao.response.*;
import com.qa.app.entities.User;
import com.qa.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> getAllUsers(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @GetMapping("/{id}/subscriptions/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClassRegistrationResponse>> getAllCoursesOfUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getAllCoursesOfUser(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDao> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Deleted");
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> changePassword(@PathVariable Long id, @RequestBody UpdatePassword changePassword) {
        return ResponseEntity.ok(userService.updatePassword(id, changePassword));
    }

    @PutMapping("/{id}/name")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> changeName(@PathVariable Long id, @RequestBody UpdatePassword changePassword) {
        return ResponseEntity.ok(userService.updateName(id, changePassword));
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> changeAll(@PathVariable Long id, @RequestBody UpdatePassword changePassword) {
        return ResponseEntity.ok(userService.updateAll(id, changePassword));
    }

    @PutMapping("/{id}/email")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> changeEmail(@PathVariable Long id, @RequestBody UpdatePassword changePassword) {
        return ResponseEntity.ok(userService.updateEmail(id, changePassword));
    }

    @PostMapping("/{id}/course/subscribe")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> addNewCourse(@PathVariable Long id, @RequestBody UserRegistrationRequest userRegistrationRequest) {
        return ResponseEntity.ok(userService.addRegistration(id, userRegistrationRequest));
    }

    @GetMapping("/{id}/admin")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<IsAdmin> checkAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(userService.checkIfAdmin(id));
    }

    @PutMapping("/{id}/promote/admin")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDao> elevateAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(userService.elevateUserToAdmin(id));
    }

    @PutMapping("/{id}/promote/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDao> elevateUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.elevateUserToUser(id));
    }

    @PostMapping("/{id}/course/unSubscribe")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> removeNewCourse(@PathVariable Long id, @RequestBody UserRegistrationRequest userRegistrationRequest) {
        return ResponseEntity.ok(userService.deleteCourse(id, userRegistrationRequest));
    }

    @GetMapping("/{id}/course/{courseId}/subscription")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<IsSubscribedResponse> checkSubscription(@PathVariable Long id, @PathVariable Long courseId) {

        return ResponseEntity.ok(userService.checkSubscription(id, courseId));
    }

    @GetMapping("/{id}/course/{courseId}/isOpen")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<IsOpenCourseDao> checkIsOpen(@PathVariable Long id, @PathVariable Long courseId) {
        return ResponseEntity.ok(userService.checkIsOpen(id, courseId));
    }

    @PutMapping("/{id}/course/{courseId}/isOpen/lesson")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<IsOpenCourseDao> changeLessonStatusIsOpen(@PathVariable Long id, @PathVariable Long courseId,
                                                                    @RequestBody ChangeIsOpenDao changeIsOpenLessonDao) {
        return ResponseEntity.ok(userService.changeLessonStatusIsOpen(id, courseId, changeIsOpenLessonDao));
    }

    @PutMapping("/{id}/course/{courseId}/isOpen/quiz")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<IsOpenCourseDao> changeQuizStatusIsOpen(@PathVariable Long id, @PathVariable Long courseId,
                                                                  @RequestBody ChangeIsOpenDao changeIsOpenLessonDao) {
        return ResponseEntity.ok(userService.changeQuizStatusIsOpen(id, courseId, changeIsOpenLessonDao));
    }


    @GetMapping("/{id}/course/{courseId}/quiz/{quizId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReturnIsOpenQuizDao> changeQuizStatusIsOpen(@PathVariable Long id, @PathVariable Long courseId,
                                                                      @PathVariable Long quizId) {
        return ResponseEntity.ok(userService.getQuiz(id, courseId, quizId));
    }

    @GetMapping("/{id}/course/{courseId}/lesson/{lessonId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReturnIsOpenLessonDao> changeLessonStatusIsOpen(@PathVariable Long id,
                                                                          @PathVariable Long courseId,
                                                                          @PathVariable Long lessonId) {
        return ResponseEntity.ok(userService.getLesson(id, courseId, lessonId));
    }

    @DeleteMapping("/{id}/course/{courseId}/isOpen")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteIsOpen(@PathVariable Long id, @PathVariable Long courseId) {
        userService.deleteIsOpen(id, courseId);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/{id}/course/ownership")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CourseDaoWithoutChapter>> checkOwnership(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getAllCourseOwnership(id));
    }

    @GetMapping("/{id}/course/{courseId}/ownership")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<IsOwnerResponse> checkOwnership(@PathVariable Long id,
                                                          @PathVariable Long courseId) {
        return ResponseEntity.ok(userService.checkCourseOwnershipByCourse(id, courseId));
    }

    @PostMapping("/{id}/course/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDao> createNewCourse(@PathVariable Long id, @RequestBody NewCourseRequest request) {
        return ResponseEntity.ok(userService.createRegistration(id, request));
    }

    @GetMapping("/{id}/add/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDao> addXp(@PathVariable Long id, @RequestParam("xp") Long xp) {
        return ResponseEntity.ok(userService.addXp(id, xp));
    }


}
