package com.example.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListResponse<T> {
    @SerializedName("@odata.context")
    @Expose
    private String odata;
    
    @SerializedName("value")
    @Expose
    private List<T> data;

    public List<T> getData() {
        return data;
    }
}

