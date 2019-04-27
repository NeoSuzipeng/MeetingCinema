package com.stylefeng.guns.api.film.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by su on 2019/4/24.
 * 热门电影模型
 *
 */
public class FilmVo implements Serializable{

    private int filmNum;

    private List<FilmInfoVo> filmInfo;

    private int nowPage;

    private int totalPage;

    public int getFilmNum() {
        return filmNum;
    }

    public void setFilmNum(int filmNum) {
        this.filmNum = filmNum;
    }

    public List<FilmInfoVo> getFilmInfo() {
        return filmInfo;
    }

    public void setFilmInfo(List<FilmInfoVo> filmInfo) {
        this.filmInfo = filmInfo;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
