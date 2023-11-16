package com.run.nextjsapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.run.nextjsapi.common.Result;
import com.run.nextjsapi.common.ResultCode;
import com.run.nextjsapi.entity.User;
import com.run.nextjsapi.service.UserService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/11/16
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @RequestMapping(path = "/createOrUpdate", method = RequestMethod.POST)
    public Result createOrUpdate(@RequestBody User user) {
        boolean isSave = userService.saveOrUpdate(user);
        if (isSave) {
            return Result.ok();
        } else {
            logger.error("createOrUpdate error");
            return Result.build(ResultCode.DATABASE_ERROR);
        }
    }

    @RequestMapping(path = "/getUserList", method = RequestMethod.GET)
    public Result getUserList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");

        Page<User> userPage = new Page<>(page, pageSize, true);
        try {
            Page<User> pageResult = userService.page(userPage, wrapper);
            List<User> userList = pageResult.getRecords();
            long total = pageResult.getTotal();
            return Result.ok()
                    .appendData("userList", userList)
                    .appendData("total", total);
        } catch (Exception e) {
            logger.error("getUserList error: {}", e.getMessage());
            return Result.error().message(e.getMessage());
        }
    }

    @RequestMapping(path = "/deleteUserById/{id}", method = RequestMethod.DELETE)
    public Boolean deleteUserById(@PathVariable Long id) {
        return userService.removeById(id);
    }

}
