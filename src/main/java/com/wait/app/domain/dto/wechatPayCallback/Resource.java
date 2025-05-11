package com.wait.app.domain.dto.wechatPayCallback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 天
 * Time: 2023/11/8 20:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resource {

    /**
     * 原始回调类型，为transaction。
     */
    private String original_type;

    /**
     * 对开启结果数据进行加密的加密算法，目前只支持AEAD_AES_256_GCM。
     */
    private String algorithm;

    /**
     * Base64编码后的开启/停用结果数据密文。
     */
    private String ciphertext;

    /**
     * 附加数据。
     */
    private String associated_data;

    /**
     * 加密使用的随机串。
     */
    private String nonce;
}
