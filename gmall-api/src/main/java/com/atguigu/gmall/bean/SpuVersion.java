package com.atguigu.gmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class SpuVersion implements Serializable {
    private static final long serialVersionUID = 9213209393660958377L;

    @Column
    @Id
    private String id;
    private String spuId;
    private String spuVersion;
    private String isEnabled;

    public SpuVersion() {
    }

    public SpuVersion(String id, String spuId, String spuVersion, String isEnabled) {
        this.id = id;
        this.spuId = spuId;
        this.spuVersion = spuVersion;
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

    public String getSpuVersion() {
        return spuVersion;
    }

    public void setSpuVersion(String spuVersion) {
        this.spuVersion = spuVersion;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }
}
