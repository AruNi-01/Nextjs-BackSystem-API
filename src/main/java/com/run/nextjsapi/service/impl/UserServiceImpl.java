package com.run.nextjsapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.run.nextjsapi.dao.UserMapper;
import com.run.nextjsapi.entity.User;
import com.run.nextjsapi.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/11/16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
