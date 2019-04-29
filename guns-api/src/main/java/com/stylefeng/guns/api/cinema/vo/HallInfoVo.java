package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Su on 2019/4/27.
 *
 */

@Data
public class HallInfoVo implements Serializable {

    private String hallFieldId;

    private String hallName;

    private String price;

    private String seatFile;

    private String soldSeats;      //订单信息中查询

}
