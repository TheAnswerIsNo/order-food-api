package com.wait.app.controller.web;

import cn.dev33.satoken.util.SaResult;
import com.wait.app.controller.BaseController;
import com.wait.app.domain.dto.user.UserInfoDTO;
import com.wait.app.domain.param.login.WebLoginParam;
import com.wait.app.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author 天
 * Time: 2024/9/6 22:19
 */
@RestController
@Api(tags = "登录",value = "登录")
public class LoginController extends BaseController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @ApiOperation("后台登录")
    @PostMapping("/login/web")
    public SaResult login(@RequestBody WebLoginParam webLoginParam){
        UserInfoDTO userInfoDTO = loginService.webLogin(webLoginParam);
        return new SaResult(SaResult.CODE_SUCCESS,"登录成功",userInfoDTO);
    }

    @ApiOperation(value = "注销")
    @PostMapping(value = "/logout")
    public SaResult logout(){
        loginService.logout();
        return SaResult.ok("注销成功");
    }


}
