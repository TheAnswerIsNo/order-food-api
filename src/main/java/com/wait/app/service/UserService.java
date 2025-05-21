package com.wait.app.service;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.wait.app.domain.dto.role.RoleDTO;
import com.wait.app.domain.dto.user.UserInfoDTO;
import com.wait.app.domain.entity.User;
import com.wait.app.domain.entity.UserRole;
import com.wait.app.domain.param.user.UserListParam;
import com.wait.app.domain.param.user.UserSaveParam;
import com.wait.app.repository.UserRepository;
import com.wait.app.repository.UserRoleRepository;
import com.wait.app.utils.page.PageUtil;
import com.wait.app.utils.page.ResponseDTOWithPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author 天
 * Time: 2024/9/15 10:54
 */
@Service
public class UserService{

    private final UserRoleRepository userRoleRepository;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRoleRepository userRoleRepository, UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    /**
     * 分配角色
     * @param userId userId
     * @param roleIds roleIds
     */
    public void distribute(String userId, List<String> roleIds) {
        userRoleRepository.removeByUserId(userId);
        List<UserRole> list = roleIds.stream().map(item -> UserRole.builder().userId(userId).roleId(item).build()).toList();
        userRoleRepository.saveBatch(list);
    }

    /**
     * 用户信息
     * @return UserInfoDTO
     */
    public UserInfoDTO info() {
        SaSession session = StpUtil.getSession();
        User user = JSONUtil.toBean((String) session.get(SaSession.USER),User.class);
        List<RoleDTO> roleDTOList = JSONUtil.toList((String) session.get(SaSession.ROLE_LIST), RoleDTO.class);
        return UserInfoDTO.builder().id(user.getId()).roles(roleDTOList).build();
    }

    public User getUserByPhone(String phone) {
        return userRepository.lambdaQuery().eq(User::getPhone,phone).one();
    }

    /**
     * 用户列表
     * @param userListParam userListParam
     * @return  ResponseDTOWithPage<UserInfoDTO>
     */
    public ResponseDTOWithPage<UserInfoDTO> list(UserListParam userListParam) {
        PageUtil.startPage(userListParam, true, User.class);
        List<User> list = userRepository.lambdaQuery()
                .like(StrUtil.isNotBlank(userListParam.getNickname()),User::getNickname, userListParam.getNickname())
                .orderByDesc(User::getCreateTime)
                .list();
        List<UserInfoDTO> result = BeanUtil.copyToList(list, UserInfoDTO.class);
        return PageUtil.getListDTO(result,userListParam);
    }

    /**
     * 保存用户
     * @param userSaveParam userSaveParam
     */
    public void save(UserSaveParam userSaveParam) {
        User user = BeanUtil.toBean(userSaveParam, User.class);
        // 加密
        String hashpw = BCrypt.hashpw(userSaveParam.getPassword(), BCrypt.gensalt(15));
        user.setPassword(hashpw);
        userRepository.saveOrUpdate(user);
    }

    /**
     * 删除用户
     * @param id id
     */
    public void delete(String id) {
        userRepository.removeById(id);
    }
}
