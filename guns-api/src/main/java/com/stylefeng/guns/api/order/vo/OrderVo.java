package com.stylefeng.guns.api.order.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Su on 2019/4/30.
 * 订单信息模型
 */
@Data
public class OrderVo implements Serializable{

    private String orderId;

    private String filmName;

    private String fieldTime;

    private String cinemaName;

    private String seatsName;

    private String orderPrice;

    private String orderTimestamp;

    private String orderStatus;
}
