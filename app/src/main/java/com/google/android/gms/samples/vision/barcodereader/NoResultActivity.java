package com.google.android.gms.samples.vision.barcodereader;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import java.net.URLEncoder;

public class NoResultActivity extends AppCompatActivity {

    private SearchView searchBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_result);

        searchBar = (SearchView) findViewById(R.id.simpleSearchView);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.trendyol.com/tum--urunler?q=" + URLEncoder.encode(s)));
                startActivity(browserIntent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }


}
