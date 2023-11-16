package com.run.nextjsapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.run.nextjsapi.dao.TeamMapper;
import com.run.nextjsapi.entity.Team;
import com.run.nextjsapi.service.TeamService;
import org.springframework.stereotype.Service;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/11/16
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {
}
