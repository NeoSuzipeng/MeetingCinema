package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.FilmInfoVo;
import com.stylefeng.guns.api.cinema.vo.OrderCinemaVo;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderVo;
import com.stylefeng.guns.rest.common.persistence.dao.MeetOrderTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MeetOrderT;
import com.stylefeng.guns.rest.common.util.FTPUtil;
import com.stylefeng.guns.rest.common.util.UUIDUtil;
import com.stylefeng.guns.rest.modular.order.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Su on 2019/4/30.
 * 订单服务实现
 */
@Slf4j
@Component
@Service(interfaceClass = OrderServiceAPI.class, filter = "tracing")
public class DefaultOrderServiceImpl implements OrderServiceAPI {

    @Autowired
    private FTPUtil ftpUtil;

    @Autowired
    private MeetOrderTMapper meetOrderTMapper;

    @Reference(interfaceClass = CinemaServiceAPI.class, filter = "tracing")
    private CinemaServiceAPI cinemaServiceAPI;

    /**
     * 验证售出的票是否为真
     * @param fieldId 影厅ID
     * @param seats 用户选择未售座位
     * @return
     */
    @Override
    public boolean isTrueSeats(String fieldId, String seats, String seatLocation) {
        //根据FieldId找到位置图->Controller调用Cinema模块
        //根据位置图名称找到JSon文件
        String fileStrByAddress = ftpUtil.getFileStrByAddress(seatLocation);
        JSONObject jsonObject = JSONObject.parseObject(fileStrByAddress);
        String ids = jsonObject.get("ids").toString();

        String[] seatArray = seats.split(",");
        String[] seatIds = ids.split(",");
        //检查seats是否是正确ID
        boolean isTrueSeats = true;
        int count = 0;
        for (String seatId : seatIds) {
            for (String s : seatArray) {
                if (s.equalsIgnoreCase(seatId))     //选取座位与已有座位匹配
                    count++;
            }
        }
        if (seatArray.length != count)
            isTrueSeats = false;

        //检查seats是否出现重复
        boolean isRepetition = false;
        for(int i = 0; i < seatArray.length; i++){
            for (int j = 0; j < seatArray.length; j++) {
                if (i != j && seatArray[i].equalsIgnoreCase(seatArray[j])) {
                    isRepetition = true;
                    break;
                }
            }
        }
        return isTrueSeats && (!isRepetition);
    }

    /**
     * 用户选择得未售座位是否未被卖出
     * @param field 影厅ID
     * @param seats 用户选择未售座位
     * @return True：全部未卖出  False:一个以上已被卖出
     */
    @Override
    public boolean isNotSoldSeats(String field, String seats) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("field_id", field);
        entityWrapper.ne("order_status", Constant.PAY_CANCEL);
        List<MeetOrderT> meetOrderTs = meetOrderTMapper.selectList(entityWrapper);
        String[] seatArray = seats.split(",");      //用户选择座位

