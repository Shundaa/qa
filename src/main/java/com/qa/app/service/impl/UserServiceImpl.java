package com.qa.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.app.dao.request.NewCourseRequest;
import com.qa.app.dao.request.ReturnIsOpenLessonDao;
import com.qa.app.dao.request.UpdatePassword;
import com.qa.app.dao.request.UserRegistrationRequest;
import com.qa.app.dao.response.*;
import com.qa.app.entities.*;
import com.qa.app.exception.ResourceNotFoundException;
import com.qa.app.repository.*;
import com.qa.app.service.UserService;
import com.qa.app.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.qa.app.entities.Role.ADMIN;
import static com.qa.app.entities.Role.USER;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final ClassRegistrationRepository classRegistrationRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final ChapterRepository chapterRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();
        userList.forEach(a -> a.setPassword(a.getPassword()));
        return userList;
    }

    @Override
    public UserDao getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id: " + id + " Not Found"));
        return ObjectMapperUtils.map(user, UserDao.class);
    }

    @Override
    public void deleteUser(Long id) {
        User user = findById(id);
        classRegistrationRepository.deleteAll(user.getRegistrations());
        userRepository.delete(user);
    }

    @Override
    public IsAdmin checkIfAdmin(Long id) {
        User user = findById(id);
        IsAdmin isAdmin = IsAdmin.builder()
                .isAdmin(FALSE)
                .build();
        if (user.getRole() == ADMIN) {
            isAdmin.setIsAdmin(TRUE);
        }
        return isAdmin;
    }

    @Override
    public UserDao elevateUserToAdmin(Long id) {
        User user = findById(id);
        user.setRole(ADMIN);
        return ObjectMapperUtils.map(userRepository.save(user), UserDao.class);
    }

    @Override
    public UserDao elevateUserToUser(Long id) {
        User user = findById(id);
        user.setRole(USER);
        return ObjectMapperUtils.map(userRepository.save(user), UserDao.class);
    }

    @Override
    public UserDao addXp(Long id, Long xp) {
        User user = findById(id);
        Long newXpValue = user.getXp() + xp;
        if (Objects.nonNull(user.getLevel()) && newXpValue > 100L) {
            user.setXp(newXpValue - 100L);
            user.setLevel(user.getLevel() + 1L);
        } else if (Objects.isNull(user.getLevel()) && newXpValue > 100L) {
            user.setLevel(1L);
            user.setXp(newXpValue - 100L);
        } else {
            user.setXp(newXpValue);
        }
        return ObjectMapperUtils.map(userRepository.save(user), UserDao.class);
    }

    @Override
    public IsSubscribedResponse checkSubscription(Long id, Long courseId) {
        User user = findById(id);
        IsSubscribedResponse isSubscribed = IsSubscribedResponse.builder()
                .isSubscribed(true)
                .build();
        List<ClassRegistration> registrationList = user.getRegistrations()
                .stream()
                .filter(classRegistration -> classRegistration.getCourse().getId()
                        .equals(courseId)).toList();

        if (registrationList.isEmpty()) {
            isSubscribed.setIsSubscribed(false);
        }
        return isSubscribed;
    }

    @Override
    public IsOpenCourseDao checkIsOpen(Long id, Long courseId) {
        User user = findById(id);
        List<ClassRegistration> registrationList = user.getRegistrations()
                .stream()
                .filter(classRegistration -> classRegistration.getCourse().getId()
                        .equals(courseId)).toList();
        ClassRegistration classRegistration = registrationList.getFirst();
        IsOpenCourseDao isOpenCourseDao;
        if (!Objects.isNull(classRegistration.getIsOpen()) &&
                !classRegistration.getIsOpen().isBlank()) {
            isOpenCourseDao = getValue(registrationList.getFirst().getIsOpen());
        } else {
            isOpenCourseDao = ObjectMapperUtils
                    .map(registrationList.getFirst().getCourse(), IsOpenCourseDao.class);
        }
        checkNewModifications(isOpenCourseDao);

        try {
            classRegistration.setIsOpen(objectMapper.writeValueAsString(isOpenCourseDao));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        classRegistrationRepository.save(classRegistration);
        return isOpenCourseDao;
    }

    private void checkNewModifications(IsOpenCourseDao isOpenCourseDao) {
        isOpenCourseDao.getChapters().forEach(isOpenChapterDao -> {
            Chapter chapter = chapterRepository.findById(isOpenChapterDao.getId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("chapter with id: " +
                                    isOpenChapterDao.getId() + " Not Found"));
            List<Long> idsLesson = isOpenChapterDao.getLessons().stream().map(IsOpenLessonDao::getId).toList();
            List<Lesson> lessonsNotPresent = chapter.getLessons()
                    .stream()
                    .filter(lesson -> !idsLesson.contains(lesson.getId()))
                    .toList();
            isOpenChapterDao.setLessons(isOpenChapterDao.getLessons().stream()
                    .filter(isOpenLessonDao -> chapter.getLessons()
                            .stream()
                            .map(Lesson::getId)
                            .anyMatch(id -> id.equals(isOpenLessonDao.getId())))
                    .toList());
            if (!lessonsNotPresent.isEmpty()) {
                isOpenChapterDao.getLessons().addAll(ObjectMapperUtils
                        .mapAll(lessonsNotPresent, IsOpenLessonDao.class));
            }
            isOpenChapterDao.getLessons().forEach(isOpenLessonDao -> {

                Lesson lesson = lessonRepository.findById(isOpenLessonDao.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("lesson with id: " +
                                        isOpenLessonDao.getId() + " Not Found"));
                List<Long> idsQuiz = isOpenLessonDao.getQuizzes().stream().map(IsOpenQuizDao::getId).toList();
                List<Quiz> quizzesNotPresent = lesson.getQuizzes()
                        .stream()
                        .filter(quiz -> !idsQuiz.contains(quiz.getId()))
                        .toList();
                isOpenLessonDao.setQuizzes(isOpenLessonDao.getQuizzes().stream()
                        .filter(quiz -> lesson.getQuizzes().stream().map(Quiz::getId)
                                .anyMatch(id -> id.equals(quiz.getId())))
                        .toList());
                if (!quizzesNotPresent.isEmpty()) {
                    isOpenLessonDao.getQuizzes().addAll(ObjectMapperUtils
                            .mapAll(quizzesNotPresent, IsOpenQuizDao.class));
                }
            });
        });
        getUpdatePercentageAndSetLocked(isOpenCourseDao);
    }

    private void getUpdatePercentageAndSetLocked(IsOpenCourseDao isOpenCourseDao) {
        for (int i = 0; i < isOpenCourseDao.getChapters().size(); i++) {
            var isOpenChapterDao = isOpenCourseDao.getChapters().get(i);
            getPercentage(isOpenChapterDao);
            for (int j = 0; j < isOpenChapterDao.getLessons().size(); j++) {
                var isOpenLessonDao = isOpenChapterDao.getLessons().get(j);
                getPercentage(isOpenLessonDao);
                if (j == 0) {
                    isOpenLessonDao.setIsLocked(false);
                } else {
                    isOpenLessonDao.setIsLocked(!isOpenChapterDao.getLessons().get(j - 1).getIsComplete());
                }
            }
            if (i == 0) {
                isOpenChapterDao.setIsLocked(false);
            } else {
                isOpenChapterDao.setIsLocked(!isOpenCourseDao.getChapters().get(i - 1).getIsComplete());
            }
        }
    }

    @Override
    public IsOpenCourseDao changeLessonStatusIsOpen(Long id, Long courseId, ChangeIsOpenDao changeIsOpenDao) {
        User user = findById(id);
        List<ClassRegistration> registrationList = user.getRegistrations()
                .stream()
                .filter(classRegistration -> classRegistration.getCourse().getId()
                        .equals(courseId)).toList();
        IsOpenCourseDao isOpenCourseDao = getValue(registrationList.getFirst().getIsOpen());
        isOpenCourseDao.getChapters().forEach(isOpenChapterDao -> {
            isOpenChapterDao.getLessons().forEach(isOpenLessonDao -> {
                if (isOpenLessonDao.getId().equals(changeIsOpenDao.getId())) {
                    isOpenLessonDao.setIsOpen(changeIsOpenDao.getIsOpen());
                }
            });
        });
        getUpdatePercentageAndSetLocked(isOpenCourseDao);
        try {
            registrationList.getFirst().setIsOpen(objectMapper.writeValueAsString(isOpenCourseDao));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        classRegistrationRepository.saveAll(registrationList);
        return isOpenCourseDao;
    }

    @Override
    public IsOpenCourseDao changeQuizStatusIsOpen(Long id, Long courseId, ChangeIsOpenDao changeIsOpenDao) {
        User user = findById(id);
        List<ClassRegistration> registrationList = user.getRegistrations()
                .stream()
                .filter(classRegistration -> classRegistration.getCourse().getId()
                        .equals(courseId)).toList();
        IsOpenCourseDao isOpenCourseDao = getValue(registrationList.getFirst().getIsOpen());
        isOpenCourseDao.getChapters().forEach(isOpenChapterDao -> {
            isOpenChapterDao.getLessons().forEach(isOpenLessonDao -> {
                isOpenLessonDao.getQuizzes().forEach(isOpenQuizDao -> {
                    if (isOpenQuizDao.getId().equals(changeIsOpenDao.getId())) {
                        isOpenQuizDao.setIsOpen(changeIsOpenDao.getIsOpen());
                    }
                });
            });
        });
        getUpdatePercentageAndSetLocked(isOpenCourseDao);
        try {
            registrationList.getFirst().setIsOpen(objectMapper.writeValueAsString(isOpenCourseDao));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        classRegistrationRepository.saveAll(registrationList);
        return isOpenCourseDao;
    }

    @Override
    public ReturnIsOpenQuizDao getQuiz(Long id, Long courseId, Long quizId) {
        User user = findById(id);
        List<ClassRegistration> registrationList = user.getRegistrations()
                .stream()
                .filter(classRegistration -> classRegistration.getCourse().getId()
                        .equals(courseId)).toList();
        List<IsOpenQuizDao> returnIsOpenQuizDaoList = new ArrayList<>();
        IsOpenCourseDao isOpenCourseDao = getValue(registrationList.getFirst().getIsOpen());
        checkNewModifications(isOpenCourseDao);
        isOpenCourseDao.getChapters().forEach(isOpenChapterDao -> {
            isOpenChapterDao.getLessons().forEach(isOpenLessonDao -> {
                isOpenLessonDao.getQuizzes().forEach(isOpenQuizDao -> {
                    if (isOpenQuizDao.getId().equals(quizId))
                        returnIsOpenQuizDaoList.add(isOpenQuizDao);
                });
            });
        });
        ReturnIsOpenQuizDao returnIsOpenQuizDao = ObjectMapperUtils.map(quizRepository
                .findById(returnIsOpenQuizDaoList.getFirst().getId()), ReturnIsOpenQuizDao.class);
        returnIsOpenQuizDao.setIsOpen(returnIsOpenQuizDaoList.getFirst().getIsOpen());
        return returnIsOpenQuizDao;
    }

    @Override
    public ReturnIsOpenLessonDao getLesson(Long id, Long courseId, Long lessonId) {
        User user = findById(id);
        List<ClassRegistration> registrationList = user.getRegistrations()
                .stream()
                .filter(classRegistration -> classRegistration.getCourse().getId()
                        .equals(courseId)).toList();
        List<IsOpenLessonDao> returnIsOpenLessonDaoList = new ArrayList<>();
        IsOpenCourseDao isOpenCourseDao = getValue(registrationList.getFirst().getIsOpen());
        checkNewModifications(isOpenCourseDao);
        isOpenCourseDao.getChapters().forEach(isOpenChapterDao -> {
            isOpenChapterDao.getLessons().forEach(isOpenLessonDao -> {
                if (isOpenLessonDao.getId().equals(lessonId))
                    returnIsOpenLessonDaoList.add(isOpenLessonDao);
            });
        });
        ReturnIsOpenLessonDao returnIsOpenLessonDao = ObjectMapperUtils.map(lessonRepository
                .findById(returnIsOpenLessonDaoList.getFirst().getId()), ReturnIsOpenLessonDao.class);
        returnIsOpenLessonDao.setIsOpen(returnIsOpenLessonDaoList.getFirst().getIsOpen());
        returnIsOpenLessonDao.setIsLocked(returnIsOpenLessonDaoList.getFirst().getIsLocked());
        returnIsOpenLessonDao.setIsComplete(returnIsOpenLessonDaoList.getFirst().getIsComplete());
        returnIsOpenLessonDao.setPercentage(returnIsOpenLessonDaoList.getFirst().getPercentage());
        return returnIsOpenLessonDao;
    }

    @Override
    public void deleteIsOpen(Long id, Long courseId) {
        User user = findById(id);
        List<ClassRegistration> registrationList = user.getRegistrations()
                .stream()
                .filter(classRegistration -> classRegistration
                        .getCourse().getId().equals(courseId))
                .toList();
        registrationList.forEach(classRegistration -> classRegistration.setIsOpen(null));
        classRegistrationRepository.saveAll(registrationList);
    }

    @Override
    public List<CourseDaoWithoutChapter> getAllCourseOwnership(Long id) {
        return ObjectMapperUtils.mapAll(courseRepository.findCoursesByOwner(id), CourseDaoWithoutChapter.class);
    }

    @Override
    public IsOwnerResponse checkCourseOwnershipByCourse(Long owner, Long courseId) {
        List<Course> courses = courseRepository.findCoursesByIdAndOwner(courseId, owner);
        IsOwnerResponse isOwnerResponse = IsOwnerResponse.builder()
                .isOwner(true)
                .build();
        if (courses != null && courses.isEmpty()) {
            isOwnerResponse.setIsOwner(false);
        }
        return isOwnerResponse;
    }

    @Override
    public User addRegistration(Long id, UserRegistrationRequest userRegistrationRequest) {
        User user = findById(id);
        List<ClassRegistration> classRegistrations = user.getRegistrations();
        Course course = courseRepository.findById(userRegistrationRequest.getCourseId()).orElseThrow(
                () -> new ResourceNotFoundException("Course with id: " + userRegistrationRequest.getCourseId() + " Not Found"));
        ClassRegistration classRegistration = new ClassRegistration();
        classRegistration.setCourse(course);
        classRegistration.setRegisteredAt(LocalDateTime.now());
        classRegistration.setUser(user);
        classRegistrations.add(classRegistration);
        user.setRegistrations(classRegistrations);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User deleteCourse(Long id, UserRegistrationRequest course) {
        User user = findById(id);
        List<ClassRegistration> newList = user.getRegistrations()
                .stream().filter(classRegistration -> classRegistration.getCourse().getId()
                        .equals(course.getCourseId())).toList();
        classRegistrationRepository.deleteAll(newList);
        user.getRegistrations().removeAll(newList);
        return userRepository.save(user);
    }

    @Override
    public User updatePassword(Long id, UpdatePassword password) {
        User user = findById(id);
        user.setPassword(passwordEncoder.encode(password.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateEmail(Long id, UpdatePassword password) {
        User user = findById(id);
        user.setEmail(password.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User updateName(Long id, UpdatePassword password) {
        User user = findById(id);
        user.setFirstName(password.getFirstName());
        user.setLastName(password.getLastName());
        return userRepository.save(user);
    }

    @Override
    public User updateAll(Long id, UpdatePassword password) {
        User user = findById(id);
        if (password.getPassword() != null)
            user.setPassword(passwordEncoder.encode(password.getPassword()));
        if (password.getFirstName() != null)
            user.setFirstName(password.getFirstName());
        if (password.getLastName() != null)
            user.setLastName(password.getLastName());
        if (password.getEmail() != null)
            user.setEmail(password.getEmail());
        return userRepository.save(user);
    }

    @Override
    public UserDao createRegistration(Long id, NewCourseRequest request) {
        User user = findById(id);
        Course course = Course.builder()
                .name(request.getName())
                .owner(request.getOwner())
                .ownerName(user.getFirstName())
                .ownerLastName(user.getLastName())
                .description(request.getDescription())
                .build();

        Course courseSaved = courseRepository.save(course);
        List<ClassRegistration> classRegistrations = user.getRegistrations();
        ClassRegistration classRegistration = ClassRegistration.builder()
                .user(user)
                .course(courseSaved)
                .registeredAt(LocalDateTime.now())
                .build();
        classRegistrations.add(classRegistration);
        user.setRegistrations(classRegistrations);
        return ObjectMapperUtils.map(userRepository.save(user), UserDao.class);
    }

    @Override
    public List<ClassRegistrationResponse> getAllCoursesOfUser(Long id) {
        User user = findById(id);
        List<ClassRegistrationResponse> classRegistrationResponses = new ArrayList<>();
        List<ClassRegistration> classRegistrations = user.getRegistrations();
        classRegistrations.forEach(classRegistration -> {
            //String isOpen = classRegistration.getIsOpen();
            ClassRegistrationResponse classRegistrationResponse = ObjectMapperUtils
                    .map(classRegistration, ClassRegistrationResponse.class);
//            if (isOpen != null && isOpen.isBlank()) {
//                IsOpenCourseDao isOpenCourseDao = getValue(isOpen);
//                classRegistrationResponse.setIsOpenCourseDao(isOpenCourseDao);
//            }
            classRegistrationResponses.add(classRegistrationResponse);
        });
        return classRegistrationResponses;
    }

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id: " + id + " Not Found"));
    }

    private IsOpenCourseDao getValue(String isOpen) {
        try {
            return objectMapper.readValue(isOpen, IsOpenCourseDao.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void getPercentage(IsOpenLessonDao isOpen) {
        var isOpened = isOpen.getQuizzes().stream()
                .filter(IsOpenQuizDao::getIsOpen)
                .toList();

        isOpen.setIsComplete(isOpen.getIsOpen());
        if (isOpen.getQuizzes().isEmpty() && isOpen.getIsOpen()) {
            isOpen.setPercentage(BigDecimal.valueOf(100));
        } else if (isOpen.getQuizzes().isEmpty()) {
            isOpen.setPercentage(BigDecimal.valueOf(0));
        } else {
            isOpen.setPercentage(BigDecimal.valueOf(100 * (double) isOpened.size() / isOpen.getQuizzes().size()));
            isOpen.setIsComplete(isOpened.size() == isOpen.getQuizzes().size());
        }
    }

    private void getPercentage(IsOpenChapterDao isOpen) {
        var isOpened = isOpen.getLessons().stream()
                .filter(IsOpenLessonDao::getIsOpen)
                .toList();
        isOpen.setPercentage(BigDecimal.valueOf(100 * (double) isOpened.size() / isOpen.getLessons().size()));
        isOpen.setIsComplete(isOpened.size() == isOpen.getLessons().size());
    }
}
