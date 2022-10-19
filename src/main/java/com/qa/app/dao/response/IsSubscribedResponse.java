package com.qa.app.dao.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IsSubscribedResponse {

    private Boolean isSubscribed;
    private List<IsOpenChapterDao> chapters;
}
