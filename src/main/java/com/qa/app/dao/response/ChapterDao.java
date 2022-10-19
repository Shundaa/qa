package com.qa.app.dao.response;

import com.qa.app.dao.request.LessonDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChapterDao {
    private Long id;
    private String name;
    private List<LessonDao> lessons;
}
