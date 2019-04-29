package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.*;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.MeetAreaDictT;
import com.stylefeng.guns.rest.common.persistence.model.MeetBrandDictT;
import com.stylefeng.guns.rest.common.persistence.model.MeetCinemaT;
import com.stylefeng.guns.rest.common.persistence.model.MeetHallDictT;
import com.stylefeng.guns.rest.modular.cinema.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Su on 2019/4/27.
 * 影院模块服务实现
 */
@Service(interfaceClass = CinemaServiceAPI.class)
@Component
public class DefaultCinemaServiceImpl implements CinemaServiceAPI {

    @Autowired
    private MeetCinemaTMapper meetCinemaTMapper;

    @Autowired
    private MeetAreaDictTMapper meetAreaDictTMapper;

    @Autowired
    private MeetBrandDictTMapper meetBrandDictTMapper;

    @Autowired
    private MeetHallDictTMapper meetHallDictTMapper;

    @Autowired
    private MeetFieldTMapper meetFieldTMapper;

    /**
     * 根据CinemaQueryVO，查询影院列表
     * @param cinemaQueryVo
     * @return
     */
    @Override
    public Page<CinemaVo> getCinemas(CinemaQueryVo cinemaQueryVo) {

        Page<MeetCinemaT> page = new Page<>(cinemaQueryVo.getNowPage(), cinemaQueryVo.getPageSize());
        EntityWrapper<MeetCinemaT> entityWrapper = new EntityWrapper<>();
        //判断是否传入查询条件 brandId hallType districtId
        int brandId = cinemaQueryVo.getBrandId();
        int hallType = cinemaQueryVo.getHallType();
        int districtId = cinemaQueryVo.getDistrictId();
        if (!checkDefaultCondition(brandId)){
            entityWrapper.eq("brand_id", brandId);
        }
        if (!checkDefaultCondition(hallType)){
            entityWrapper.eq("area_id", hallType);
        }
        if (!checkDefaultCondition(districtId)){
            entityWrapper.eq("hall_ids", "%#"+districtId+"#%");
        }
        //数据实体转换为业务实体
        List<MeetCinemaT> meetCinemaTs = meetCinemaTMapper.selectPage(page, entityWrapper);
        List<CinemaVo> cinemaVos = new ArrayList<>();
        for (MeetCinemaT meetCinemaT : meetCinemaTs) {
            CinemaVo cinemaVo = new CinemaVo();

            cinemaVo.setUuid(meetCinemaT.getUuid()+"");
            cinemaVo.setAddress(meetCinemaT.getCinemaAddress());
            cinemaVo.setCinemaName(meetCinemaT.getCinemaName());
            cinemaVo.setMinimumPrice(meetCinemaT.getMinimumPrice()+"");

            cinemaVos.add(cinemaVo);
        }
        //分页
        long counts = meetCinemaTMapper.selectCount(entityWrapper);
        Page<CinemaVo> result = new Page<>();
        result.setRecords(cinemaVos);
        result.setSize(cinemaQueryVo.getPageSize());
        result.setTotal(counts);
        return result;
    }

    /**
     * 检查是否为默认查询条件
     * @param condition
     * @return
     */
    private boolean checkDefaultCondition(int condition){
        if(condition != Constant.DEFAULT_QUERY_CONDITION)
            return Boolean.FALSE;
        return Boolean.TRUE;
    }

    /**
     * 根据条件获取品牌列表
     * @param brandId
     * @return
     */
    @Override
    public List<BrandVo> getBrands(int brandId) {

        List<MeetBrandDictT> meetBrandDictTs = meetBrandDictTMapper.selectList(null);
        List<BrandVo> brandVos = new ArrayList<>();

        boolean conditionExist = false;       //判断查询条件是否存在数据库
        BrandVo defaultBrand = null;
        for (MeetBrandDictT meetBrandDictT : meetBrandDictTs) {
            BrandVo brandVo = new BrandVo();
            brandVo.setBrandId(meetBrandDictT.getUuid()+"");
            brandVo.setBrandName(meetBrandDictT.getShowName());
            if (meetBrandDictT.getUuid() == Constant.DEFAULT_QUERY_CONDITION)      //记录默认条件
                defaultBrand = brandVo;
            if (brandVo.getBrandId().equals(brandId+"")){      //存在查询条件IsActive置为True
                conditionExist = true;
                brandVo.setActive(Boolean.TRUE);
            }
            brandVos.add(brandVo);
        }

        if (!conditionExist && defaultBrand != null)    //不存在查询条件将默认条件实体IsActive置为True
            defaultBrand.setActive(Boolean.TRUE);
        return brandVos;
    }

