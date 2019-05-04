package com.stylefeng.guns.api.alipay;

import com.stylefeng.guns.api.alipay.vo.AliPayInfoVo;
import com.stylefeng.guns.api.alipay.vo.AliPayResultVo;

/**
 * Created by Su on 2019/5/3.
 * 支付服务接口
 */
public interface AliPayServiceAPI {

    AliPayInfoVo getQRCode(String orderId);

    AliPayResultVo getOrderStatus(String orderId);

}
