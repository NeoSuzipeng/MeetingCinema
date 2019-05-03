package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by 10353 on 2019/4/30.
 * 订单所需影院信息
 */
@Data
public class OrderCinemaVo  implements Serializable{

    private Integer cinemaId;

    private String filmPrice;
}