    /**
     * 根据条件获取区域列表
     * @param areaId
     * @return
     */
    @Override
    public List<AreaVo> getArea(int areaId) {
        List<MeetAreaDictT> meetAreaDictTs = meetAreaDictTMapper.selectList(null);
        List<AreaVo> areaVos = new ArrayList<>();

        boolean conditionExist = false;
        AreaVo defaultArea = null;
        for (MeetAreaDictT meetAreaDictT : meetAreaDictTs) {
            AreaVo areaVo = new AreaVo();
            areaVo.setAreaId(meetAreaDictT.getUuid()+"");
            areaVo.setAreaName(meetAreaDictT.getShowName());
            if (meetAreaDictT.getUuid() == Constant.DEFAULT_QUERY_CONDITION)
                defaultArea = areaVo;
            if (areaVo.getAreaId().equals(areaId+"")){
                conditionExist = true;
                areaVo.setActive(Boolean.TRUE);
            }
            areaVos.add(areaVo);
        }
        if (!conditionExist && defaultArea != null)
            defaultArea.setActive(Boolean.TRUE);
        return areaVos;
    }

    /**
     * 获取影厅类型列表
     * @param hallType
     * @return
     */
    @Override
    public List<HalltypeVo> getHallType(int hallType) {
        List<MeetHallDictT> meetHallDictTs = meetHallDictTMapper.selectList(null);
        List<HalltypeVo> halltypeVos = new ArrayList<>();

        boolean conditionExist = false;
        HalltypeVo defaultHallType = null;

        for (MeetHallDictT meetHallDictT : meetHallDictTs) {
            HalltypeVo halltypeVo = new HalltypeVo();
            halltypeVo.setHalltypeId(meetHallDictT.getUuid()+"");
            halltypeVo.setHalltypeName(meetHallDictT.getShowName());
            if (meetHallDictT.getUuid() == Constant.DEFAULT_QUERY_CONDITION)
                defaultHallType = halltypeVo;
            if (halltypeVo.getHalltypeId().equals(hallType+"")){
                conditionExist = true;
                halltypeVo.setActive(Boolean.TRUE);
            }
            halltypeVos.add(halltypeVo);
        }
        if (!conditionExist && defaultHallType != null)
            defaultHallType.setActive(Boolean.TRUE);
        return halltypeVos;
    }

    /**
     * 根据影院编号，获取影院信息
     * @param cinemaId
     * @return
     */
    @Override
    public CinemaInfoVo getCinemaInfoById(int cinemaId) {
        MeetCinemaT meetCinemaT = meetCinemaTMapper.selectById(cinemaId);

        CinemaInfoVo cinemaInfoVo = new CinemaInfoVo();
        cinemaInfoVo.setCinemaAdress(meetCinemaT.getCinemaAddress());
        cinemaInfoVo.setCinemaId(meetCinemaT.getUuid()+"");
        cinemaInfoVo.setCinemaName(meetCinemaT.getCinemaName());
        cinemaInfoVo.setCinemaPhone(meetCinemaT.getCinemaPhone());

        return cinemaInfoVo;
    }

    /**
     * 获取所有电影的信息和对应的放映场次信息，根据影院编号
     * @param cinemaId
     * @return
     */
    @Override
    public List<FilmInfoVo> getFilmInfoByCinemaId(int cinemaId) {
        List<FilmInfoVo> filmInfos = meetFieldTMapper.getFilmInfosByCinemaId(cinemaId);
        return filmInfos;
    }

    /**
     * 根据放映场次ID获取放映信息
     * @param fieldId
     * @return
     */
    @Override
    public HallInfoVo getFilmFieldInfo(int fieldId) {
        HallInfoVo hallInfoVo = meetFieldTMapper.getHallInfo(fieldId);
        return hallInfoVo;
    }

    /**
     * 根据放映场次查询播放的电影编号，然后根据电影编号获取对应的电影信息
     * @param fieldId
     * @return
     */
    @Override
    public FilmInfoVo getFilmInfoByFieldId(int fieldId) {
        FilmInfoVo filmInfoVo = meetFieldTMapper.getFilmInfoByFieldId(fieldId);
        return filmInfoVo;
    }
}
