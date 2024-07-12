package com.example.project.model.ServiceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class ListResponse<T> {
    @SerializedName("@odata.context")
    @Expose
    private String odata;

    @SerializedName("value")
    @Expose
    private List<T> data;

    public ListResponse() {
    }

    public ListResponse(String odata, List<T> data) {
        this.odata = odata;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ListResponse{" +
                "data=" + Arrays.toString(data.toArray()) +
                '}';
    }

    public List<T> getData() {
        return data;
    }
}

