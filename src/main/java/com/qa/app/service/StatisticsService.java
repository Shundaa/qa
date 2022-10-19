package com.qa.app.service;

import com.qa.app.dao.request.EditAllStatistics;
import com.qa.app.dao.response.UserDao;

public interface StatisticsService {
    UserDao addCoin(Long id);

    UserDao addRefill(Long id);

    UserDao addVictory(Long id);

    UserDao removeCoin(Long id);

    UserDao removeRefill(Long id);

    UserDao removeVictory(Long id);

    UserDao getStatistics(Long id);

    UserDao editAllStatistics(Long id, EditAllStatistics newStatistics);

    UserDao addBug(Long id);

    UserDao removeBug(Long id);
}
