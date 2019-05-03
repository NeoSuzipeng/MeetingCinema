package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.api.cinema.vo.FilmInfoVo;
import com.stylefeng.guns.api.cinema.vo.HallInfoVo;
import com.stylefeng.guns.rest.common.persistence.model.MeetFieldT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author su
 * @since 2019-04-27
 */
public interface MeetFieldTMapper extends BaseMapper<MeetFieldT> {

    List<FilmInfoVo> getFilmInfosByCinemaId(@Param("cinemaId") int cinemaId);

    HallInfoVo getHallInfo(@Param("fieldId") int fieldId);

    FilmInfoVo getFilmInfoByFieldId(@Param("fieldId") int fieldId);

    //订单模块使用
    String getSeatsByFiledId(@Param("fieldId")int fieldId);
}