        for (MeetOrderT meetOrderT : meetOrderTs) {
            String[] soldSeatArray = meetOrderT.getSeatsIds().split(",");  //已售座位
            for (String soldSeat : soldSeatArray) {
                for (String choiceSeat : seatArray) {
                    if (choiceSeat.equalsIgnoreCase(soldSeat))
                        return false;            //用户选择已售座位
                }
            }
        }
        return true;
    }

    /**
     * 创建订单
     * @param fieldId
     * @param soldSeats
     * @param seatsName
     * @return
     */
    @Override
    public OrderVo createOrder(Integer fieldId, String soldSeats, String seatsName, Integer userId) {

        //条件1：验证售出的票是否为真
        String seatsLocation = cinemaServiceAPI.getSeatsLocationByFieldId(fieldId);
        boolean isTrue = isTrueSeats(soldSeats, fieldId+"", seatsLocation);
        //条件2：座位是否已销售
        boolean isNotSold = isNotSoldSeats(fieldId+"", soldSeats);

        if(isTrue && isNotSold){
            //UUID
            long uuid = UUIDUtil.getUuid();

            //获取影片信息
            FilmInfoVo filmInfoVo = cinemaServiceAPI.getFilmInfoByFieldId(fieldId);
            Integer filmId = Integer.parseInt(filmInfoVo.getFilmId());
            //获取影院信息
            OrderCinemaVo orderCinemaVo = cinemaServiceAPI.getOrderNeeds(fieldId);
            Integer cinemaId = orderCinemaVo.getCinemaId();
            double filmPrice = Double.parseDouble(orderCinemaVo.getFilmPrice());
            int totalCounts = soldSeats.split(",").length;
            double totalPrice = getTotalPrice(totalCounts, filmPrice);

            MeetOrderT newOrder = new MeetOrderT();
            newOrder.setUuid(uuid+"");
            newOrder.setSeatsName(seatsName);
            newOrder.setSeatsIds(soldSeats);
            newOrder.setCinemaId(cinemaId);
            newOrder.setFieldId(fieldId);
            newOrder.setFilmId(filmId);
            newOrder.setOrderUser(userId);
            newOrder.setFilmPrice(filmPrice);
            newOrder.setOrderPrice(totalPrice);

            int effectRows = meetOrderTMapper.insertOrder(newOrder);
            if (effectRows > 0){
                OrderVo order = meetOrderTMapper.getOrderInfoById(uuid+"");
                return order;
            }else {
                log.error("订单插入失败");
                return null;
            }
        }else {
            log.info("座位不存在");
            return null;
        }

    }



    private double getTotalPrice(int totalCounts, double filmPrice){
        BigDecimal totals = new BigDecimal(totalCounts);
        BigDecimal price = new BigDecimal(filmPrice);

        BigDecimal totalPrice = totals.multiply(price);

        totalPrice.setScale(2, RoundingMode.HALF_UP);
        return totalPrice.doubleValue();
    }

    /**
     * 获取当前用户订单信息
     * @param userId
     * @return 用户订单列表
     */
    @Override
    public Page<OrderVo> getOrderByUserId(Integer userId, Page<OrderVo> page) {
        Page<OrderVo> result = new Page<>();
        if (userId == null){
            log.error("订单查询失败,用户未知");
            return null;
        }
        List<OrderVo> orders = meetOrderTMapper.getOrderInfoByUserId(userId+"",page);
        if (orders == null || orders.size() == 0){
            result.setTotal(0);
            result.setRecords(new ArrayList<>());
            return result;
        }else{
            EntityWrapper<MeetOrderT> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("order_user", userId);
            Integer counts = meetOrderTMapper.selectCount(entityWrapper);
            result.setTotal(counts);
            result.setRecords(orders);
            return result;
        }

    }

    /**
     * 影院模块：为影厅提供已售座位信息
     * @param fieldId
     * @return
     */
    @Override
    public String getSoldSeatByFieldId(Integer fieldId) {
        if (fieldId == null){
            log.error("查询已售座位错误");
            return null;
        }else{
            String soldSeats =meetOrderTMapper.getSoldSeatsByFieldId(fieldId);
            return soldSeats;
        }
    }

    @Override
    public OrderVo getOrderInfoById(String orderId) {
        OrderVo orderVo = meetOrderTMapper.getOrderInfoById(orderId);
        return orderVo;
    }

    @Override
    public boolean paySuccess(String orderId) {

        MeetOrderT meetOrderT = new MeetOrderT();
        meetOrderT.setUuid(orderId);
        meetOrderT.setOrderStatus(Constant.PAY_SUCCESS);

        int effectRow = meetOrderTMapper.updateById(meetOrderT);
        return effectRow >= 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean payFail(String orderId) {
        MeetOrderT meetOrderT = new MeetOrderT();
        meetOrderT.setUuid(orderId);
        meetOrderT.setOrderStatus(Constant.PAY_FAIL);
        int effectRow = meetOrderTMapper.updateById(meetOrderT);
        return effectRow >= 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean cancel(String userId, String orderId) {
        MeetOrderT meetOrderT = meetOrderTMapper.selectById(orderId);
        if (meetOrderT != null){          //无此订单
            if (userId != null && userId.equals(meetOrderT.getOrderUser()+"")){  //越权检查
                if (meetOrderT.getOrderStatus() == Constant.PAY_FAIL){         //订单状态检查
                    MeetOrderT cancelOrder = new MeetOrderT();
                    cancelOrder.setUuid(orderId);
                    cancelOrder.setOrderStatus(Constant.PAY_CANCEL);
                    int effectRows = meetOrderTMapper.updateById(cancelOrder);
                    return effectRows >= 1 ? Boolean.TRUE : Boolean.FALSE;
                }

            }
        }
        return Boolean.FALSE;
    }


}
