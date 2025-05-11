package com.wait.app.repository;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tangzc.mpe.base.repository.BaseRepository;
import com.wait.app.domain.entity.User;
import com.wait.app.repository.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 *
 *
 * @author 天
 * Time: 2024/9/6 22:36
 */
@Repository
public class UserRepository extends BaseRepository<UserMapper,User> {

    @Resource
    private UserMapper userMapper;

    /**
     * 根据openId获取用户
     * @param openId openId
     * @return user
     */
    public User getUserByOpenId(String openId){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getOpenId,openId);
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 根据phone获取用户
     * @param phone phone
     * @return user
     */
    public User getUserByPhone(String phone){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getPhone,phone);
        return userMapper.selectOne(queryWrapper);
    }
}
