package com.stylefeng.guns.rest.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.order.vo.OrderVo;
import com.stylefeng.guns.rest.common.persistence.model.MeetOrderT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author su
 * @since 2019-04-30
 */
public interface MeetOrderTMapper extends BaseMapper<MeetOrderT> {

    OrderVo getOrderInfoById(@Param("uuid") String uuid);

    List<OrderVo> getOrderInfoByUserId(@Param("userId") String userId, Page<OrderVo> page);

    String getSoldSeatsByFieldId(@Param("fieldId")Integer fieldId);
}
