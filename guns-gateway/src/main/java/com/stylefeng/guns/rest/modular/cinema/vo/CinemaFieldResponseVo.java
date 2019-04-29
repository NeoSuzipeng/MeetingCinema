package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.cinema.vo.CinemaInfoVo;
import com.stylefeng.guns.api.cinema.vo.FilmInfoVo;
import com.stylefeng.guns.api.cinema.vo.HallInfoVo;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Su on 2019/4/29.
 *
 */
@Data
public class CinemaFieldResponseVo implements Serializable {

    private FilmInfoVo filmInfo;

    private CinemaInfoVo cinemaInfo;

    private HallInfoVo hallInfo;

}
