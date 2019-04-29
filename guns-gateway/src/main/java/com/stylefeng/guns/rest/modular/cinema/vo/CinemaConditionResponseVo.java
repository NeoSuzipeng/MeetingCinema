package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.cinema.vo.AreaVo;
import com.stylefeng.guns.api.cinema.vo.BrandVo;
import com.stylefeng.guns.api.cinema.vo.HalltypeVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 10353 on 2019/4/29.
 *
 */

@Data
public class CinemaConditionResponseVo implements Serializable {

    private List<BrandVo> brandList;

    private List<AreaVo> areaList;

    private List<HalltypeVo> halltypeList;
}
