package com.qa.app.dao.request;

import com.qa.app.dao.response.QuizDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnIsOpenLessonDao {

    private Long id;
    private String name;

    private String text;
    private List<QuizDao> quizzes;
    private Boolean isOpen = false;
    private Boolean isComplete = false;
    private Boolean isLocked = false;
    private BigDecimal percentage;

}
