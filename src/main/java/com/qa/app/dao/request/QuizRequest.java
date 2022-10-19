package com.qa.app.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequest {


    private String name;
    private String text;

    private byte[] image;

    private Long correctAnswer;
    private List<AnswerRequest> answerRequests;
}
