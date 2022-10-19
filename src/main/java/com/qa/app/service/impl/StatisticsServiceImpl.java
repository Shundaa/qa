package com.qa.app.service.impl;

import com.qa.app.dao.request.EditAllStatistics;
import com.qa.app.dao.response.UserDao;
import com.qa.app.entities.User;
import com.qa.app.repository.UserRepository;
import com.qa.app.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public UserDao getStatistics(Long id) {
        return modelMapper.map(findById(id), UserDao.class);
    }

    @Override
    public UserDao editAllStatistics(Long id, EditAllStatistics newStatistics) {
        User user = findById(id);
        if (Objects.nonNull(newStatistics.getCoin()))
            user.setCoin(newStatistics.getCoin());
        if (Objects.nonNull(newStatistics.getBug()))
            user.setBug(newStatistics.getBug());
        if (Objects.nonNull(newStatistics.getVictory()))
            user.setVictory(newStatistics.getVictory());
        if (Objects.nonNull(newStatistics.getRefill()))
            user.setRefill(newStatistics.getRefill());
        return modelMapper.map(userRepository.save(user), UserDao.class);
    }

    @Override
    public UserDao addCoin(Long id) {
        User user = findById(id);
        user.setCoin(user.getCoin() + 1);
        return modelMapper.map(userRepository.save(user), UserDao.class);
    }

    @Override
    public UserDao addRefill(Long id) {
        User user = findById(id);
        user.setRefill(user.getRefill() + 1);
        return modelMapper.map(userRepository.save(user), UserDao.class);
    }

    @Override
    public UserDao addVictory(Long id) {
        User user = findById(id);
        user.setVictory(user.getVictory() + 1);
        return modelMapper.map(userRepository.save(user), UserDao.class);
    }

    @Override
    public UserDao removeCoin(Long id) {
        User user = findById(id);
        if (user.getCoin() > 0)
            user.setCoin(user.getCoin() + -1);
        return modelMapper.map(userRepository.save(user), UserDao.class);
    }

    @Override
    public UserDao removeRefill(Long id) {
        User user = findById(id);
        if (user.getRefill() > 0)
            user.setRefill(user.getRefill() + -1);
        return modelMapper.map(userRepository.save(user), UserDao.class);
    }

    @Override
    public UserDao removeVictory(Long id) {
        User user = findById(id);
        if (user.getVictory() > 0)
            user.setVictory(user.getVictory() - 1);
        return modelMapper.map(userRepository.save(user), UserDao.class);
    }

    @Override
    public UserDao addBug(Long id) {
        User user = findById(id);
        user.setBug(user.getBug() + 1);
        return modelMapper.map(userRepository.save(user), UserDao.class);
    }

    @Override
    public UserDao removeBug(Long id) {
        User user = findById(id);
        if (user.getBug() > 0)
            user.setBug(user.getBug() - 1);
        return modelMapper.map(userRepository.save(user), UserDao.class);
    }


    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with Id: " + id + " not found"));
    }
}
