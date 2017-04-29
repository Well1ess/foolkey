package com.example.a29149.yuyuan.Util.Secret;

/**
 * Created by geyao on 2017/4/26.
 */
public class RSAKeyDTO {
    private Long id;
    private String pubBase64Str ;
    private String priBase64Str;
    private Long userId;

    public RSAKeyDTO() {
        super();
    }

    @Override
    public String toString() {
        return "RSAKeyDTO{" +
                "id=" + id +
                ", pubBase64Str='" + pubBase64Str + '\'' +
                ", priBase64Str='" + priBase64Str + '\'' +
                ", userId=" + userId +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getPubBase64Str() {
        return pubBase64Str;
    }

    public void setPubBase64Str(String pubBase64Str) {
        this.pubBase64Str = pubBase64Str;
    }

    public String getPriBase64Str() {
        return priBase64Str;
    }

    public void setPriBase64Str(String priBase64Str) {
        this.priBase64Str = priBase64Str;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
