package com.wait.app.repository;

import com.tangzc.mpe.base.repository.BaseRepository;
import com.wait.app.domain.entity.Goods;
import com.wait.app.repository.mapper.GoodsMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 天
 * Time: 2025/4/23 6:00
 */
@Repository
public class GoodsRepository extends BaseRepository<GoodsMapper, Goods> {
}
