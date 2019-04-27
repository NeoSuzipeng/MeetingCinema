package com.stylefeng.guns.api.film.vo;

import java.io.Serializable;

/**
 * Created by su on 2019/4/26.
 * 演员信息模型
 */
public class ActorVo implements Serializable {

    private String imgAddress;

    private String directorName;

    private String roleName;

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
