package com.run.nextjsapi.entity.params;

import lombok.Data;
import java.util.List;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/11/17
 */
@Data
public class UserFilterParams {
    private Pagination pagination;
    private Filters filters;
    private String field;
    private String order;
    private String searchUsername;

    @Data
    public static class Pagination {
        private int current;
        private int pageSize;
    }

    @Data
    public static class Filters {
        private List<String> name;
        private List<Integer> age;
        private List<String> gender;
        private List<String> phone;
        private List<String> team;
    }
}
