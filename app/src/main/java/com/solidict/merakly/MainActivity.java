package com.solidict.merakly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.solidict.meraklysdk.BannerListener;
import com.solidict.meraklysdk.BannerView;

public class MainActivity extends AppCompatActivity implements BannerListener {

    BannerView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bannerView = (BannerView) findViewById(R.id.banner_view);
        bannerView.setBannerListener(this);
        bannerView.init("a40b6dfad96834f19ab9bc7f4194dd4c", "2ce915812b00c27b2fcc65b2289bd74f");
        //bannerView.load();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bannerView.onResume();
    }

    @Override
    public void onLoadSuccess() {

    }

    @Override
    public void onLoadError(String message) {

    }
}
