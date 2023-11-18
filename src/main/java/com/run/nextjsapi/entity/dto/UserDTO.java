package com.run.nextjsapi.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.run.nextjsapi.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/11/17
 */
@Data
public class UserDTO {
    private Long id;

    private String name;

    private Integer age;

    private String gender;

    private String phone;

    private Integer teamId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date createTime;

    private String team;
}
