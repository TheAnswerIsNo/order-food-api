package com.wait.app.domain.dto.order;

import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 天
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayDTO {

    private PrepayWithRequestPaymentResponse response;

    private String orderId;
}
