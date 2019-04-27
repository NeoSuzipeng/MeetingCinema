package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.api.film.vo.FilmDetailVo;
import com.stylefeng.guns.rest.common.persistence.model.MeetFilmT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author su
 * @since 2019-04-25
 */
public interface MeetFilmTMapper extends BaseMapper<MeetFilmT> {

    FilmDetailVo getFilmDetailByName(@Param("filmName")String filmName);

    FilmDetailVo getFilmDetailById(@Param("uuid")String uuid);

}
