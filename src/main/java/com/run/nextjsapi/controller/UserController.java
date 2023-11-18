package com.run.nextjsapi.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.run.nextjsapi.common.Result;
import com.run.nextjsapi.entity.Team;
import com.run.nextjsapi.entity.User;
import com.run.nextjsapi.entity.dto.UserDTO;
import com.run.nextjsapi.entity.params.UserFilterParams;
import com.run.nextjsapi.entity.vo.UserVO;
import com.run.nextjsapi.service.TeamService;
import com.run.nextjsapi.service.UserService;
import com.run.nextjsapi.utils.ObjectConvertUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(path = "/createOrUpdate", method = RequestMethod.POST)
    public Boolean createOrUpdate(@RequestBody UserDTO userDTO) {
        try {
            User user = ObjectConvertUtil.convertObject(userDTO, User.class, (source, target) -> {
                Integer originTeamId = null;
                if (source.getId() != null) {
                    User userTmp = userService.getOne(new QueryWrapper<User>()
                            .eq("id", source.getId())
                            .select("team_id"));
                    // 要注意 DB 中查出来的 userTmp 可能为 null
                    originTeamId = userTmp != null ? userTmp.getTeamId() : null;
                }

                Team currentTeam = teamService.getOne(new QueryWrapper<Team>()
                        .eq("team_name", source.getTeam()));
                // 将 null Team 转换为 new Team()，方便后续直接使用
                currentTeam = currentTeam != null ? currentTeam : new Team();

                // 原来 Team 的 memberCount 减一
                if (originTeamId != null && !originTeamId.equals(currentTeam.getId())) {
                    Team originTeam = teamService.getById(originTeamId);
                    teamService.update(new UpdateWrapper<Team>()
                            .set("member_count", originTeam.getMemberCount() - 1)
                            .eq("id", originTeam.getId()));
                }

                if (currentTeam.getId() != null) {
                    // 现在 Team 的 memberCount 加一
                    if (originTeamId == null || !originTeamId.equals(currentTeam.getId())) {
                        teamService.update(new UpdateWrapper<Team>()
                                .set("member_count", currentTeam.getMemberCount() != null ? currentTeam.getMemberCount() + 1 : 1)
                                .eq("id", currentTeam.getId()));
                    }
                }

                target.setTeamId(currentTeam.getId());
            });
            userService.saveOrUpdate(user);
        } catch (Exception e) {
            logger.error("createOrUpdate error: {}", e.getMessage());
            return false;
        }
        return true;
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

        // 解析 jsonString 得到 UserFilterParams，动态拼接 SQL
        UserFilterParams userFilterParams = JSON.parseObject(jsonString, UserFilterParams.class);
        // 分页参数
        int current = 1, pageSize = 10;
        if (userFilterParams.getPagination() != null) {
            current = userFilterParams.getPagination().getCurrent();
            pageSize = userFilterParams.getPagination().getPageSize();
        }
        // 排序参数
        if (userFilterParams.getField() != null && userFilterParams.getOrder() != null) {
            wrapper.orderBy(true, userFilterParams.getOrder().equals("ascend"), userFilterParams.getField());
        }
        wrapper.orderByDesc("create_time");
        // 过滤参数
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
        if (userFilterParams.getSearchUsername() != null && !userFilterParams.getSearchUsername().isBlank()) {
            wrapper.like("name", userFilterParams.getSearchUsername());
        }

        Page<User> userPage = new Page<>(current, pageSize, true);
        try {
            Page<User> pageResult = userService.page(userPage, wrapper);
            List<User> userList = pageResult.getRecords();

            List<UserVO> userVOList = ObjectConvertUtil.convertList(userList, UserVO.class, (source, target) -> {
                if (source.getTeamId() != null) {
                    Team team = teamService.getById(source.getTeamId());
                    target.setTeam(team.getTeamName());
                }
            });

            long total = pageResult.getTotal();
            return Result.ok()
                    .appendData("userList", userVOList)
                    .appendData("total", total);
        } catch (Exception e) {
            logger.error("getUserList error: {}", e.getMessage());
            return Result.error().message(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(path = "/deleteUserById/{id}", method = RequestMethod.DELETE)
    public Boolean deleteUserById(@PathVariable Long id) {
        try {
            if (userService.getById(id).getTeamId() != null) {
                Team team = teamService.getById(userService.getById(id).getTeamId());
                teamService.update(new UpdateWrapper<Team>()
                        .set("member_count", team.getMemberCount() - 1)
                        .eq("id", team.getId()));
            }
            userService.removeById(id);
        } catch (Exception e) {
            logger.error("deleteUserById error: {}", e.getMessage());
            return false;
        }
        return true;
    }

}
