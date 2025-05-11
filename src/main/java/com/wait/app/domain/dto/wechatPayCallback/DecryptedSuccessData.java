package com.wait.app.domain.dto.wechatPayCallback;

import com.wechat.pay.java.service.lovefeast.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.SceneInfo;
import com.wechat.pay.java.service.payments.model.PromotionDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 *
 * @author 天
 * Time: 2023/11/8 20:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecryptedSuccessData {

    /**
     * 微信支付系统生成的订单号。
     */
    private String transactionId;

    /**
     * 订单金额信息
     */
    private Amount amount;

    /**
     * 商户的商户号，由微信支付生成并下发。
     */
    private String mchid;

    /**
     * 交易状态，枚举值：
     * SUCCESS：支付成功
     * REFUND：转入退款
     * NOTPAY：未支付
     * CLOSED：已关闭
     * REVOKED：已撤销（付款码支付）
     * USERPAYING：用户支付中（付款码支付）
     * PAYERROR：支付失败(其他原因，如银行返回失败)
     */
    private String tradeState;

    /**
     * 银行类型，采用字符串类型的银行标识。银行标识请参考《银行类型对照表》。
     */
    private String bankType;

    /**
     * 优惠功能，享受优惠时返回该字段
     */
    private List<PromotionDetail> promotionDetail;

    /**
     * 支付完成时间
     */
    private Date successTime;

    /**
     * 支付者信息
     */
    private Payer payer;

    /**
     * 商户系统内部订单号，可以是数字、大小写字母_-*的任意组合且在同一个商户号下唯一。
     */
    private String outTradeNo;

    /**
     * 直连商户申请的公众号或移动应用AppID。
     */
    private String appID;

    /**
     * 交易状态描述。
     */
    private String tradeStateDesc;

    /**
     * 交易类型，枚举值：
     * JSAPI：公众号支付
     * NATIVE：扫码支付
     * App：App支付
     * MICROPAY：付款码支付
     * MWEB：H5支付
     * FACEPAY：刷脸支付
     */
    private String tradeType;

    /**
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用，实际情况下只有支付完成状态才会返回该字段。
     */
    private String attach;

    /**
     * 支付场景信息描述
     */
    private SceneInfo sceneInfo;
}
