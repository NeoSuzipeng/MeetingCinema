package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by su on 2019/4/25.
 *
 */

@Component
@Service(interfaceClass = FilmServiceAPI.class, filter = "tracing")
public class DefaultFilmServiceImpl implements FilmServiceAPI{

    @Autowired
    private MeetBannerTMapper meetBannerTMapper;

    @Autowired
    private MeetFilmTMapper meetFilmTMapper;

    @Autowired
    private MeetCatDictTMapper meetCatDictTMapper;

    @Autowired
    private MeetYearDictTMapper meetYearDictTMapper;

    @Autowired
    private MeetSourceDictTMapper meetSourceDictTMapper;

    @Autowired
    private MeetFilmInfoTMapper meetFilmInfoTMapper;

    @Autowired
    private MeetActorTMapper meetActorTMapper;



    @Override
    public List<BannerVo> getBanners() {

        //查詢Banner所有信息
        List<MeetBannerT> meetBannerTs = meetBannerTMapper.selectList(null);
        //組裝Banner信息
        List<BannerVo> banners = new ArrayList<>();
        for(MeetBannerT meetBannerT : meetBannerTs){
            BannerVo banner = new BannerVo();
            banner.setBannerId(meetBannerT.getUuid()+"");
            banner.setBannerAddress(meetBannerT.getBannerAddress());
            banner.setBannerUrl(meetBannerT.getBannerUrl());
            banners.add(banner);
        }
        return banners;
    }

