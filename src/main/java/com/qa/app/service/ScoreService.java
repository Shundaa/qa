package com.qa.app.service;

import com.qa.app.dao.response.LeaderDao;
import com.qa.app.entities.User;

public interface ScoreService {
    LeaderDao score(User user);

    LeaderDao score(String email);
}
