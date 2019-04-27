package com.stylefeng.guns.api.film.vo;

import java.io.Serializable;

/**
 * Created by su on 2019/4/25.
 * 影片类型模型
 */
public class CatVo implements Serializable {

    private String catId;

    private String catName;

    private boolean isActive;        //类型选中状态

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
