package com.stylefeng.guns.rest.modular.film.vo;

import com.stylefeng.guns.api.film.vo.BannerVo;
import com.stylefeng.guns.api.film.vo.FilmInfoVo;
import com.stylefeng.guns.api.film.vo.FilmVo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by su on 2019/4/24.
 * 前端首页交互模型
 */
public class FilmIndexVo implements Serializable{

    private List<BannerVo> banners;

    private FilmVo hotFilms;

    private FilmVo soonFilms;

    private List<FilmInfoVo> boxRanking;

    private List<FilmInfoVo> expectRanking;

    private List<FilmInfoVo> top100;

    public List<BannerVo> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerVo> banners) {
        this.banners = banners;
    }

    public List<FilmInfoVo> getTop100() {
        return top100;
    }

    public void setTop100(List<FilmInfoVo> top100) {
        this.top100 = top100;
    }

    public List<FilmInfoVo> getExpectRanking() {
        return expectRanking;
    }

    public void setExpectRanking(List<FilmInfoVo> expectRanking) {
        this.expectRanking = expectRanking;
    }

    public List<FilmInfoVo> getBoxRanking() {
        return boxRanking;
    }

    public void setBoxRanking(List<FilmInfoVo> boxRanking) {
        this.boxRanking = boxRanking;
    }

    public FilmVo getSoonFilms() {
        return soonFilms;
    }

    public void setSoonFilms(FilmVo soonFilms) {
        this.soonFilms = soonFilms;
    }

    public FilmVo getHotFilms() {
        return hotFilms;
    }

    public void setHotFilms(FilmVo hotFilms) {
        this.hotFilms = hotFilms;
    }
}
