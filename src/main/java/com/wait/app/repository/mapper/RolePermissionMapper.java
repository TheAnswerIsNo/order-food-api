package com.wait.app.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wait.app.domain.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 天
* @description 针对表【role_permission(角色权限关联表)】的数据库操作Mapper
* @createDate 2024-09-11 18:02:24
* @Entity com.wait.takeaway.repository.domain.RolePermission
*/
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

}




