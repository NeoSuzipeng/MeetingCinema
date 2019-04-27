package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.api.film.vo.ActorVo;
import com.stylefeng.guns.rest.common.persistence.model.MeetActorT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author su
 * @since 2019-04-25
 */
public interface MeetActorTMapper extends BaseMapper<MeetActorT> {
    List<ActorVo> getActors(@Param("filmId")String filmId);
}
