package com.wait.app.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.wait.app.domain.dto.role.RoleDTO;
import com.wait.app.domain.entity.Role;
import com.wait.app.domain.param.role.RoleSaveParam;
import com.wait.app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author 天
 * Time: 2024/9/15 9:31
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * 保存角色
     * @param roleSaveParam roleSaveParam
     */
    public void save(RoleSaveParam roleSaveParam, String userId) {
        Role role = BeanUtil.copyProperties(roleSaveParam, Role.class);
        if (StrUtil.isEmpty(roleSaveParam.getId())){
            role.setCreatorId(userId);
            roleRepository.save(role);
        }else {
            roleRepository.updateById(role);
        }
    }

    public List<RoleDTO> list() {
        List<Role> list = roleRepository.lambdaQuery().orderByDesc(Role::getSort).orderByDesc(Role::getCreateTime).list();
        return BeanUtil.copyToList(list, RoleDTO.class);
    }
}
