package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.stylefeng.guns.api.alipay.AliPayServiceAPI;
import com.stylefeng.guns.api.alipay.vo.AliPayInfoVo;
import com.stylefeng.guns.api.alipay.vo.AliPayResultVo;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderVo;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.film.constant.Constant;
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

    @Reference(interfaceClass = AliPayServiceAPI.class)
    private AliPayServiceAPI aliPayServiceAPI;

    public ResponseVO buyTicketsError(Integer fieldId, String soldSeats, String seatsName){
        log.info("熔断业务");
        return ResponseVO.serviceFail("订票人数过多，稍后重试");
    }

    @HystrixCommand(fallbackMethod = "buyTicketsError", commandProperties = {
            @HystrixProperty(name="execution.isolation.strategy", value = "THREAD"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value
                    = "4000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            },
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "1"),
                    @HystrixProperty(name = "maxQueueSize", value = "10"),
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "1000"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "8"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1500")
    })
    @RequestMapping(value = "buyTickets", method = RequestMethod.POST)
    public ResponseVO buyTickets(Integer fieldId, String soldSeats, String seatsName){
        try{
            String userId = CurrentUser.getUserId();
            if(StringUtils.isEmpty(userId)){
                return ResponseVO.serviceFail("用户未登录");
            }

            OrderVo order = orderServiceAPI.createOrder(fieldId, soldSeats, seatsName, Integer.parseInt(userId));
            if (order == null){
                log.error("购票失败");
                return ResponseVO.serviceFail("购票业务异常");
            }else {
                return ResponseVO.Success(order);
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


    //TODO 取消订单,并清除已售座位





    @RequestMapping(value = "getPayInfo", method = RequestMethod.POST)
    public ResponseVO getPayInfo(@RequestParam("orderId")String orderId){
        String userId = CurrentUser.getUserId();
        if (StringUtils.isEmpty(userId)){
            return ResponseVO.serviceFail("用户未登录");
        }

        AliPayInfoVo aliPayInfoVo = aliPayServiceAPI.getQRCode(orderId);
        if (aliPayInfoVo == null){
            return ResponseVO.serviceFail("二维码生成失败");
        }
        return ResponseVO.Success(aliPayInfoVo,Constant.PRE_IMG);
    }


    @RequestMapping(value = "getPayResult", method = RequestMethod.POST)
    public ResponseVO getPayResult(@RequestParam("orderId")String orderId,
                                   @RequestParam(name = "tryNums", required = false, defaultValue = "1")Integer tryNums){
        String userId = CurrentUser.getUserId();
        if (StringUtils.isEmpty(userId)){
            return ResponseVO.serviceFail("用户未登录");
        }
        if (tryNums >= 4){      //重试超过四次
            return ResponseVO.serviceFail("订单支付失败，请稍后重试");
        }else{
            AliPayResultVo aliPayResultVo = aliPayServiceAPI.getOrderStatus(orderId);
            if (aliPayResultVo == null || ToolUtil.isEmpty(aliPayResultVo.getOrderId())){
                AliPayResultVo failOrder = new AliPayResultVo();
                failOrder.setOrderId(orderId);
                failOrder.setOrderStatus(0);
                failOrder.setOrderMsg("支付失败");
                return ResponseVO.Success(failOrder);
            }
            return ResponseVO.Success(aliPayResultVo);
        }

    }
}
