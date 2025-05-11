package com.wait.app.domain.dto.wechatPayCallback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * @author 天
 * Time: 2023/11/8 20:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WechatPayCallback {

    /**
     * 通知的唯一ID。
     */
    private String id;

    /**
     * 通知创建的时间
     */
    private Date create_time;

    /**
     * 通知的类型，支付成功通知的类型为TRANSACTION.SUCCESS。
     */
    private String resource_type;

    /**
     * 通知的资源数据类型，支付成功通知为encrypt-resource。
     */
    private String event_type;

    /**
     * 回调摘要
     */
    private String summary;

    /**
     * 通知资源数据。
     */
    private Resource resource;
}
