package com.wait.app.service;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 自定义权限验证接口扩展
 * @author 天
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        SaSession saSession = StpUtil.getSessionByLoginId(loginId);
        saSession.get(SaSession.PERMISSION_LIST);
        return null;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        SaSession saSession = StpUtil.getSessionByLoginId(loginId);
        List<Integer> list = (List<Integer>)saSession.get(SaSession.ROLE_LIST);
        return list.stream().map(Objects::toString).toList();
    }

}