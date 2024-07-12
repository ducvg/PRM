package com.example.project.model.ServiceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenDTO {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("expiresAt")
    @Expose
    private String expiresAt;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public TokenDTO(){}
    public TokenDTO(String token, String expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }
}
