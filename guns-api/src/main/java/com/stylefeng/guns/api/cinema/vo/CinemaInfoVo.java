package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Su on 2019/4/27.
 * 影院信息模型
 */
@Data
public class CinemaInfoVo implements Serializable {

    private String cinemaId;

    private String imgUrl;

    private String cinemaName;

    private String cinemaAdress;

    private String cinemaPhone;

}
