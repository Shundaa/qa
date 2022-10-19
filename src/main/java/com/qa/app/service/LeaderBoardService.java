package com.qa.app.service;

import com.qa.app.dao.response.LeaderDao;

import java.util.List;

public interface LeaderBoardService {
    List<LeaderDao> getTop5();

    List<LeaderDao> getTop5ByCourse(Long courseId);
}
