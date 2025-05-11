package com.wait.app.controller.web;

import cn.dev33.satoken.util.SaResult;
import com.wait.app.controller.BaseController;
import com.wait.app.domain.dto.role.RoleDTO;
import com.wait.app.domain.param.role.RoleSaveParam;
import com.wait.app.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author 天
 * Time: 2024/9/15 9:29
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色",value = "角色")
public class RoleController extends BaseController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(value = "/save")
    @ApiOperation("保存角色")
    public SaResult save(@RequestBody RoleSaveParam roleSaveParam){
        roleService.save(roleSaveParam,getUserId());
        return SaResult.ok("保存成功");
    }

    @GetMapping("/list")
    @ApiOperation("角色列表")
    public SaResult list(){
        List<RoleDTO> list = roleService.list();
        return SaResult.data(list);
    }

}
