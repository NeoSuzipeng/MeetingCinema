package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by 10353 on 2019/4/27.
 * 影院查询参数模型
 */
@Data
public class CinemaQueryVo implements Serializable{

    private Integer brandId = 99;

    private Integer hallType = 99;

    private Integer districtId = 99;

    private Integer pageSize = 12;

    private Integer nowPage = 1;

}
