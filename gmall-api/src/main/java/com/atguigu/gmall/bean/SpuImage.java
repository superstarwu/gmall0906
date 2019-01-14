package com.atguigu.gmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class SpuImage implements Serializable {
    private static final long serialVersionUID = 6442333214737716830L;

    @Column
    @Id
    private String id;
    private String spuId;
    private String imgName;
    private String imgUrl;

    public SpuImage() {
    }

    public SpuImage(String id, String spuId, String imgName, String imgUrl) {
        this.id = id;
        this.spuId = spuId;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
