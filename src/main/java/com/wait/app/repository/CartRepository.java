package com.wait.app.repository;

import com.tangzc.mpe.base.repository.BaseRepository;
import com.wait.app.domain.entity.Cart;
import com.wait.app.repository.mapper.CartMapper;
import org.springframework.stereotype.Repository;

/**
 * @author 天
 * Time: 2025/4/23 6:00
 */
@Repository
public class CartRepository extends BaseRepository<CartMapper, Cart> {
}
