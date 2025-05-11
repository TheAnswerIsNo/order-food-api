package com.wait.app.service;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.wait.app.domain.dto.role.RoleDTO;
import com.wait.app.domain.dto.user.UserInfoDTO;
import com.wait.app.domain.entity.User;
import com.wait.app.domain.enumeration.*;
import com.wait.app.domain.param.login.WebLoginParam;
import com.wait.app.repository.RoleRepository;
import com.wait.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @author 天
 * Time: 2024/9/6 22:21
 */
@Service
@Slf4j
public class LoginService {
    private final UserRepository userRepository;

    private final UserService userService;

    private final RoleRepository roleRepository;

    private final Executor executor;

    @Autowired
    public LoginService(UserRepository userRepository, Executor executor, UserService userService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.executor = executor;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    /**
     * 公用登录方法
     */
    private UserInfoDTO publicLogin(User user, String device) {

        String userId = user.getId();
        String loginId = PrefixEnum.USER.getValue() + userId;
        UserInfoDTO userInfoDTO = BeanUtil.copyProperties(user, UserInfoDTO.class);
        List<RoleDTO> roleDTOList = roleRepository.getRoleByUserId(userId);
        userInfoDTO.setRoles(roleDTOList);
        StpUtil.login(loginId, device);
        // 异步序列化进redis
        CompletableFuture.runAsync(() -> {
            SaSession session = StpUtil.getSessionByLoginId(loginId);
            // 添加角色
            session.set(SaSession.USER, JSONUtil.toJsonStr(user)).
                    set(SaSession.ROLE_LIST, JSONUtil.toJsonStr(roleDTOList));
        }, executor);
        return userInfoDTO;
    }

    /**
     * 注销
     */
    public void logout() {
        StpUtil.logout();
    }

    public UserInfoDTO webLogin(WebLoginParam webLoginParam) {
        String phone = webLoginParam.getPhone();
        String password = webLoginParam.getPassword();
        User user = userService.getUserByPhone(phone);
        if (Objects.isNull(user)) {
            throw new SaTokenException(400, "该手机号不存在");
        }

        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new SaTokenException(400, "密码错误");
        }
        return this.publicLogin(user, DeviceEnum.WEB.getValue());
    }
}
