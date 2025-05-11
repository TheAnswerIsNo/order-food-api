package com.wait.app.repository.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wait.app.domain.entity.TOrder;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 天
* @description 针对表【order(订单表)】的数据库操作Mapper
* @createDate 2024-09-11 18:02:24
* @Entity com.wait.takeaway.repository.domain.Order
*/
@Mapper
public interface TOrderMapper extends BaseMapper<TOrder> {

}




