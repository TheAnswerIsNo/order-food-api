package com.wait.app.controller.cli;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.util.SaResult;
import com.wait.app.domain.dto.dict.DictListDTO;
import com.wait.app.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 天
 * Time: 2025/5/14 15:59
 */
@RestController
@Api(tags = "字典-客户端",value = "字典-客户端")
@RequestMapping("/dict/cli")
@SaIgnore
public class DictCliController {

    private final DictService dictService;

    @Autowired
    public DictCliController(DictService dictService) {
        this.dictService = dictService;
    }

    @GetMapping("/list")
    @ApiOperation(value = "指定字典列表")
    public SaResult list(@RequestParam(required = false) String type){
        List<DictListDTO> list = dictService.list(type);
        return SaResult.data(list);
    }
}
