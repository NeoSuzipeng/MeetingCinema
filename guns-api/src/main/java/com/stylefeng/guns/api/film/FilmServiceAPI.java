package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

/**
 * Created by su on 2019/4/25.
 *
 * 影片模块API接口
 */
public interface FilmServiceAPI {

    //===============影片首页接口===========
    //获取Banners
    List<BannerVo> getBanners();

    //获取热映影片
    FilmVo getHotFilms(boolean limit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId);

    //获取即将上映影片【受欢迎程度】
    FilmVo getSoonFilms(boolean limit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId);  // 参数用于接口重用

    //获取经典电影
    FilmVo getClassicFilms(int nums, int nowPage, int sortId, int sourceId, int yearId, int catId);  // 参数用于接口重用

    //获取票房排行榜
    List<FilmInfoVo> getBoxRanking();

    //获取人气排行榜
    List<FilmInfoVo> getExpectRanking ();

    //获取Top100
    List<FilmInfoVo> getTop();

    //==============影片条件接口==============
    //分类条件
    List<CatVo> getCats();

    //片源条件
    List<SourceVo> getSources();

    //年代条件
    List<YearVo> getYears();

    //获取影片主体信息
    FilmDetailVo getFilmDetail(int searchType, String searchParam);

    //获取影片其他信息 【演员表】 【图片地址】等


    //获取影片描述信息
    String getFilmDesc(String filmId);

    //获取演员信息
    List<ActorVo> getActors(String filmId);

    //获取导演信息
    ActorVo getDirector(String filmId);

    //获取图片信息
    ImgVo getImgs(String filmId);
}
