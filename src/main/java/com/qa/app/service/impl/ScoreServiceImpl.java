package com.qa.app.service.impl;

import com.qa.app.dao.response.LeaderDao;
import com.qa.app.entities.User;
import com.qa.app.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private final ModelMapper modelMapper;

    @Override
    public LeaderDao score(User user) {
//        return addOneScoreToUser(user);
        return null;
    }

    @Override
    public LeaderDao score(String email) {
//        Optional<User> userOptional = userRepository.findByEmail(email);
//        if (userOptional.isEmpty()) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        User user = userOptional.get();
//        return addOneScoreToUser(user);
        return null;
    }

    private LeaderDao addOneScoreToUser(User user) {
//        user.setScore(user.getScore().add(BigDecimal.ONE));
//        return modelMapper.map(userRepository.save(user), LeaderDao.class);
        return null;
    }
}
