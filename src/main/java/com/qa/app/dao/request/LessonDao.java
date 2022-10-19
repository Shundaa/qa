package com.qa.app.dao.request;

import com.qa.app.dao.response.QuizDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDao {

    private Long id;
    private String name;

    private String text;
    private List<QuizDao> quizzes;

}
