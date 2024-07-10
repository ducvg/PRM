package com.example.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestObj {

    @SerializedName("a")
    @Expose
    private String a;

    /**
     * No args constructor for use in serialization
     *
     */
    public TestObj() {
    }

    /**
     *
     * @param a
     */
    public TestObj(String a) {
        super();
        this.a = a;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

}
