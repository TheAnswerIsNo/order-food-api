package com.wait.app.utils.page;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author 天
 *
 * @description: 分页工具封装
 */
public class PageUtil {

    /**
     * 开始分页
     * @param requestDTOWithPage 分页参数
     * @param count 是否统计总条数
     * @param tClass
     * @return
     * @param <T>
     */
    public static <T> Page<T> startPage(RequestDTOWithPage requestDTOWithPage,boolean count,Class<T> tClass){
        return PageHelper.startPage(requestDTOWithPage.getPage(), requestDTOWithPage.getPageSize(),count);
    }

    /**
     * 获取分页后数据信息
     * @param list
     * @param requestDTOWithPage
     * @return
     * @param <T>
     */
    public static  <T> PageInfo<T> getPageInfo(List<T> list,RequestDTOWithPage requestDTOWithPage){
        return new PageInfo<>(list,requestDTOWithPage.getPageSize());
    }

    /**
     * 获取分页响应DTO
     * @param list
     * @param requestDTOWithPage
     * @return
     * @param <T>
     */
    public static <T> ResponseDTOWithPage<T> getListDTO(List<T> list,RequestDTOWithPage requestDTOWithPage){
        PageInfo<T> pageInfo = getPageInfo(list, requestDTOWithPage);
        return new ResponseDTOWithPage<>(list,pageInfo.getTotal());
    }

}
