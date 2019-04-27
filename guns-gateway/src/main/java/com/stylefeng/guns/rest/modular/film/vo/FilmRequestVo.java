package com.stylefeng.guns.rest.modular.film.vo;

import java.io.Serializable;

/**
 * Created by su on 2019/4/25.
 * 影片查询参数模型
 */
public class FilmRequestVo implements Serializable{

    private Integer showType = 1; //查询类型，1-正在热映，2-即将上映，3-经典影片

    private Integer sortId = 1; //排序方式，1-按热门搜索，2-按时间搜索，3-按评价搜索

    private Integer catId = 99; //类型编号

    private Integer yearId = 99;  //区域编号

    private Integer sourceId = 99;  //年代编号

    private Integer nowPage = 1; //影片列表当前页，翻页使用

    private Integer pageSize = 18; //每页显示条数

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getYearId() {
        return yearId;
    }

    public void setYearId(Integer yearId) {
        this.yearId = yearId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getNowPage() {
        return nowPage;
    }

    public void setNowPage(Integer nowPage) {
        this.nowPage = nowPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
