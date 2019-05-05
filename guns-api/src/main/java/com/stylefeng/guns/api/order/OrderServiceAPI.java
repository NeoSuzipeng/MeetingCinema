package com.stylefeng.guns.api.order;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.vo.OrderCinemaVo;
import com.stylefeng.guns.api.order.vo.OrderVo;

import java.util.List;

/**
 * Created by Su on 2019/4/30.
 *
 */
public interface OrderServiceAPI {

    /**
     * 验证售出的票是否为真
     * @param fieldId 影厅ID
     * @param seats 用户选择未售座位
     * @return
     */
    boolean isTrueSeats(String fieldId, String seats, String seatLocation);

    /**
     * 用户选择得未售座位是否未被卖出
     * @param field 影厅ID
     * @param seats 用户选择未售座位
     * @return True：全部未卖出  False:一个以上已被卖出
     */
    boolean isNotSoldSeats(String field, String seats);

    /**
     * 创建订单
     * @param fieldId
     * @param soldSeats
     * @param seatsName
     * @return
     */
    OrderVo createOrder(Integer fieldId, String soldSeats, String seatsName, Integer userId);

    /**
     * 获取当前用户订单信息
     * @param userId
     * @return 用户订单列表
     */
    Page<OrderVo> getOrderByUserId(Integer userId, Page<OrderVo> page);


    /**
     * 影院模块：为影厅提供已售座位信息
     * @param fieldId
     * @return
     */
    String getSoldSeatByFieldId(Integer fieldId);


    /**
     * 支付模块：支付前查询订单信息
     * @param orderId
     * @return
     */
    OrderVo getOrderInfoById(String orderId);

    /**
     * 支付模块：支付成功后修改订单状态
     * @param orderId
     * @return
     */
    boolean paySuccess(String orderId);

    /**
     * 支付模块：支付失败后修改订单状态
     * @param orderId
     * @return
     */
    boolean payFail(String orderId);


    /**
     * 支付模块：取消订单
     * @param userId
     * @param orderId
     * @return
     */
    boolean cancel(String userId, String orderId);
}
