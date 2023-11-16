package com.run.nextjsapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.run.nextjsapi.common.Result;
import com.run.nextjsapi.common.ResultCode;
import com.run.nextjsapi.entity.Team;
import com.run.nextjsapi.service.TeamService;
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
@RequestMapping("/team")
public class TeamController {

    private final Logger logger = LoggerFactory.getLogger(TeamController.class);
    
    @Resource
    private TeamService teamService;
    
    @RequestMapping(path = "/createOrUpdate", method = RequestMethod.POST)
    public Result createOrUpdate(@RequestBody Team team) {
        boolean isSave = teamService.saveOrUpdate(team);
        if (isSave) {
            return Result.ok();
        } else {
            logger.error("createOrUpdate error");
            return Result.build(ResultCode.DATABASE_ERROR);
        }
    }
    
    @RequestMapping(path = "/getTeamList", method = RequestMethod.GET)
    public Result getTeamList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<Team> teamPage = new Page<>(page, pageSize, true);
        try {
            Page<Team> teamResult = teamService.page(teamPage);
            List<Team> teamList = teamResult.getRecords();
            long total = teamResult.getTotal();
            return Result.ok()
                    .appendData("teamList", teamList)
                    .appendData("total", total);
        } catch (Exception e) {
            logger.error("getTeamList error: {}", e.getMessage());
            return Result.error().message(e.getMessage());
        }
    }

    @RequestMapping(path = "/deleteTeamById/{id}", method = RequestMethod.DELETE)
    public Boolean deleteTeamById(@PathVariable Long id) {
        return teamService.removeById(id);
    }
    
}
