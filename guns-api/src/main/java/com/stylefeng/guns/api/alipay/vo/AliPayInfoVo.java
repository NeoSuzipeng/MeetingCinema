package com.stylefeng.guns.api.alipay.vo;

import lombok.Data;


import java.io.Serializable;

/**
 * Created by Su on 2019/5/3.
 * 支付信息模型
 */
@Data
public class AliPayInfoVo implements Serializable{

    private String orderId;

    private String QRCodeAddress;

}
