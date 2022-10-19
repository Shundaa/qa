package com.qa.app.service;

import com.qa.app.dao.request.NewChapterRequest;
import com.qa.app.dao.request.NewChapterToCourseRequest;
import com.qa.app.dao.request.NewCourseRequest;
import com.qa.app.dao.response.CourseDao;
import com.qa.app.dao.response.UserDaoWithoutClassRegistration;

import java.util.List;

public interface CourseService {
    List<CourseDao> get();

    CourseDao get(Long id);

    List<UserDaoWithoutClassRegistration> getAllUserOfCourse(Long id);

    void delete(Long id);

    CourseDao edit(Long id, NewCourseRequest newLessonRequest);

    CourseDao newLesson(NewCourseRequest newLessonRequest);

    CourseDao addNewChapterToCourse(Long id, NewChapterToCourseRequest addNew);

    CourseDao createNewChapterToCourse(Long id, NewChapterRequest createNew);

    List<CourseDao> searchCourses(String name);
}
