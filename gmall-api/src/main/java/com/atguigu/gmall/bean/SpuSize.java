package com.atguigu.gmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class SpuSize implements Serializable {
    private static final long serialVersionUID = 8162101487170112843L;

    @Column
    @Id
    private String id;
    private String spuId;
    private String spuSize;
    private String isEnabled;

    public SpuSize() {
    }

    public SpuSize(String id, String spuId, String spuSize, String isEnabled) {
        this.id = id;
        this.spuId = spuId;
        this.spuSize = spuSize;
        this.isEnabled = isEnabled;
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

    public String getSpuSize() {
        return spuSize;
    }

    public void setSpuSize(String spuSize) {
        this.spuSize = spuSize;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }
}
