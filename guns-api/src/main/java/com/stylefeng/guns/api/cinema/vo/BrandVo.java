package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Su on 2019/4/27.
 *
 */
@Data
public class BrandVo implements Serializable{

    private String brandId;

    private String brandName;

    private boolean isActive;

}
