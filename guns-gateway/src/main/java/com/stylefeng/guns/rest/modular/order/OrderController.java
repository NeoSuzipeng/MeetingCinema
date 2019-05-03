package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderVo;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Su on 2019/4/30.
 * 订单服务后端服务
 */
@Slf4j
@RestController
@RequestMapping(value = "/order/")
public class OrderController {

    @Reference(interfaceClass = OrderServiceAPI.class)
    private OrderServiceAPI orderServiceAPI;

    @Reference(interfaceClass = CinemaServiceAPI.class)
    private CinemaServiceAPI cinemaServiceAPI;

    @RequestMapping(value = "buyTickets", method = RequestMethod.POST)
    public ResponseVO buyTickets(Integer fieldId, String soldSeats, String seatsName){
        try{
            String userId = CurrentUser.getUserId();
            if(StringUtils.isEmpty(userId)){
                return ResponseVO.serviceFail("用户未登录");
            }
            //条件1：验证售出的票是否为真
            String seatsLocation = cinemaServiceAPI.getSeatsLocationByFieldId(fieldId);
            boolean isTrue = orderServiceAPI.isTrueSeats(soldSeats, fieldId+"", seatsLocation);
            //条件2：座位是否已销售
            boolean isNotSold = orderServiceAPI.isNotSoldSeats(fieldId+"", soldSeats);

            if(isNotSold && isTrue){
                OrderVo order = orderServiceAPI.createOrder(fieldId, soldSeats, seatsName, Integer.parseInt(userId));
                if (order == null){
                    log.error("购票失败");
                    return ResponseVO.serviceFail("购票业务异常");
                }else {
                    return ResponseVO.Success(order);
                }
            }else {
                return ResponseVO.serviceFail("选座错误");
            }
        }catch (Exception e){
            log.error("购票业务异常", e);
            return ResponseVO.serviceFail("购票业务异常");
        }
    }


    @RequestMapping(value = "getOrderInfo", method = RequestMethod.POST)
    public ResponseVO getOrderInfo(
                @RequestParam(name = "nowPage", required = false, defaultValue = "1") Integer nowPage,
                @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize
    ){
        //获取当前用户信息
        String userId = CurrentUser.getUserId();
        if(StringUtils.isEmpty(userId)){
            return ResponseVO.serviceFail("用户未登录");
        }
        //获取当前用户订单信息
        Page<OrderVo> page = new Page<>(nowPage, pageSize);
        Page<OrderVo> orders = orderServiceAPI.getOrderByUserId(Integer.parseInt(userId), page);
        return ResponseVO.Success(nowPage, (int)orders.getPages(), orders);
    }


    //TODO 取消订单
}
