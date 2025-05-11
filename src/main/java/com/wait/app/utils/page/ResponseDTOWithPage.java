package com.wait.app.utils.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 天
 *
 * @description: 分页响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTOWithPage<T> {

    private List<T> list;

    private Long count;
}
