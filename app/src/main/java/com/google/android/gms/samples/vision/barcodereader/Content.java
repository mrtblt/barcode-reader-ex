package com.google.android.gms.samples.vision.barcodereader;

import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("id")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
