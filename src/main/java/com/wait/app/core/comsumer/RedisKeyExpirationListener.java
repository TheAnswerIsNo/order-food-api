package com.wait.app.core.comsumer;


import com.wait.app.domain.entity.TOrder;
import com.wait.app.domain.enumeration.OrderStatusEnum;
import com.wait.app.domain.enumeration.PrefixEnum;
import com.wait.app.repository.OrderRepository;
import com.wait.app.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 *
 * @author 天
 * Time: 2023/10/30 19:08
 * redis过期回调监听
 */
@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    private final OrderService orderService;

    private final OrderRepository orderRepository;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer, OrderService orderService, OrderRepository orderRepository) {
        super(listenerContainer);
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    /**
     * 监听redis中的订单
     * @param message message
     * @param pattern pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expirationKey = message.toString();
        // 若过期key中包含订单前缀
        if (expirationKey.contains(PrefixEnum.ORDER.getValue())){
            String orderId = expirationKey.split(PrefixEnum.ORDER.getValue())[1];
            log.info("处理redis中的订单信息,订单编号为:" + orderId);
            TOrder order = orderRepository.getById(orderId);
            if (order.getStatus().equals(OrderStatusEnum.WFK.getValue())){
                orderService.cancelOrder(orderId);
            }
        }
    }

}
