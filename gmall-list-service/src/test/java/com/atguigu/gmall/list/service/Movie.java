package com.atguigu.gmall.list.service;

import java.util.List;

public class Movie {

    private String id;
    private String name;
    private String doubanScore;
    private List<Actor> actorList;

    public Movie(String id) {
        this.id = id;
    }

    public Movie(String id, String name, String doubanScore, List<Actor> actorList) {
        this.id = id;
        this.name = name;
        this.doubanScore = doubanScore;
        this.actorList = actorList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoubanScore() {
        return doubanScore;
    }

    public void setDoubanScore(String doubanScore) {
        this.doubanScore = doubanScore;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }
}
