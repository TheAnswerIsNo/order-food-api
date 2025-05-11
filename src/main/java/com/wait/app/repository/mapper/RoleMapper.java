package com.wait.app.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wait.app.domain.dto.role.RoleDTO;
import com.wait.app.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 天
* @description 针对表【role(角色表)】的数据库操作Mapper
* @createDate 2024-09-11 18:02:24
* @Entity com.wait.takeaway.repository.domain.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<RoleDTO> getRoleByUserId(String userId);
}




