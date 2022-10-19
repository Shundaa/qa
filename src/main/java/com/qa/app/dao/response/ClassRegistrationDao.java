package com.qa.app.dao.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassRegistrationDao {

    private Long id;
    private String name;
    private String owner;
    private CourseDao course;
}