    @Override
    public FilmVo getHotFilms(boolean limit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {

        FilmVo filmVo = new FilmVo();
        List<FilmInfoVo> filmInfoVos = new ArrayList<>();

        //热映影片的限制条件
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("film_status", "1");

        //判斷是否为首页内容
        if (limit){
            //如果是，则是首页限制条数
            Page<MeetFilmT> hotPage = new Page<>(1, nums);
            List<MeetFilmT> meetFilmTs = meetFilmTMapper.selectPage(hotPage, entityWrapper);


            //组装List<FilmInfoVo>
            filmInfoVos = getFilmInfoVos(meetFilmTs);
            filmVo.setFilmNum(meetFilmTs.size());
        }else{
            //如果不是，则是列表页

            //1-按热门搜索，2-按时间搜索，3-按评价搜索
            Page<MeetFilmT> hotPage = null;
            switch(sortId){
                case 1:
                    hotPage = new Page<>(nowPage, nums, "film_box_office");
                    break;
                case 2:
                    hotPage = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3:
                    hotPage = new Page<>(nowPage, nums, "film_score");
                    break;
                default:
                    hotPage = new Page<>(nowPage, nums, "film_box_office");

            }

            if(sourceId != 99){
                entityWrapper.eq("film_source", sourceId);
            }
            if (yearId != 99){
                entityWrapper.eq("film_date", yearId);
            }
            if (catId != 99){
                //#2#3#4#
                String catStr = "#"+catId+"#";
                entityWrapper.eq("film_cats", catStr);

            }
            List<MeetFilmT> meetFilmTs = meetFilmTMapper.selectPage(hotPage, entityWrapper);


            //组装List<FilmInfoVo>
            filmInfoVos = getFilmInfoVos(meetFilmTs);
            filmVo.setFilmNum(meetFilmTs.size());

            //总页数
            int totalHotCounts = meetFilmTMapper.selectCount(entityWrapper);
            int totalHotPages = (totalHotCounts % nums == 0) ? (totalHotCounts/nums): (totalHotCounts/nums) + 1;
            filmVo.setNowPage(nowPage);
            filmVo.setTotalPage(totalHotPages);

        }
        filmVo.setFilmInfo(filmInfoVos);
        return filmVo;
    }

    private List<FilmInfoVo> getFilmInfoVos(List<MeetFilmT> meetFilmTs){
        List<FilmInfoVo> filmInfoVos = new ArrayList<>();
        for (MeetFilmT meetFilmT : meetFilmTs){
            FilmInfoVo filmInfoVo = new FilmInfoVo();
            filmInfoVo.setBoxNum(meetFilmT.getFilmBoxOffice());
            filmInfoVo.setExpectNum(meetFilmT.getFilmPresalenum()); //影片期望
            filmInfoVo.setFilmId(meetFilmT.getUuid()+"");
            filmInfoVo.setFilmName(meetFilmT.getFilmName());
            filmInfoVo.setFilmScore(meetFilmT.getFilmScore());
            filmInfoVo.setFilmType(meetFilmT.getFilmType());
            filmInfoVo.setImgAddress(meetFilmT.getImgAddress());
            filmInfoVo.setScore(meetFilmT.getFilmScore());
            filmInfoVo.setShowTime(DateUtil.getDay(meetFilmT.getFilmTime()));
            filmInfoVos.add(filmInfoVo);
        }
        return filmInfoVos;
    }

    @Override
    public FilmVo getSoonFilms(boolean limit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVo filmVo = new FilmVo();
        List<FilmInfoVo> filmInfoVos = new ArrayList<>();

        //即将上映影片的限制条件
        EntityWrapper entitySoonFilmWrapper = new EntityWrapper();
        entitySoonFilmWrapper.eq("film_status",  "2");

        //判斷是否为首页内容
        if (limit){
            //如果是则是首页限制条数
            Page<MeetFilmT> soonPage = new Page<>(1, nums);
            List<MeetFilmT> meetSoonFilmTs = meetFilmTMapper.selectPage(soonPage, entitySoonFilmWrapper);
            //组装List<FilmInfoVo>
            filmInfoVos = getFilmInfoVos(meetSoonFilmTs);
            filmVo.setFilmNum(meetSoonFilmTs.size());
        }else{
            //如果不是，则是列表页
            Page<MeetFilmT> soonPage = null;
            //1-按热门搜索，2-按时间搜索，3-按评价搜索
            switch(sortId){
                case 1:
                    soonPage = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
                case 2:
                    soonPage = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3:
                    soonPage = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
                default:
                    soonPage = new Page<>(nowPage, nums, "film_preSaleNum");

            }

            if(sourceId != 99){
                entitySoonFilmWrapper.eq("film_source", sourceId);
            }
            if (yearId != 99){
                entitySoonFilmWrapper.eq("film_date", yearId);
            }
            if (catId != 99){
                //#2#3#4#
                String catStr = "#"+catId+"#";
                entitySoonFilmWrapper.eq("film_cats", catStr);

            }
            List<MeetFilmT> meetFilmTs = meetFilmTMapper.selectPage(soonPage, entitySoonFilmWrapper);


            //组装List<FilmInfoVo>
            filmInfoVos = getFilmInfoVos(meetFilmTs);
            filmVo.setFilmNum(meetFilmTs.size());

            //总页数
            int totalSoonCounts = meetFilmTMapper.selectCount(entitySoonFilmWrapper);
            int totalSoonPages = (totalSoonCounts % nums == 0) ? (totalSoonCounts/nums): (totalSoonCounts/nums) + 1;
            filmVo.setNowPage(nowPage);
            filmVo.setTotalPage(totalSoonPages);
        }
        filmVo.setFilmInfo(filmInfoVos);
        return filmVo;
    }

    @Override
    public FilmVo getClassicFilms(int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVo filmVo = new FilmVo();
        List<FilmInfoVo> filmInfoVos = new ArrayList<>();

        //即将上映影片的限制条件
        EntityWrapper entityClassicFilmWrapper = new EntityWrapper();
        entityClassicFilmWrapper.eq("film_status", "3");

        Page<MeetFilmT> classicPage = new Page<>(nowPage, nums);

        //1-按热门搜索，2-按时间搜索，3-按评价搜索
        switch (sortId) {
            case 1:
                classicPage = new Page<>(nowPage, nums, "film_score");
                break;
            case 2:
                classicPage = new Page<>(nowPage, nums, "film_time");
                break;
            case 3:
                classicPage = new Page<>(nowPage, nums, "film_score");
                break;
            default:
                classicPage = new Page<>(nowPage, nums, "film_box_office");

        }

        if (sourceId != 99) {
            entityClassicFilmWrapper.eq("film_source", sourceId);
        }
        if (yearId != 99) {
            entityClassicFilmWrapper.eq("film_date", yearId);
        }
        if (catId != 99) {
            //#2#3#4#
            String catStr = "#" + catId + "#";
            entityClassicFilmWrapper.eq("film_cats", catStr);

        }
        List<MeetFilmT> meetFilmTs = meetFilmTMapper.selectPage(classicPage, entityClassicFilmWrapper);


        //组装List<FilmInfoVo>
        filmInfoVos = getFilmInfoVos(meetFilmTs);
        filmVo.setFilmNum(meetFilmTs.size());

        //总页数
        int totalSoonCounts = meetFilmTMapper.selectCount(entityClassicFilmWrapper);
        int totalSoonPages = (totalSoonCounts % nums == 0) ? (totalSoonCounts / nums) : (totalSoonCounts / nums) + 1;
        filmVo.setNowPage(nowPage);
        filmVo.setTotalPage(totalSoonPages);
        filmVo.setFilmInfo(filmInfoVos);
        return filmVo;
    }

    @Override
    public List<FilmInfoVo> getBoxRanking() {
        //条件：正在上映的票房前十名
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("film_status", "1");

        Page<MeetFilmT> page = new Page<>(1, 10, "film_box_office");
        List<MeetFilmT> meetFilmTs = meetFilmTMapper.selectPage(page, entityWrapper);

        List<FilmInfoVo> filmInfoVos = getFilmInfoVos(meetFilmTs);

        return filmInfoVos;
    }

    @Override
    public List<FilmInfoVo> getExpectRanking() {
        //条件：即将上映的预售前十名
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("film_status", "2");


        Page<MeetFilmT> page = new Page<>(1, 10, "film_preSaleNum");
        List<MeetFilmT> meetFilmTs = meetFilmTMapper.selectPage(page, entityWrapper);

        List<FilmInfoVo> filmInfoVos = getFilmInfoVos(meetFilmTs);

        return filmInfoVos;
    }

    @Override
    public List<FilmInfoVo> getTop() {
        //条件：评分前十名
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("film_status", "1");


        Page<MeetFilmT> page = new Page<>(1, 10, "film_score");
        List<MeetFilmT> meetFilmTs = meetFilmTMapper.selectPage(page, entityWrapper);

        List<FilmInfoVo> filmInfoVos = getFilmInfoVos(meetFilmTs);

        return filmInfoVos;
    }

    /**
     * 获取影片类别条件集合
     * @return 影片类别条件集合
     */
    @Override
    public List<CatVo> getCats() {
        List<CatVo> catVos = new ArrayList<>();
        List<MeetCatDictT> meetCatDictTs = meetCatDictTMapper.selectList(null); //查询全部类型字典

        for (MeetCatDictT meetCatDictT : meetCatDictTs) {
            CatVo catVo = new CatVo();

            catVo.setCatId(meetCatDictT.getUuid() + "");
            catVo.setCatName(meetCatDictT.getShowName());
            catVos.add(catVo);
        }
        return catVos;
    }

    /**
     * 获取片源条件集合
     * @return 片源条件集合
     */
    @Override
    public List<SourceVo> getSources() {
        List<SourceVo> sourceVos = new ArrayList<>();
        List<MeetSourceDictT> meetSourceDictTs = meetSourceDictTMapper.selectList(null);

        for (MeetSourceDictT meetSourceDictT : meetSourceDictTs) {
            SourceVo sourceVo = new SourceVo();
            sourceVo.setSourceId(meetSourceDictT.getUuid() + "");
            sourceVo.setSourceName(meetSourceDictT.getShowName());
            sourceVos.add(sourceVo);
        }
        return sourceVos;
    }

    /**
     * 获取年代条件集合
     * @return 年代条件集合
     */
    @Override
    public List<YearVo> getYears() {
        List<YearVo> yearVos = new ArrayList<>();
        List<MeetYearDictT> meetYearDictTs = meetYearDictTMapper.selectList(null);

        for (MeetYearDictT meetYearDictT : meetYearDictTs) {
            YearVo yearVo = new YearVo();
            yearVo.setYearId(meetYearDictT.getUuid() + "");
            yearVo.setYearName(meetYearDictT.getShowName());
            yearVos.add(yearVo);
        }
        return yearVos;
    }

    @Override
    public FilmDetailVo getFilmDetail(int searchType, String searchParam) {

        FilmDetailVo filmDetailVo = null;
        // 根据searchType判断查询类型
        // 0表示按照编号查找，1表示按照名称查找, 默认按照编号查询
        switch (searchType){
            case 0:
                filmDetailVo = meetFilmTMapper.getFilmDetailById(searchParam);
                break;
            case 1:
                filmDetailVo = meetFilmTMapper.getFilmDetailByName("%"+searchParam+"%");
                break;
            default:
                filmDetailVo = meetFilmTMapper.getFilmDetailById(searchParam);

        }

        return filmDetailVo;
    }

    /**
     * 获取影片介绍
     * @param filmId 影片ID
     * @return 影片描述
     */
    @Override
    public String getFilmDesc(String filmId) {
        MeetFilmInfoT meetFilmInfoT = getMeetFilmInfo(filmId);
        return meetFilmInfoT.getBiography();
    }

    @Override
    public List<ActorVo> getActors(String filmId) {
        List<ActorVo> actorVos = meetActorTMapper.getActors(filmId);
        return actorVos;
    }

    @Override
    public ActorVo getDirector(String filmId) {
        MeetFilmInfoT meetFilmInfoT = getMeetFilmInfo(filmId);
        int directorId = meetFilmInfoT.getDirectorId();
        MeetActorT meetActorT = meetActorTMapper.selectById(directorId);
        ActorVo director = new ActorVo();
        director.setImgAddress(meetActorT.getActorImg());
        director.setDirectorName(meetActorT.getActorName());
        return director;
    }

    @Override
    public ImgVo getImgs(String filmId) {
        MeetFilmInfoT meetFilmInfoT = getMeetFilmInfo(filmId);
        String filmStr = meetFilmInfoT.getFilmImgs();
        String[] filmImgs = filmStr.split(",");
        ImgVo imgVo = new ImgVo();
        imgVo.setMainImg(filmImgs[0]);
        imgVo.setImg01(filmImgs[1]); //主图
        imgVo.setImg02(filmImgs[2]);
        imgVo.setImg03(filmImgs[3]);
        imgVo.setImg04(filmImgs[4]);
        return imgVo;
    }

    /**
     * 代码重用
     * @return
     */
    private MeetFilmInfoT getMeetFilmInfo(String filmId){
        MeetFilmInfoT meetFilmInfoT = new MeetFilmInfoT();
        meetFilmInfoT.setFilmId(filmId);     //设置查询属性
        meetFilmInfoT = meetFilmInfoTMapper.selectOne(meetFilmInfoT);
        return meetFilmInfoT;
    }
}
