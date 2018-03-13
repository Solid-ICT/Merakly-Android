package com.solidict.merakly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.solidict.meraklysdk.MeraklyAdBanner;

public class MainActivity extends AppCompatActivity {

    MeraklyAdBanner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        banner = (MeraklyAdBanner) findViewById(R.id.bnr_test);
        banner.setCredentials("2c452b2e7c887b51399a6522803a6fda", "5159fef648f9c0587f0a9480aa277875");


    }

    @Override
    protected void onResume() {
        super.onResume();
        //bannerView.onResume();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void btnClicked(View view) {
        banner.load();
    }
}
