package com.qa.app.service.impl;

import com.qa.app.dao.request.AnswerRequest;
import com.qa.app.dao.request.NewAnswersToQuizRequest;
import com.qa.app.dao.request.QuizRequest;
import com.qa.app.dao.response.QuizDao;
import com.qa.app.entities.Answer;
import com.qa.app.entities.Quiz;
import com.qa.app.exception.ResourceNotFoundException;
import com.qa.app.repository.AnswerRepository;
import com.qa.app.repository.QuizRepository;
import com.qa.app.service.QuizService;
import com.qa.app.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final AnswerRepository answerRepository;

    @Override
    public List<QuizDao> get() {
        return ObjectMapperUtils.mapAll(quizRepository.findAll(), QuizDao.class);
    }

    @Override
    public QuizDao get(Long id) {
        return ObjectMapperUtils.map(findById(id),
                QuizDao.class);
    }

    @Override
    public void delete(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    public QuizDao edit(Long id, QuizRequest request) {
        Quiz quiz = findById(id);
        if (Objects.nonNull(request.getName()) && !request.getName().isEmpty()) {
            quiz.setName(request.getName());
        }
        if (Objects.nonNull(request.getText()) && !request.getText().isEmpty()) {
            quiz.setText(request.getText());
        }
        if (Objects.nonNull(request.getImage()) && request.getImage().length > 0) {
            quiz.setImage(request.getImage());
        }
        if (Objects.nonNull(request.getCorrectAnswer())) {
            quiz.setCorrectAnswer(request.getCorrectAnswer());
        }
        if (Objects.nonNull(request.getAnswerRequests()) && !request.getAnswerRequests().isEmpty()) {
            if (Objects.nonNull(quiz.getAnswer()) && !quiz.getAnswer().isEmpty()) {
                quiz.getAnswer().clear();
            }
            List<Answer> newAnswers = ObjectMapperUtils.mapAll(request.getAnswerRequests(), Answer.class);
            newAnswers.forEach(answer -> answer.setQuiz(quiz));
            quiz.getAnswer().addAll(newAnswers);
        }

        return ObjectMapperUtils.map(quizRepository.saveAndFlush(quiz), QuizDao.class);
    }

    @Override
    public QuizDao newLesson(QuizRequest newQuiz) {
        Quiz quiz = ObjectMapperUtils.map(newQuiz, Quiz.class);
        return ObjectMapperUtils.map(quizRepository.save(quiz), QuizDao.class);
    }

    @Override
    public QuizDao addNewAnswersToQuiz(Long id, NewAnswersToQuizRequest request) {
        Quiz quiz = findById(id);
        List<Answer> answers = quiz.getAnswer();
        request.getAnswersId().forEach(answerId -> {
            Answer answer = answerRepository.findById(answerId).orElseThrow(
                    () -> new ResourceNotFoundException("answer with id: " + answerId + " Not Found"));
            answer.setQuiz(quiz);
            answers.add(answer);
        });
        return ObjectMapperUtils.map(quizRepository.save(quiz), QuizDao.class);
    }

    @Override
    public QuizDao createNewAnswersToQuiz(Long id, AnswerRequest request) {
        Quiz quiz = findById(id);
        List<Answer> answers = quiz.getAnswer();
        Answer answer = Answer.builder()
                .text(request.getText())
                .build();
        answer.setQuiz(quiz);
        answers.add(answer);
        quiz.setAnswer(answers);
        return ObjectMapperUtils.map(quizRepository.save(quiz), QuizDao.class);
    }

    private Quiz findById(Long id) {
        return quizRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Chapter with id: " + id + " Not Found"));
    }
}
