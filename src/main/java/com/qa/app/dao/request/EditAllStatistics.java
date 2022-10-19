package com.qa.app.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditAllStatistics {

    private Long bug;
    private Long coin;
    private Long refill;
    private Long victory;

}
