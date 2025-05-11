package com.wait.app.repository;


import com.tangzc.mpe.base.repository.BaseRepository;
import com.wait.app.domain.entity.TOrder;
import com.wait.app.repository.mapper.TOrderMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 天
 * Time: 2024/10/5 2:59
 */
@Repository
public class OrderRepository extends BaseRepository<TOrderMapper, TOrder> {
}
