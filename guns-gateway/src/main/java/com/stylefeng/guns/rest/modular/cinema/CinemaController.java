package com.stylefeng.guns.rest.modular.cinema;

import com.stylefeng.guns.api.cinema.vo.CinemaQueryVo;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by su on 2019/4/27.
 *
 */

@RequestMapping("/cinema/")
@RestController
public class CinemaController {

    /**
     * 通过查询条件查询对应影院实体
     * @param cinemaQueryVo 查询条件封装模型
     * @return 影院实体集合响应对象
     */
    @RequestMapping(value = "getCinemas", method = RequestMethod.GET)
    public ResponseVO getCinemas(CinemaQueryVo cinemaQueryVo){

        return null;
    }


    /**
     * 获取影院查询条件
     * @return
     */
    @RequestMapping(value = "getCondition", method = RequestMethod.GET)
    public ResponseVO getCondition(CinemaQueryVo cinemaQueryVo){

        return null;
    }

    /**
     * 获取影片的所有放映场次
     * @param cinemaId 影院ID
     * @return
     */
    @RequestMapping(value = "getFields", method = RequestMethod.GET)
    public ResponseVO getFields(Integer cinemaId){
        return null;
    }


    /**
     * 获取对应场次及影片的信息
     * @param cinemaId
     * @param fieldId
     * @return
     */
    @RequestMapping(value = "getFieldInfo", method = RequestMethod.GET)
    public ResponseVO getFieldInfo(Integer cinemaId, Integer fieldId){
        return null;
    }
}
