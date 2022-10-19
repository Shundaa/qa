package com.qa.app.service;

import com.qa.app.dao.request.AnswerRequest;
import com.qa.app.dao.response.AnswerDao;

import java.util.List;

public interface AnswerService {
    List<AnswerDao> get();

    AnswerDao get(Long id);

    void delete(Long id);

    AnswerDao edit(Long id, AnswerRequest newAnswer);

    AnswerDao newAnswer(AnswerRequest newAnswer);
}
