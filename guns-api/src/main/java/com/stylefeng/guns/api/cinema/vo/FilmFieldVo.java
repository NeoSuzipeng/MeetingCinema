package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Su on 2019/4/27.
 * 影片放映场次模型
 */
@Data
public class FilmFieldVo implements Serializable {

    private String fieldId;

    private String beginTime;

    private String endTime;

    private String language;

    private String hallName;

    private String price;

}
