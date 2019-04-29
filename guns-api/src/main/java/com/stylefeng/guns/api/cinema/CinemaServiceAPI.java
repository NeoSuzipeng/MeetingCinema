package com.stylefeng.guns.api.cinema;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.vo.*;

import java.util.List;


/**
 * Created by su on 2019/4/27.
 * 影院服务接口
 */
public interface CinemaServiceAPI {

    //1、根据CinemaQueryVO，查询影院列表
    Page<CinemaVo> getCinemas(CinemaQueryVo cinemaQueryVo);

    //2、根据条件获取品牌列表
    List<BrandVo> getBrands(int brandId);

    //3、获取行政区域列表
    List<AreaVo> getArea(int areaId);

    //4、获取影厅类型列表
    List<HalltypeVo> getHallType(int hallType);

    //5、根据影院编号，获取影院信息
    CinemaInfoVo getCinemaInfoById(int cinemaId);

    //6、获取所有电影的信息和对应的放映场次信息，根据影院编号
    List<FilmInfoVo> getFilmInfoByCinemaId(int cinemaId);

    //7、根据放映场次ID获取放映信息
    HallInfoVo getFilmFieldInfo(int fieldId);

    //8、根据放映场次查询播放的电影编号，然后根据电影编号获取对应的电影信息
    FilmInfoVo getFilmInfoByFieldId(int field);


}
