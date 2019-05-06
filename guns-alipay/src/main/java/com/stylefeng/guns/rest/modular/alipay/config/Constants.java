package com.stylefeng.guns.rest.modular.alipay.config;

/**
 * Created by liuyangkly on 15/7/29.
 *
 */
public class Constants {

    private Constants() {
        // No Constructor.
    }

    public static final String SUCCESS = "10000"; // 成功
    public static final String PAYING  = "10003"; // 用户支付中
    public static final String FAILED  = "40004"; // 失败
    public static final String ERROR   = "20000"; // 系统异常

    public static final Integer PAY_SUCCESS = 1;
    public static final Integer PAY_FAIL = 0;
    public static final String QR_PATH = "qrcode/";

    //回调常量
    public static final String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
    public static final String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

    public static final  String RESPONSE_SUCCESS = "success";
    public static final  String RESPONSE_FAILED = "failed";


}
