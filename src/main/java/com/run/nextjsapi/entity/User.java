package com.run.nextjsapi.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/11/16
 */
@Data
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    // update 时允许把 age 设为 null
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer age;

    private String gender;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String phone;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer teamId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date createTime;

}
