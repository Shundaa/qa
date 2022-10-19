package com.qa.app.service.impl;

import com.qa.app.dao.request.AnswerRequest;
import com.qa.app.dao.response.AnswerDao;
import com.qa.app.entities.Answer;
import com.qa.app.exception.ResourceNotFoundException;
import com.qa.app.repository.AnswerRepository;
import com.qa.app.service.AnswerService;
import com.qa.app.utils.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;

    @Override
    public List<AnswerDao> get() {
        return ObjectMapperUtils.mapAll(answerRepository.findAll(), AnswerDao.class);
    }

    @Override
    public AnswerDao get(Long id) {
        return ObjectMapperUtils.map(findById(id),
                AnswerDao.class);
    }

    @Override
    public void delete(Long id) {
        answerRepository.deleteById(id);
    }

    @Override
    public AnswerDao edit(Long id, AnswerRequest newQuiz) {
        Answer answer = findById(id);
        return ObjectMapperUtils.map(answerRepository.save(answer), AnswerDao.class);
    }

    @Override
    public AnswerDao newAnswer(AnswerRequest newQuiz) {
        Answer answer = ObjectMapperUtils.map(newQuiz, Answer.class);
        return ObjectMapperUtils.map(answerRepository.save(answer), AnswerDao.class);
    }


    private Answer findById(Long id) {
        return answerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Chapter with id: " + id + " Not Found"));
    }
}
