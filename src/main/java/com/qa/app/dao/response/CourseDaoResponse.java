package com.qa.app.dao.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDaoResponse {

    private Long id;
    private String name;
    private Long owner;
    private String ownerName;
    private String description;
    private String ownerLastName;
}
