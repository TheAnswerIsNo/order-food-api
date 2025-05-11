package com.wait.app.repository;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tangzc.mpe.base.repository.BaseRepository;
import com.wait.app.domain.entity.UserRole;
import com.wait.app.repository.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 天
 * Time: 2024/9/15 10:57
 */
@Repository
public class UserRoleRepository extends BaseRepository<UserRoleMapper, UserRole> {

    private final UserRoleMapper userRoleMapper;

    @Autowired
    public UserRoleRepository(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    /**
     * 根据userId删除
     * @param userId userId
     */
    public void removeByUserId(String userId){
        LambdaUpdateWrapper<UserRole> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserRole::getUserId,userId);
        userRoleMapper.delete(updateWrapper);
    }
}
