package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Su on 2019/4/27.
 *
 */

@Data
public class AreaVo implements Serializable {

    private String areaId;

    private String areaName;

    private boolean isActive;

}
