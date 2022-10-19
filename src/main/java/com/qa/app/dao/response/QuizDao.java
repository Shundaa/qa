package com.qa.app.dao.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizDao {

    private Long id;

    private String name;
    private String text;

    private byte[] image;

    private Long correctAnswer;

    private List<AnswerDao> answer;
}
