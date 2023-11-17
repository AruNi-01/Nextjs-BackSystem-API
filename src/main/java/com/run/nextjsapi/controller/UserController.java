package com.run.nextjsapi.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.run.nextjsapi.common.Result;
import com.run.nextjsapi.common.ResultCode;
import com.run.nextjsapi.entity.Team;
import com.run.nextjsapi.entity.User;
import com.run.nextjsapi.entity.params.UserFilterParams;
import com.run.nextjsapi.service.TeamService;
import com.run.nextjsapi.service.UserService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @desc: TODO: 查询出 Team
 * @author: AruNi_Lu
 * @date: 2023/11/16
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private TeamService teamService;

    @RequestMapping(path = "/createOrUpdate", method = RequestMethod.POST)
    public Boolean createOrUpdate(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    @RequestMapping(path = "/getUserList", method = RequestMethod.GET)
    public Result getUserList(@RequestParam("jsonParams") String jsonString) {
        /*
          jsonParams: {
            "pagination": {
              "current": 1,
              "pageSize": 10
            },
            "filters": {
              "age": [
                "20"
              ],
              "gender": [
                "male"
              ]
            },
            "order": "ascend",
            "field": "name",
            “searchUsername": "AruNi_Lu"
          }
         */
        logger.info("getUserList jsonParams: {}", jsonString);
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        UserFilterParams userFilterParams = JSON.parseObject(jsonString, UserFilterParams.class);
        int current = userFilterParams.getPagination().getCurrent();
        int pageSize = userFilterParams.getPagination().getPageSize();
        if (userFilterParams.getField() != null && userFilterParams.getOrder() != null) {
            wrapper.orderBy(true, userFilterParams.getOrder().equals("ascend"), userFilterParams.getField());
        }
        wrapper.orderByDesc("create_time");

        if (userFilterParams.getFilters() != null) {
            if (userFilterParams.getFilters().getGender() != null) {
                wrapper.in("gender", userFilterParams.getFilters().getGender());
            }
            List<String> teams = userFilterParams.getFilters().getTeam();
            if (teams != null) {
                List<Integer> teamIds = teamService.list(new QueryWrapper<Team>()
                                .in("team_name", teams))
                        .stream()
                        .map(Team::getId)
                        .toList();
                wrapper.in(!teamIds.isEmpty(), "team_id", teamIds);
            }
        }

        // 搜索参数
        if (userFilterParams.getSearchUsername() != null) {
            wrapper.like("name", userFilterParams.getSearchUsername());
        }

        Page<User> userPage = new Page<>(current, pageSize, true);
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
