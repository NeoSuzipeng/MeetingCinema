package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Su on 2019/4/27.
 * 影院所需影片模型
 */
@Data
public class FilmInfoVo implements Serializable {

    private String filmId;

    private String filmName;

    private String filmLength;

    private String filmType;

    private String filmCats;

    private String actors;

    private String imgAddress;

    private List<FilmFieldVo> filmFieldVos;

}
