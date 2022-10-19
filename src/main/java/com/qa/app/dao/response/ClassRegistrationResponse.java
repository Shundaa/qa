package com.qa.app.dao.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassRegistrationResponse {

    private Long id;
    private CourseDaoResponse course;
    private LocalDateTime registeredAt;
    private Long grade;
}
