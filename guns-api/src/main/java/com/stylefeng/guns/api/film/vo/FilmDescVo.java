package com.stylefeng.guns.api.film.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by su on 2019/4/26.
 * 影片描述信息模型
 *
 */
public class FilmDescVo implements Serializable{

    private String biography;

    private String filmId;

    private ImgVo imgs;

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public ImgVo getImgs() {
        return imgs;
    }

    public void setImgs(ImgVo imgs) {
        this.imgs = imgs;
    }

//    private List<ActorVo> actors;

}
