package com.stylefeng.guns.api.film.vo;

import java.io.Serializable;

/**
 * Created by 10353 on 2019/4/26.
 *
 * 影片详情额外信息模型
 */
public class InfoRequestVo implements Serializable{

    private String biography;

    private ActorRequestVo actors;

    private ImgVo imgs;

    private String filmId;

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public ActorRequestVo getActors() {
        return actors;
    }

    public void setActors(ActorRequestVo actors) {
        this.actors = actors;
    }

    public ImgVo getImgs() {
        return imgs;
    }

    public void setImgs(ImgVo imgs) {
        this.imgs = imgs;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }
}
