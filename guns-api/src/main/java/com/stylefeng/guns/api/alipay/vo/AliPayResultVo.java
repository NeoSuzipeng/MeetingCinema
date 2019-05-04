package com.stylefeng.guns.api.alipay.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Su on 2019/5/3.
 * 支付结果模型
 */
@Data
public class AliPayResultVo implements Serializable{

    private String orderId;

    private Integer orderStatus;

    private String orderMsg;

}
