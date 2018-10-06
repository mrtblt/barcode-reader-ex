package com.google.android.gms.samples.vision.barcodereader;

import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("contentId")
    private Integer contentId;

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }
}
