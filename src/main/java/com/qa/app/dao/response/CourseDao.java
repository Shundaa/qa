package com.qa.app.dao.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDao {

    private Long id;
    private String name;
    private Long owner;
    private String ownerName;
    private String ownerLastName;
    private String description;
    private List<ChapterDao> chapters = new ArrayList<>();
}
