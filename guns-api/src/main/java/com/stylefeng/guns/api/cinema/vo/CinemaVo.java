package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by su on 2019/4/27.
 *
 */
@Data
public class CinemaVo implements Serializable{

    private String uuid;

    private String cinemaName;

    private String address;

    private String minimumPrice;

}
