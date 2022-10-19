package com.qa.app.service;

import com.qa.app.dao.request.AnswerRequest;
import com.qa.app.dao.request.NewAnswersToQuizRequest;
import com.qa.app.dao.request.QuizRequest;
import com.qa.app.dao.response.QuizDao;

import java.util.List;

public interface QuizService {
    List<QuizDao> get();

    QuizDao get(Long id);

    void delete(Long id);

    QuizDao edit(Long id, QuizRequest newQuiz);

    QuizDao newLesson(QuizRequest newQuiz);

    QuizDao addNewAnswersToQuiz(Long id, NewAnswersToQuizRequest request);

    QuizDao createNewAnswersToQuiz(Long id, AnswerRequest request);
}
