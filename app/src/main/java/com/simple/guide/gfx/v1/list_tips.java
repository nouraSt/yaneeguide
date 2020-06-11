package com.simple.guide.gfx.v1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class list_tips extends AppCompatActivity {

    ImageView back ;
    RecyclerView mRecyclerView ;
    ContentAdapter adapter ;
    RecyclerView.LayoutManager layoutManager ;

    RelativeLayout relativeAdView ;
    InterstitialAd mInterstitialAd ;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items);
        loadItems();

        back = findViewById(R.id.back);

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

    private void loadItems(){
        mRecyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this , 1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new ContentAdapter(mRecyclerView.getContext());
        mRecyclerView.setAdapter(adapter);
    }


    public void ShowInterstitial(){
        if (mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView preview_img;
        TextView title;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.cadre_view, parent, false));

            preview_img =  itemView.findViewById(R.id.img_preview);
            title =  itemView.findViewById(R.id.title_preview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, StepsActivity.class);
                    intent.putExtra(StepsActivity.tips, getAdapterPosition());
                    context.startActivity(intent);
                    ShowInterstitial();
                }
            });

        }

    }

    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of Card in RecyclerView.
        private static final int LENGTH = 8 ;

        private final String[] Title_Array;
        private final Drawable[] ImgPreview_Array;


        ContentAdapter(Context context) {

            Resources resources = context.getResources();
            Title_Array = resources.getStringArray(R.array.title);
            TypedArray typedArray = resources.obtainTypedArray(R.array.images_previews);


            ImgPreview_Array = new Drawable[typedArray.length()];
            for (int i = 0; i < ImgPreview_Array.length; i++) {
                ImgPreview_Array[i] = typedArray.getDrawable(i);
            }
            typedArray.recycle();

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.preview_img.setImageDrawable(ImgPreview_Array[position % ImgPreview_Array.length]);
            holder.title.setText(Title_Array[position % Title_Array.length]);

        }

        @Override
        public int getItemCount() {
            return LENGTH;
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
