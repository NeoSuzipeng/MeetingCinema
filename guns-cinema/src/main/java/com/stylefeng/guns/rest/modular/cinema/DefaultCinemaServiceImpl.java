package com.stylefeng.guns.rest.modular.cinema;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.*;
import com.stylefeng.guns.rest.common.persistence.model.MeetCinemaT;

import java.util.List;

/**
 * Created by Su on 2019/4/27.
 * 影院模块服务实现
 */
public class DefaultCinemaServiceImpl implements CinemaServiceAPI {


    /**
     * 根据CinemaQueryVO，查询影院列表
     * @param cinemaQueryVo
     * @return
     */
    @Override
    public Page<CinemaVo> getCinemas(CinemaQueryVo cinemaQueryVo) {
        EntityWrapper<MeetCinemaT> entityWrapper = new EntityWrapper<>();
        return null;
    }

    /**
     * 根据条件获取品牌列表
     * @param brandId
     * @return
     */
    @Override
    public List<BrandVo> getBrands(int brandId) {
        return null;
    }

    /**
     * 根据条件获取区域列表
     * @param areaId
     * @return
     */
    @Override
    public List<AreaVo> getArea(int areaId) {
        return null;
    }

    /**
     * 获取影厅类型列表
     * @param hallType
     * @return
     */
    @Override
    public List<HalltypeVo> getHallType(int hallType) {
        return null;
    }

    /**
     * 根据影院编号，获取影院信息
     * @param cinemaId
     * @return
     */
    @Override
    public CinemaInfoVo getCinemaInfoById(int cinemaId) {
        return null;
    }

    /**
     * 获取所有电影的信息和对应的放映场次信息，根据影院编号
     * @param cinemaId
     * @return
     */
    @Override
    public FilmInfoVo getFilmInfoByCinemaId(int cinemaId) {
        return null;
    }

    /**
     * 根据放映场次ID获取放映信息
     * @param fieldId
     * @return
     */
    @Override
    public FilmFieldVo getFilmFieldInfo(int fieldId) {
        return null;
    }

    /**
     * 根据放映场次查询播放的电影编号，然后根据电影编号获取对应的电影信息
     * @param field
     * @return
     */
    @Override
    public FilmInfoVo getFilmInfoByFieldId(int field) {
        return null;
    }
}
