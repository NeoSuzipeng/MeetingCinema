package com.stylefeng.guns.api.film.vo;

import java.io.Serializable;

/**
 * Created by su on 2019/4/25.
 *
 * 影片年代模型
 */
public class YearVo implements Serializable{

    private String yearId;

    private String yearName;

    private boolean isActive;

    public String getYearId() {
        return yearId;
    }

    public void setYearId(String yearId) {
        this.yearId = yearId;
    }

    public String getYearName() {
        return yearName;
    }

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
