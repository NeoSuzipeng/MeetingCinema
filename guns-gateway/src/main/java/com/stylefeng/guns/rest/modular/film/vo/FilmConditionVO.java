package com.stylefeng.guns.rest.modular.film.vo;

import com.stylefeng.guns.api.film.vo.CatVo;
import com.stylefeng.guns.api.film.vo.SourceVo;
import com.stylefeng.guns.api.film.vo.YearVo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by su on 2019/4/25.
 *
 * 影片条件集合模型
 */
public class FilmConditionVO implements Serializable{

    List<CatVo> catInfo;

    List<SourceVo> sourceInfo;

    List<YearVo> yearInfo;

    public List<CatVo> getCatInfo() {
        return catInfo;
    }

    public void setCatInfo(List<CatVo> catInfo) {
        this.catInfo = catInfo;
    }

    public List<SourceVo> getSourceInfo() {
        return sourceInfo;
    }

    public void setSourceInfo(List<SourceVo> sourceInfo) {
        this.sourceInfo = sourceInfo;
    }

    public List<YearVo> getYearInfo() {
        return yearInfo;
    }

    public void setYearInfo(List<YearVo> yearInfo) {
        this.yearInfo = yearInfo;
    }

    
}
