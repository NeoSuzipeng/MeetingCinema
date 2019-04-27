package com.stylefeng.guns.rest.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author su
 * @since 2019-04-27
 */
@TableName("meet_hall_film_info_t")
public class MeetHallFilmInfoT extends Model<MeetHallFilmInfoT> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "UUID", type = IdType.AUTO)
    private Integer uuid;
    @TableField("film_id")
    private Integer filmId;
    @TableField("film_name")
    private String filmName;
    @TableField("film_length")
    private String filmLength;
    @TableField("film_cats")
    private String filmCats;
    @TableField("film_language")
    private String filmLanguage;
    private String actors;
    @TableField("img_address")
    private String imgAddress;


    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmLength() {
        return filmLength;
    }

    public void setFilmLength(String filmLength) {
        this.filmLength = filmLength;
    }

    public String getFilmCats() {
        return filmCats;
    }

    public void setFilmCats(String filmCats) {
        this.filmCats = filmCats;
    }

    public String getFilmLanguage() {
        return filmLanguage;
    }

    public void setFilmLanguage(String filmLanguage) {
        this.filmLanguage = filmLanguage;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "MeetHallFilmInfoT{" +
        "uuid=" + uuid +
        ", filmId=" + filmId +
        ", filmName=" + filmName +
        ", filmLength=" + filmLength +
        ", filmCats=" + filmCats +
        ", filmLanguage=" + filmLanguage +
        ", actors=" + actors +
        ", imgAddress=" + imgAddress +
        "}";
    }
}
