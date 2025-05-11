package com.wait.app.utils.page;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author 天
 *
 * @description: 请求参数(分页)
 */
public class RequestDTOWithPage implements Pageable{

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页", name = "page", dataType = "Integer", example = "1")
    private Integer page = 1;

    /**
     * 分页大小
     */
    @ApiModelProperty(value = "分页大小", name = "pageSize", dataType = "Integer", example = "20")
    private Integer pageSize = 20;

    /**
     * 获取 当前页
     *
     * @return page 当前页
     */
    @Override
    public Integer getPage() {
        return page;
    }

    /**
     * 设置 当前页
     *
     * @param page 当前页
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * 获取 分页大小
     *
     * @return pageSize 分页大小
     */
    @Override
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置 分页大小
     *
     * @param pageSize 分页大小
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
