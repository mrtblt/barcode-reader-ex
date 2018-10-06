/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gms.samples.vision.barcodereader;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends Activity implements View.OnClickListener {

    private CompoundButton useFlash;
    private TextView barcodeValue;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    private String barcodeApi = "https://productcontentapigw.trendyol.com/productcontents/2194808";
    private static String response2;
    public String barcodeString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barcodeValue = (TextView) findViewById(R.id.barcode_value);

        useFlash = (CompoundButton) findViewById(R.id.use_flash);

        findViewById(R.id.read_barcode).setOnClickListener(this);

    }

    private void getContent(final String barcodeNumber) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://stageproductcontentapigw.trendyol.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContentApiInterface service = retrofit.create(ContentApiInterface.class);
        Call<Content> contentCall = service.getProductContentId(barcodeNumber);
        contentCall.enqueue(new Callback<Content>() {
            @Override
            public void onResponse(Call<Content> call, retrofit2.Response<Content> response) {
                response2 = response.body() != null ? response.body().getId().toString() : null;
                if(response2 != null ){
                    directToTrendyol();

                }
                else {
                    barcodeValue.setText("Aradiginiz urunu maalesef bulamadık :( ");

                }
            }

            @Override
            public void onFailure(Call<Content> call, Throwable t) {
                Log.i("mert", t.getMessage());

            }
        });
    }


    private void directToTrendyol() {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.trendyol.com/trendyol/trendyol-p-" + response2));
        startActivity(browserIntent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            //intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());
            intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    barcodeString = barcode.displayValue;
                    barcodeValue.setText(barcodeString);
                    try {
                        getContent("3031389");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
