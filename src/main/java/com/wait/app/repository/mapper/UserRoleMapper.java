package com.wait.app.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wait.app.domain.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 天
* @description 针对表【user_role(用户角色关联表)】的数据库操作Mapper
* @createDate 2024-09-11 18:02:24
* @Entity com.wait.takeaway.repository.domain.UserRole
*/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}




