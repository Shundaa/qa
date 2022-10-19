package com.qa.app.service.impl;

import com.qa.app.dao.request.LessonDao;
import com.qa.app.dao.request.NewLessonRequest;
import com.qa.app.dao.request.NewQuizzesToLessonRequest;
import com.qa.app.dao.request.QuizRequest;
import com.qa.app.dao.response.QuizDao;
import com.qa.app.entities.Answer;
import com.qa.app.entities.Lesson;
import com.qa.app.entities.Quiz;
import com.qa.app.exception.ResourceNotFoundException;
import com.qa.app.repository.AnswerRepository;
import com.qa.app.repository.LessonRepository;
import com.qa.app.repository.QuizRepository;
import com.qa.app.service.LessonService;
import com.qa.app.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final QuizRepository quizRepository;
    private final AnswerRepository answerRepository;

    @Override
    public List<LessonDao> get() {
        return ObjectMapperUtils.mapAll(lessonRepository.findAll(), LessonDao.class);
    }

    @Override
    public LessonDao get(Long id) {
        return ObjectMapperUtils.map(findById(id),
                LessonDao.class);
    }

    @Override
    public void delete(Long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public LessonDao edit(Long id, NewLessonRequest newLessonRequest) {
        Lesson Lesson = findById(id);
        if (Objects.nonNull(newLessonRequest) && !newLessonRequest.getText().isEmpty()) {
            Lesson.setText(newLessonRequest.getText());
        }
        if (Objects.nonNull(newLessonRequest.getName()) && !newLessonRequest.getName().isEmpty()) {
            Lesson.setName(newLessonRequest.getName());
        }
        return ObjectMapperUtils.map(lessonRepository.save(Lesson), LessonDao.class);
    }

    @Override
    public LessonDao newLesson(NewLessonRequest newLessonRequest) {
        Lesson Lesson = ObjectMapperUtils.map(newLessonRequest, Lesson.class);
        Lesson l = lessonRepository.save(Lesson);
        return ObjectMapperUtils.map(l, LessonDao.class);
    }

    @Override
    public LessonDao newQuizzesToLesson(Long id, NewQuizzesToLessonRequest newLessonRequest) {
        Lesson lesson = findById(id);
        List<Quiz> quizzes = lesson.getQuizzes();
        newLessonRequest.getQuizzesId().forEach(quizId -> quizzes.add(quizRepository.findById(quizId).orElseThrow(
                () -> new ResourceNotFoundException("Chapter with id: " + id + " Not Found"))));
        lesson.setQuizzes(quizzes);
        return ObjectMapperUtils.map(lessonRepository.save(lesson), LessonDao.class);
    }

    @Override
    public QuizDao createQuizzesToLesson(Long id, QuizRequest request) {
        Lesson lesson = findById(id);
        Quiz quiz = Quiz.builder()
                .name(request.getName())
                .text(request.getText())
                .image(request.getImage())
                .lesson(lesson)
                .correctAnswer(request.getCorrectAnswer())
                .build();
        Quiz quizSaved = quizRepository.save(quiz);
        if (quizSaved.getAnswer() == null || quizSaved.getAnswer().isEmpty()) {
            quizSaved.setAnswer(new ArrayList<>());
        }
        request.getAnswerRequests().forEach(answerRequest -> {
            Answer answer = ObjectMapperUtils.map(answerRequest, Answer.class);
            answer.setQuiz(quizSaved);
            quizSaved.getAnswer().add(answerRepository.save(answer));
        });

        lesson.setQuizzes(List.of(quizSaved));
        return ObjectMapperUtils.map(quizSaved, QuizDao.class);
    }

    private Lesson findById(Long id) {
        return lessonRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lesson with id: " + id + " Not Found"));
    }
}
