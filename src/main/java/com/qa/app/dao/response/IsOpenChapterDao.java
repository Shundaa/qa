package com.qa.app.dao.response;

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
public class IsOpenChapterDao {
    private Long id;
    private String name;
    private Boolean isComplete = false;
    private Boolean isLocked = false;
    private BigDecimal percentage;
    private List<IsOpenLessonDao> lessons;
}
