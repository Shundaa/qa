package com.qa.app.service;

import com.qa.app.dao.request.NewCourseRequest;
import com.qa.app.dao.request.ReturnIsOpenLessonDao;
import com.qa.app.dao.request.UpdatePassword;
import com.qa.app.dao.request.UserRegistrationRequest;
import com.qa.app.dao.response.*;
import com.qa.app.entities.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    IsAdmin checkIfAdmin(Long id);

    UserDao elevateUserToAdmin(Long id);

    UserDao elevateUserToUser(Long id);

    UserDao getUser(Long id);

    void deleteUser(Long id);

    UserDao addXp(Long id, Long xp);

    IsSubscribedResponse checkSubscription(Long id, Long courseId);

    IsOpenCourseDao checkIsOpen(Long id, Long courseId);

    IsOpenCourseDao changeLessonStatusIsOpen(Long id, Long courseId, ChangeIsOpenDao changeIsOpenDao);

    IsOpenCourseDao changeQuizStatusIsOpen(Long id, Long courseId, ChangeIsOpenDao changeIsOpenDao);

    ReturnIsOpenQuizDao getQuiz(Long id, Long courseId, Long quizId);

    ReturnIsOpenLessonDao getLesson(Long id, Long courseId, Long quizId);

    void deleteIsOpen(Long id, Long courseId);

    List<CourseDaoWithoutChapter> getAllCourseOwnership(Long id);

    IsOwnerResponse checkCourseOwnershipByCourse(Long id, Long courseId);

    User addRegistration(Long id, UserRegistrationRequest userRegistrationRequest);

    User updateUser(User user);

    User deleteCourse(Long id, UserRegistrationRequest courseId);

    User updatePassword(Long id, UpdatePassword user);

    User updateEmail(Long id, UpdatePassword user);

    User updateName(Long id, UpdatePassword user);

    User updateAll(Long id, UpdatePassword user);

    UserDao createRegistration(Long id, NewCourseRequest request);

    List<ClassRegistrationResponse> getAllCoursesOfUser(Long id);
}
