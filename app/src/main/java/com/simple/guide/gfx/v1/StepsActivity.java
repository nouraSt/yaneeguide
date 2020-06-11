package com.simple.guide.gfx.v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class StepsActivity extends AppCompatActivity {

    static String tips ;
    int position ;

    RelativeLayout tips1 , tips2 , tips3 , tips4 ,
                   tips5 , tips6 , tips7 , tips8 ;

    ImageView back ;
    RelativeLayout relativeAdView ;
    InterstitialAd mInterstitialAd ;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps);
        InitializeTips();

        position = getIntent().getIntExtra(tips , 0);
       // Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_SHORT).show();
        loadTips();




        MobileAds.initialize(this , getString(R.string.Application_Id));
        relativeAdView = findViewById(R.id.relative_adView);
        mAdView = new AdView(getApplicationContext());
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId(getString(R.string.Banner_AdMob));
        relativeAdView.addView(mAdView);
        mAdView.loadAd(new AdRequest.Builder().build());


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.Interstitial_AdMob));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowInterstitial();
                finish();
            }
        });




    }

    private void InitializeTips(){
        tips1 = findViewById(R.id.relative_tips1);
        tips2 = findViewById(R.id.relative_tips2);
        tips3 = findViewById(R.id.relative_tips3);
        tips4 = findViewById(R.id.relative_tips4);

        tips5 = findViewById(R.id.relative_tips5);
        tips6 = findViewById(R.id.relative_tips6);
        tips7 = findViewById(R.id.relative_tips7);
        tips8 = findViewById(R.id.relative_tips8);

        back= findViewById(R.id.back);

    }


    private void loadTips(){
        if (position == 0){
            setTips(tips1);
        }else if (position == 1){
            setTips(tips2);
        }else if (position == 2){
            setTips(tips3);
        }else if (position == 3){
            setTips(tips4);
        }else if (position == 4){
            setTips(tips5);
        }else if (position == 5){
            setTips(tips6);
        }else if (position == 6){
            setTips(tips7);
        }else if (position == 7){
            setTips(tips8);
        }
    }

    private void setTips(RelativeLayout tips){
        tips.setVisibility(View.VISIBLE);
    }


    public void ShowInterstitial(){
        if (mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }
    }


    @Override
    public void onBackPressed() {
        ShowInterstitial();
        super.onBackPressed();

    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {

        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();

    }


}
