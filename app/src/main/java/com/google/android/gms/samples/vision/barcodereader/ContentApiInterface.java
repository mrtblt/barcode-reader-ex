package com.google.android.gms.samples.vision.barcodereader;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ContentApiInterface {

    @GET("barcode/{barcode}")
    Call<Content> getProductContentId(@Path("barcode") String barcode);
}
