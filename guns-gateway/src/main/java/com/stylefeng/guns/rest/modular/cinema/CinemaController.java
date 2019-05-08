package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.*;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaConditionResponseVo;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaFieldResponseVo;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaFieldsResponseVo;
import com.stylefeng.guns.rest.modular.film.constant.Constant;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by su on 2019/4/27.
 * 影院模块后端服务
 */

@Slf4j
@RequestMapping("/cinema/")
@RestController
public class CinemaController {


    @Reference(interfaceClass = CinemaServiceAPI.class, filter = "tracing")
    private CinemaServiceAPI cinemaServiceAPI;


    @Reference(interfaceClass = OrderServiceAPI.class, filter = "tracing")
    private OrderServiceAPI orderServiceAPI;

    /**
     * 通过查询条件查询对应影院实体
     * @param cinemaQueryVo 查询条件封装模型
     * @return 影院实体集合响应对象
     */
    @RequestMapping(value = "getCinemas", method = RequestMethod.GET)
    public ResponseVO getCinemas(CinemaQueryVo cinemaQueryVo){
        try{
            Page<CinemaVo> cinemas = cinemaServiceAPI.getCinemas(cinemaQueryVo);
            if (cinemas.getRecords() == null || cinemas.getRecords().size() == 0) {
                return ResponseVO.serviceFail("无可查影院");
            }else{
                return ResponseVO.Success(cinemas.getCurrent(), (int)cinemas.getPages(), cinemas.getRecords());
            }
        }catch (Exception e){
            log.error("获取影院列表异常", e);
            return ResponseVO.serviceFail("影院信息查询失败");
        }
    }


    /**
     * 获取影院查询条件
     * @return
     */
    @RequestMapping(value = "getCondition", method = RequestMethod.GET)
    public ResponseVO getCondition(CinemaQueryVo cinemaQueryVo){
        try{
            List<BrandVo> brandVos = cinemaServiceAPI.getBrands(cinemaQueryVo.getBrandId());
            List<AreaVo> areaVos = cinemaServiceAPI.getArea(cinemaQueryVo.getDistrictId());
            List<HalltypeVo> hallInfoVos = cinemaServiceAPI.getHallType(cinemaQueryVo.getHallType());

            CinemaConditionResponseVo cinemaConditionResponseVo = new CinemaConditionResponseVo();
            cinemaConditionResponseVo.setAreaList(areaVos);
            cinemaConditionResponseVo.setBrandList(brandVos);
            cinemaConditionResponseVo.setHalltypeList(hallInfoVos);

            return ResponseVO.Success(cinemaConditionResponseVo);
        }catch (Exception e){
            log.error("获取条件列表失败", e);
            return ResponseVO.serviceFail("获取条件列表失败");
        }

    }

    /**
     * 获取影片的所有放映场次
     * @param cinemaId 影院ID
     * @return
     */
    @RequestMapping(value = "getFields", method = RequestMethod.GET)
    public ResponseVO getFields(Integer cinemaId){
        try{
            CinemaInfoVo cinemaInfoVo = cinemaServiceAPI.getCinemaInfoById(cinemaId);
            List<FilmInfoVo> filmInfoVos = cinemaServiceAPI.getFilmInfoByCinemaId(cinemaId);

            CinemaFieldsResponseVo cinemaFieldsResponseVo = new CinemaFieldsResponseVo();
            cinemaFieldsResponseVo.setCinemaInfo(cinemaInfoVo);
            cinemaFieldsResponseVo.setFilmList(filmInfoVos);

            return ResponseVO.Success(cinemaFieldsResponseVo, Constant.PRE_IMG);
        }catch (Exception e){
            log.error("获取播放场次失败", e);
            return ResponseVO.serviceFail("获取播放场次失败");
        }
    }


    /**
     * 获取对应场次及影片的信息
     * @param cinemaId
     * @param fieldId
     * @return
     */
        @RequestMapping(value = "getFieldInfo", method = RequestMethod.POST)
    public ResponseVO getFieldInfo(Integer cinemaId, Integer fieldId){
        try{
            HallInfoVo hallInfoVo = cinemaServiceAPI.getFilmFieldInfo(fieldId);
            CinemaInfoVo cinemaInfoVo = cinemaServiceAPI.getCinemaInfoById(cinemaId);
            FilmInfoVo filmInfoVo = cinemaServiceAPI.getFilmInfoByFieldId(fieldId);

            //TODO 测试
            String soldSeats = orderServiceAPI.getSoldSeatByFieldId(fieldId);
            hallInfoVo.setSoldSeats(soldSeats);

            CinemaFieldResponseVo cinemaFieldResponseVo = new CinemaFieldResponseVo();
            cinemaFieldResponseVo.setCinemaInfo(cinemaInfoVo);
            cinemaFieldResponseVo.setFilmInfo(filmInfoVo);
            cinemaFieldResponseVo.setHallInfo(hallInfoVo);


            return ResponseVO.Success(cinemaFieldResponseVo, Constant.PRE_IMG);
        }catch (Exception e){
            log.error("查询特定场次失败", e);
            return ResponseVO.serviceFail("查询特定场次失败");
        }

    }
}
