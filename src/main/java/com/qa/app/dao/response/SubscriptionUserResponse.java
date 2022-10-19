package com.qa.app.dao.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionUserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private List<ClassRegistrationDao> registrations = new ArrayList<>();
}
