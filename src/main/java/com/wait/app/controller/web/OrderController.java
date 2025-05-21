package com.wait.app.controller.web;

import cn.dev33.satoken.util.SaResult;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.wait.app.controller.BaseController;
import com.wait.app.domain.dto.order.OrderListDTO;
import com.wait.app.domain.dto.wechatPayCallback.DecryptedSuccessData;
import com.wait.app.domain.dto.wechatPayCallback.Resource;
import com.wait.app.domain.dto.wechatPayCallback.WechatPayCallback;
import com.wait.app.domain.dto.wechatPayCallback.WechatResponse;
import com.wait.app.domain.enumeration.WeChatInfoEnum;
import com.wait.app.domain.param.order.OrderListParam;
import com.wait.app.service.OrderService;
import com.wait.app.utils.page.ResponseDTOWithPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author 天
 * Time: 2024/10/4 11:51
 */
@RestController
@Api(tags = "订单",value = "订单")
@RequestMapping("/order")
public class OrderController extends BaseController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation("取消订单")
    @GetMapping(value = "/cancel/{orderId}")
    public SaResult cancelOrder(@PathVariable String orderId){
        orderService.cancelOrder(orderId);
        return SaResult.ok("取消订单成功");
    }

    @ApiOperation("订单列表")
    @GetMapping(value = "/list")
    public SaResult list(@ModelAttribute OrderListParam orderListParam){
        ResponseDTOWithPage<OrderListDTO> list = orderService.list(orderListParam);
        return SaResult.data(list);
    }

    @ApiOperation("制作完成")
    @GetMapping(value = "/complete/{orderId}")
    public SaResult complete(@PathVariable String orderId){
        orderService.complete(orderId);
        return SaResult.ok("修改订单状态成功");
    }

    @ApiOperation("支付回调接口")
    @PostMapping(value = "/callback")
    public void callback(@RequestBody WechatPayCallback wechatPayCallback, HttpServletResponse response) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        DecryptedSuccessData decryptedSuccessData = decryption(wechatPayCallback.getResource());
        response.setContentType("application/json");
        response.setStatus(HttpStatus.HTTP_OK);
        WechatResponse wechatResponse = new WechatResponse();
        wechatResponse.setCode(WechatResponse.SUCCESS);
        try {
            orderService.payCallback(decryptedSuccessData);
        } catch (Exception e) {
            e.printStackTrace();
            wechatResponse.setCode(WechatResponse.FAIL);
            wechatResponse.setMessage("失败");
        }
        String responseMsg = JSONUtil.toJsonStr(wechatResponse);
        OutputStream writer = response.getOutputStream();
        writer.write(responseMsg.getBytes(StandardCharsets.UTF_8));
        response.flushBuffer();
        writer.close();
    }

    /**
     * 解密操作
     * @param resource resource
     * @return DecryptedSuccessData
     */
    private DecryptedSuccessData decryption(Resource resource) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKeySpec key = new SecretKeySpec(WeChatInfoEnum.APIV3KEY.getValue().getBytes(StandardCharsets.UTF_8), "AES");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, resource.getNonce().getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.DECRYPT_MODE,key,gcmParameterSpec);
        cipher.updateAAD(resource.getAssociated_data().getBytes(StandardCharsets.UTF_8));
        String s = new String(cipher.doFinal(Base64.getDecoder().decode(resource.getCiphertext())), StandardCharsets.UTF_8);
        return JSONUtil.toBean(s,DecryptedSuccessData.class);
    }

}
