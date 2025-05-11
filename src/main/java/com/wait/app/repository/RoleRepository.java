package com.wait.app.repository;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tangzc.mpe.base.repository.BaseRepository;
import com.wait.app.domain.dto.role.RoleDTO;
import com.wait.app.domain.entity.Role;
import com.wait.app.repository.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @author 天
 * Time: 2024/9/15 9:30
 */
@Repository
public class RoleRepository extends BaseRepository<RoleMapper, Role> {

    private final RoleMapper roleMapper;

    private final Executor executor;

    @Autowired
    public RoleRepository(RoleMapper roleMapper, Executor executor) {
        this.roleMapper = roleMapper;
        this.executor = executor;
    }

    /**
     * 根据level获取角色
     *
     * @param roleKey roleKey
     * @return Role
     */
    public Role getRoleByRoleKey(String roleKey) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getRoleKey,roleKey);
        return roleMapper.selectOne(queryWrapper);
    }

    /**
     * 根据userId获取角色
     * @param userId userId
     * @return List<RoleDTO>
     */
    public List<RoleDTO> getRoleByUserId(String userId) {
        return roleMapper.getRoleByUserId(userId);
    }

    /**
     * 更新角色
     * @param userId userId
     */
    public void updateRedisRole(String userId){
        // 异步更新redis
        String loginId = StpUtil.getLoginId().toString();
        CompletableFuture.runAsync(()->{
            SaSession saSession = StpUtil.getSessionByLoginId(loginId);
            List<RoleDTO> roleDTOList = this.getRoleByUserId(userId);
            saSession.set(SaSession.ROLE_LIST, JSONUtil.toJsonStr(roleDTOList));
        },executor);
    }
}
