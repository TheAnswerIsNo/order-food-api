package com.wait.app.repository;

import com.tangzc.mpe.base.repository.BaseRepository;
import com.wait.app.domain.entity.GoodsDict;
import com.wait.app.repository.mapper.GoodsDictMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 天
 * Time: 2025/5/13 22:13
 */
@Repository
public class GoodsDictRepository extends BaseRepository<GoodsDictMapper, GoodsDict> {
}
