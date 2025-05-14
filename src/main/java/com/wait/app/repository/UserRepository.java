package com.wait.app.repository;


import com.tangzc.mpe.base.repository.BaseRepository;
import com.wait.app.domain.entity.User;
import com.wait.app.repository.mapper.UserMapper;
import org.springframework.stereotype.Repository;


/**
 *
 *
 * @author 天
 * Time: 2024/9/6 22:36
 */
@Repository
public class UserRepository extends BaseRepository<UserMapper,User> {
}
