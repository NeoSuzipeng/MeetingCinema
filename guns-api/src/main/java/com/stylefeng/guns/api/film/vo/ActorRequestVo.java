package com.stylefeng.guns.api.film.vo;

import com.stylefeng.guns.api.film.vo.ActorVo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by su on 2019/4/26.
 *
 */
public class ActorRequestVo implements Serializable {

    private ActorVo director;

    private List<ActorVo> actors;

    public ActorVo getDirector() {
        return director;
    }

    public void setDirector(ActorVo director) {
        this.director = director;
    }

    public List<ActorVo> getActors() {
        return actors;
    }

    public void setActors(List<ActorVo> actors) {
        this.actors = actors;
    }
}
