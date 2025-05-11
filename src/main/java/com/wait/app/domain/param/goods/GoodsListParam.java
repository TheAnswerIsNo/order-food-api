package com.wait.app.domain.param.goods;

import com.wait.app.utils.page.RequestDTOWithPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 天
 * Time: 2025/4/23 13:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsListParam extends RequestDTOWithPage {

    @ApiModelProperty(value = "所属类别id")
    private String dictId;
}
