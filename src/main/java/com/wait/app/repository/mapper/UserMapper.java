package com.wait.app.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wait.app.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 天
 * Time: 2024/9/6 22:35
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
