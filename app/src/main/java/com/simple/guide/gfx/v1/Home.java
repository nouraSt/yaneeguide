package com.simple.guide.gfx.v1;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;

public class Home extends AppCompatActivity {

    TextView start , share , rate , privacy;

    InterstitialAd mInterstitialAd ;

    @Override
    protected void onCreate(Bundle home){
        super.onCreate(home);
        setContentView(R.layout.home);

        start = findViewById(R.id.start_tips);
        share = findViewById(R.id.share);
        rate = findViewById(R.id.rate);
        privacy = findViewById(R.id.privacy_policy);



        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.Interstitial_AdMob));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });




        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), list_tips.class));
                ShowInterstitial();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              share();
            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Rate();
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                privacy();
            }
        });
    }


    public void Rate(){
        try {
            Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName()));
            startActivity(localIntent);
        } catch (ActivityNotFoundException localActivityNotFoundException) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }
    public void share(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name) + " ** Install from this link : " + "https://play.google.com/store/apps/details?id=" + getPackageName());
        sendIntent.setType("text/plain");
        if (isAvailable(sendIntent)) {
            startActivity(sendIntent);
        } else {
            Toast.makeText(getApplicationContext(), "There is no app available for this task", Toast.LENGTH_SHORT).show();
        }
    }

    public void privacy(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_link) ));
        if (isAvailable(intent)) {
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "There is no app available for this task", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isAvailable(Intent mIntent) {
        final PackageManager mgr = getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(mIntent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public void ShowInterstitial(){
        if (mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }
    }

}
