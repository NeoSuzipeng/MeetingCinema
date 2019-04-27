package com.stylefeng.guns.api.film.vo;

import java.io.Serializable;

/**
 * Created by su on 2019/4/25.
 * 影片源模型
 */
public class SourceVo implements Serializable{

    private String sourceId;

    private String sourceName;

    private boolean isActive;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
