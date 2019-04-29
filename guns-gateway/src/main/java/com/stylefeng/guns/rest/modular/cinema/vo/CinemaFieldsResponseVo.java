package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.cinema.vo.CinemaInfoVo;
import com.stylefeng.guns.api.cinema.vo.FilmFieldVo;
import com.stylefeng.guns.api.cinema.vo.FilmInfoVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Su on 2019/4/29.
 *
 */

@Data
public class CinemaFieldsResponseVo implements Serializable {

    private CinemaInfoVo cinemaInfo;

    private List<FilmInfoVo> filmList;

}
