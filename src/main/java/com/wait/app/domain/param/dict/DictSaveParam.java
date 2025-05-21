package com.wait.app.domain.param.dict;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *
 * @author 天
 * Time: 2024/9/11 23:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictSaveParam {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "备注")
    private String remark;
}
