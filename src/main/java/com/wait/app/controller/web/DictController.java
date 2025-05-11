package com.wait.app.controller.web;

import cn.dev33.satoken.util.SaResult;
import com.wait.app.controller.BaseController;
import com.wait.app.domain.dto.dict.DictListDTO;
import com.wait.app.domain.param.dict.DictSaveParam;
import com.wait.app.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *
 * @author 天
 * Time: 2024/9/10 17:08
 */
@RestController
@Api(tags = "字典",value = "字典")
@RequestMapping("/dict")
public class DictController extends BaseController {

    private final DictService dictService;

    @Autowired
    public DictController(DictService dictService) {
        this.dictService = dictService;
    }

    @GetMapping("/list")
    @ApiOperation(value = "指定字典列表")
    public SaResult list(@RequestParam(required = false) String type){
        List<DictListDTO> list = dictService.list(type);
        return SaResult.data(list);
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存字典")
    public SaResult save(@RequestBody @NonNull DictSaveParam dictSaveParam){
        dictService.save(dictSaveParam);
        return SaResult.ok("保存成功");
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除字典")
    public SaResult delete(@RequestParam String id){
        dictService.delete(id);
        return SaResult.ok("删除成功");
    }
}
