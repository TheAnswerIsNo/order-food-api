package com.wait.app.repository;


import com.tangzc.mpe.base.repository.BaseRepository;
import com.wait.app.domain.entity.OrderDetail;
import com.wait.app.repository.mapper.OrderDetailMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 天
 * Time: 2024/10/5 2:59
 */
@Repository
public class OrderDetailRepository extends BaseRepository<OrderDetailMapper, OrderDetail> {
}
