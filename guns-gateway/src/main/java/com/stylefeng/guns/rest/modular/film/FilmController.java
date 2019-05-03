package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmServiceAPI;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.rest.modular.film.constant.Constant;
import com.stylefeng.guns.rest.modular.film.vo.*;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by su on 2019/4/24.
 *
 */

@RequestMapping("/film/")
@RestController
public class FilmController {


    @Reference(interfaceClass = FilmServiceAPI.class)
    private FilmServiceAPI filmServiceAPI;
    /**
     * 获取首页信息接口
     * 多服务聚合
     * @return
     */
    @RequestMapping(value = "getIndex",method = RequestMethod.GET)
    public ResponseVO getIndex(){

        FilmIndexVo filmIndexVo = new FilmIndexVo();
        // 获取banner信息
        filmIndexVo.setBanners(filmServiceAPI.getBanners());

        // 获取正在热映电影
        filmIndexVo.setHotFilms(filmServiceAPI.getHotFilms(true, Constant.INDEX_FILM_NUM, 1, 1, 99, 99, 99));

        // 获取即将上映电影
        filmIndexVo.setSoonFilms(filmServiceAPI.getSoonFilms(true, Constant.INDEX_FILM_NUM, 1, 1, 99, 99, 99));

        // 票房排行榜
        filmIndexVo.setBoxRanking(filmServiceAPI.getBoxRanking());

        // 人气榜
        filmIndexVo.setExpectRanking(filmServiceAPI.getExpectRanking());

        // Top100榜单
        filmIndexVo.setTop100(filmServiceAPI.getTop());

        return ResponseVO.Success(filmIndexVo, Constant.PRE_IMG);
    }


    @RequestMapping(value = "getConditionList", method = RequestMethod.GET)
    public ResponseVO getConditionList(@RequestParam(name = "catId", required = false, defaultValue = "99") String catId,
                                       @RequestParam(name = "sourceId", required = false, defaultValue = "99") String sourceId,
                                       @RequestParam(name = "yearId", required = false, defaultValue = "99") String yearId){
        FilmConditionVO filmConditionVO = new FilmConditionVO();
        //类型集合
           //判断集合是否存在catId,如何存在则将对应实体的IsActive置为True,否则将默认"全部"True
        List<CatVo> cats = filmServiceAPI.getCats();
        CatVo defaultCat = null;    //记录默认条件,注必然存在

        //注:默认条件实体必然存在
        //遍历集合->如果存在可匹配的CatId则IsActive=True且catExist标识为True
        //       ->如果不存在,则将记录的defaultCat的IsActive置为True
        boolean catExist = false;
        for (CatVo cat : cats){
            if (cat.getCatId().equals(Constant.DEFAULT_CONDITION)){
                defaultCat = cat;           //记录默认条件实体
            }
            if (cat.getCatId().equals(catId)){      //匹配到catId对应实体
                catExist = true;
                cat.setActive(true);
            }

        }
        if(!catExist && defaultCat != null)        //如果匹配到对应实体则无需任何操作，如果匹配不到则默认实体IsActive为True
            defaultCat.setActive(true);

        //片源集合
        //逻辑同上
        List<SourceVo> sourceVos = filmServiceAPI.getSources();
        SourceVo defaultSource = null;
        boolean sourceExist = false;
        for (SourceVo sourceVo : sourceVos){
            if (sourceVo.getSourceId().equals(Constant.DEFAULT_CONDITION)){
                defaultSource = sourceVo;
            }
            if (sourceVo.getSourceId().equals(sourceId)){
                sourceExist = true;
                sourceVo.setActive(true);
            }
        }
        if(!sourceExist && defaultSource != null)
            defaultSource.setActive(true);

        //年代集合
        List<YearVo> yearVos = filmServiceAPI.getYears();
        YearVo defaultYear = null;
        boolean yearExist = false;
        for (YearVo yearVo : yearVos){
            if (yearVo.getYearId().equals(Constant.DEFAULT_CONDITION)){
                defaultYear = yearVo;
            }
            if (yearVo.getYearId().equals(yearId)){
                yearExist = true;
                yearVo.setActive(true);
            }
        }
        if(!yearExist && defaultYear != null)
            defaultYear.setActive(true);

        filmConditionVO.setCatInfo(cats);
        filmConditionVO.setSourceInfo(sourceVos);
        filmConditionVO.setYearInfo(yearVos);
        return ResponseVO.Success(filmConditionVO);
    }


    @RequestMapping(value = "getFilms", method = RequestMethod.GET)
    public ResponseVO getFilms(FilmRequestVo filmRequestVo){

        FilmVo filmVo = null;
        //提取条件属性
        int showType = filmRequestVo.getShowType();
        int sortId = filmRequestVo.getSortId();
        int nowPage = filmRequestVo.getNowPage();
        int pageSize = filmRequestVo.getPageSize();
        int catId = filmRequestVo.getCatId();
        int sourceId = filmRequestVo.getSourceId();
        int yearId = filmRequestVo.getYearId();
        boolean limit = false;
        //根据showType查询影片
        //1-正在热映，2-即将上映，3-经典影片
        switch (showType){
            case 1:
                filmVo = filmServiceAPI.getHotFilms(limit, pageSize, nowPage, sortId, sourceId, yearId, catId);
                break;
            case 2:
                filmVo = filmServiceAPI.getSoonFilms(limit, pageSize, nowPage, sortId, sourceId, yearId, catId);
                break;
            case 3:
                filmVo = filmServiceAPI.getClassicFilms(pageSize, nowPage, sortId, sourceId, yearId, catId);
                break;
            default:
                filmVo = filmServiceAPI.getHotFilms(limit, pageSize, nowPage, sortId, sourceId, yearId, catId);
        }

        return ResponseVO.Success(filmVo.getNowPage(), filmVo.getTotalPage(), filmVo.getFilmInfo(), Constant.PRE_IMG);
    }

    /**
     * 查询影片详情
     * @param searchParam 影片名称或者影片编号
     * @param searchType 查询类型
     * @return
     */
    @RequestMapping(value = "films/{searchParam}", method = RequestMethod.GET)
    public ResponseVO filmInfo(@PathVariable("searchParam") String searchParam,
                               int searchType){
        // 根据searchType判断查询类型
        // 0表示按照编号查找，1表示按照名称查找

        FilmDetailVo filmDetailVo = filmServiceAPI.getFilmDetail(searchType, searchParam);

        if (filmDetailVo == null) {
            return ResponseVO.serviceFail("无此电影信息");
        }
        String filmId = filmDetailVo.getFilmId();
        //Dubbo异步获取
        //获取影片描述信息
        String biography = filmServiceAPI.getFilmDesc(filmId);

        //获取导演信息
        ActorVo director = filmServiceAPI.getDirector(filmId);

        //获取演员信息
        List<ActorVo> actors = filmServiceAPI.getActors(filmId);

        //获取图片信息
        ImgVo imgs = filmServiceAPI.getImgs(filmId);

        InfoRequestVo info04 = new InfoRequestVo();
        ActorRequestVo actorRequestVo = new ActorRequestVo();
        //组装actorRequestVo
        actorRequestVo.setDirector(director);
        actorRequestVo.setActors(actors);
        //组装InfoRequestVo
        info04.setFilmId(filmId);
        info04.setBiography(biography);
        info04.setActors(actorRequestVo);
        info04.setImgs(imgs);
        //组装Info04
        filmDetailVo.setInfo04(info04);

        return ResponseVO.Success(filmDetailVo, Constant.PRE_IMG);
    }
}
