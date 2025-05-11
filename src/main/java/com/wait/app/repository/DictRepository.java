package com.wait.app.repository;


import com.tangzc.mpe.base.repository.BaseRepository;
import com.wait.app.domain.entity.Dict;
import com.wait.app.repository.mapper.DictMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 天
 * Time: 2024/9/10 17:11
 */
@Repository
public class DictRepository extends BaseRepository<DictMapper, Dict> {
}
