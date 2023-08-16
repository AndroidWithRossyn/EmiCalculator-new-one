package com.example.emicalculator_2.FIles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.example.emicalculator_2.Constants;
import com.example.emicalculator_2.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class splash extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(512, 512);
        getWindow().setStatusBarColor(0);
        setContentView((int) R.layout.splash_activity);
        String interAds = getString(R.string.InterstialAds);
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, 0);
        this.sharedPreferences = sharedPreferences2;
        if (!sharedPreferences2.getString(Constants.CURRENCY, "null").equals("null")) {
            Constants.CURRENCY_STORED = this.sharedPreferences.getString(Constants.CURRENCY, (String) null);
        }
        final InterstitialAd ad = new InterstitialAd(this);
        ad.setAdUnitId(interAds);
        ad.loadAd(new AdRequest.Builder().build());
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (ad.isLoaded()) {
                    ad.show();
                } else {
                    splash.this.startActivity(new Intent(splash.this, MainActivity.class));
                    splash.this.finish();
                }
                ad.setAdListener(new AdListener() {
                    public void onAdClosed() {
                        splash.this.startActivity(new Intent(splash.this, MainActivity.class));
                        splash.this.finish();
                    }

                    public void onAdFailedToLoad(LoadAdError adError) {
                        splash.this.startActivity(new Intent(splash.this, MainActivity.class));
                        splash.this.finish();
                    }
                });
            }
        }, 1500);
    }
}
