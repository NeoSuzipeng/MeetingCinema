package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Su on 2019/4/27.
 *
 */

@Data
public class HalltypeVo implements Serializable {

    private String halltypeId;

    private String halltypeName;

    private boolean isActive;

}
