package com.wait.app.domain.enumeration;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 天
 */

@Getter
@AllArgsConstructor
public enum WeChatInfoEnum {

    //微信小程序id
    APPID("appid","wxc6366579e950d692"),

    //微信小程secret
    SECRET("secret","3478f4549cdaff70f9601d8d6a21b057"),

    //微信平台返回code，这里枚举只取name的值
    JSCODE("js_code","0"),

    //查询电话用的code
    CODE("code","0"),

    //微信授权登录的grant_type
    GRANT_TYPE_LOGIN("grant_type","authorization_code"),

    //微信小程序获取AccessToken的grant_type
    GRANT_TYPE_TOKEN("grant_type","client_credential"),

    //小程序全局唯一后台接口调用凭据，token有效期为7200s
    ACCESS_TOKEN("access_token","0"),

    //商户id
    MERCHANT_ID("merchantId","1656498770"),

    // openid
    OPENID("openid",""),

    //商户APIV3密钥
    APIV3KEY("apiV3Key","zU5mI9hG1iL2zG2zS7wJ9pH7pX1wV1hC"),

    //商户证书序列号
    MERCHANT_SERIAL_NUMBER("merchantSerialNumber","66C42A33C06BE36AD0C29CB29CA29732025ACF7E"),

    //支付回调地址
    NOTIFYURL("notifyUrl","https://www.cqjhyz.top/api/order/callback"),

    PRIVATE_KEY("privateKey","MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQDvFdNg7MSp7xzw\n" +
            "8zN0j3zW5EFSYUKL+rDXOQFg2Cnsen+rNqaMUs4kWe43Hjq+o3BwKwx+n2yXuUwQ\n" +
            "0kEFstKt2Bh/s3cKJ8DT42ejrhML6dXMmSW8fe1lOPFQ2VUnMchXg4i+2yiTFryM\n" +
            "haxYyU2BM8d/OL/rDnwhv+NN3uDUP3kFmSXYjV83d3H2+KMQW0TdUXQZ90+CTboY\n" +
            "t78yAEo3+m+L8eM1rb/4ng3ymZlCntgJVReE79j4K8oMQ+fqcuy4D7cv5+gJ0xqK\n" +
            "5vUz2qAqNnHYQgS1oKmOlIQacWi06vmoawhUR3pnvEIbG59pG4a/nEcBMWD1+sXH\n" +
            "G76pJP5rAgMBAAECggEBAMhcWBqgEWgAFRbwGS0TmqFUBFIPY/9/BXg4M6l/PhMJ\n" +
            "zbgkQorSUiMwomvaj4x316CI0x3CaPT1uoNWUFuAMwKxWEaclaQTEIwBxTbsiNWx\n" +
            "DxU5OuCt/6aWg9UnbS89jxGYD58ydtxdb4Gr/HUjrioaxJMfceaA6xI1hz4ZC/2R\n" +
            "3RoD5N9TYTjrSIxfKOhCMMBooweMfqo15gKAX2p90p5uT5CZ9jGD+uWiSqIrkBm+\n" +
            "772xANQXU/VqAxiJreDJpBMRDkJTJNbln4kRwJLTVTJmTBTJc3EoLisnSje/jCOI\n" +
            "cW3uiy/epghhz59lnBqzrPUWsONFD8xhIIzJCizsddECgYEA+0nXUtda5aqdJIcp\n" +
            "Xa3NIH3KdoO+vm+CXBj5uTO2APsv9eKhW5hgYKq1u/GKOZXmaTHHm/S4p7lf8hsU\n" +
            "5t9xUsfWxFhGMK2/OB80J8ij9EnqZvr6r5kG9idQqck0YYNUUAakTboLyXSk5/x1\n" +
            "Hou4AlrNg+5Bf8gbnoZJDUxDsLcCgYEA85FpGYNRGvlViV8a/X54b15Ms2YjgbA1\n" +
            "VTcrxBvHuzOYQ+n8U7EOLwkClH0cc6CuNXNv2DjWOxOuQu6F7GHwZcueJ55qBPL3\n" +
            "aX9tgucirysOFYiKf6fqLvbjGXY6/K6zV8fBaPSrcC7DmbE4KFHtkERPzTK8VOGJ\n" +
            "Sl7q16sZw+0CgYEA0BtIEtg89AUHHRA+xtFAFw92FA5pcocgu6sHbUSRD2D3SDhH\n" +
            "D3czIftehGa15Zjp9+Z8/ACAyI6yEewxtD4KONc9WuAMSRYrTYwyAlycw5xPXret\n" +
            "0qz14fePSv1RvwwnUBsgCSrxxgYu59zhFcn6OyD+IKu5XNzZWsC1USj+yiMCgYEA\n" +
            "sZ9cRtfF4ObtowVxWGXPjF/3jWiyAhSvgBqA1z3vFSzWIMjVU28uSczHTeRDSGto\n" +
            "P+CUMtmYGXMLxRHPJohx2Lp59qiG5l8NYKkmAq1uVNIQLlHptpiMpn0zNlFLSRRa\n" +
            "A2zQNisfjur7h+x+aKD+nxPCIoGaaEglZ8/6cRb87i0CgYEApCm5ZB3j5UY/+XW0\n" +
            "vlKxibeBIYRMpL0TJjYGrZQNXHtkD1iLwRwF5CMm/At994ocUNUUc65dlnwUAis+\n" +
            "vn96N2pydxoJodxjSemPxXFm/gb4HCCBn4Un+8w2O8V5G1w4NkgY8k3xc5qxyPpC\n" +
            "olg/Sk8FjFZr4Gy4ItL17MJpegQ=")
    ;

    private final String name;

    private final String value;
}
