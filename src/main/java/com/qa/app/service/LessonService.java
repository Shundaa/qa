package com.qa.app.service;


import com.qa.app.dao.request.LessonDao;
import com.qa.app.dao.request.NewLessonRequest;
import com.qa.app.dao.request.NewQuizzesToLessonRequest;
import com.qa.app.dao.request.QuizRequest;
import com.qa.app.dao.response.QuizDao;

import java.util.List;

public interface LessonService {
    List<LessonDao> get();

    LessonDao get(Long id);

    void delete(Long id);

    LessonDao edit(Long id, NewLessonRequest newLessonRequest);

    LessonDao newLesson(NewLessonRequest newLessonRequest);

    LessonDao newQuizzesToLesson(Long id, NewQuizzesToLessonRequest newLessonRequest);

    QuizDao createQuizzesToLesson(Long id, QuizRequest request);
}
