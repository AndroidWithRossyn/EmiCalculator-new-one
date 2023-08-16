package com.example.emicalculator_2.Calculations;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import com.example.emicalculator_2.Constants;
import com.example.emicalculator_2.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class Currency extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    InterstitialAd ad;
    ArrayAdapter adapter;
    String appLink;
    int choosen = 0;
    TextView dateview;
    double[] displayanswer = new double[6];
    double[] euroto = new double[6];
    String[] finalanswer = new String[6];
    SharedPreferences frontshare;
    String interAds;
    boolean isCalculated;
    ListView listView;
    AdView madview;
    ReviewManager manger;
    String measurement = "0";
    String nativeAds;
    String premiumLink;
    ReviewInfo reviewInfo;
    SharedPreferences sharedPreferences;
    Spinner spiner;
    List<String> units = new ArrayList();
    EditText value;

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        this.choosen = position;
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public class Downloadtask extends AsyncTask<String, Void, String> {
        public Downloadtask() {
        }

        
        public String doInBackground(String... urls) {
            String result = "";
            try {
                InputStreamReader reader = new InputStreamReader(((HttpURLConnection) new URL(urls[0]).openConnection()).getInputStream());
                for (int data = reader.read(); data != -1; data = reader.read()) {
                    result = result + ((char) data);
                }
                return result;
            } catch (Exception e) {
                Log.e("Currency Error befre", e.getMessage());
                return "";
            }
        }

        
        public void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jasonobject = new JSONObject(s);
                JSONObject array = new JSONObject(jasonobject.optString("data"));
                Currency.this.euroto[0] = 1.0d / array.optDouble("EUR");
                Currency.this.euroto[1] = array.optDouble("INR") / array.optDouble("EUR");
                Currency.this.euroto[3] = array.optDouble("AED") / array.optDouble("EUR");
                Currency.this.euroto[4] = array.optDouble("JPY") / array.optDouble("EUR");
                Currency.this.euroto[5] = array.optDouble("GBP") / array.optDouble("EUR");
                Currency.this.euroto[2] = array.optDouble("EUR") / array.optDouble("EUR");
                JSONObject jSONObject = jasonobject;
                Currency.this.sharedPreferences.edit().putFloat("euroto0", (float) Currency.this.euroto[0]).apply();
                Currency.this.sharedPreferences.edit().putFloat("euroto1", (float) Currency.this.euroto[1]).apply();
                Currency.this.sharedPreferences.edit().putFloat("euroto2", (float) Currency.this.euroto[2]).apply();
                Currency.this.sharedPreferences.edit().putFloat("euroto3", (float) Currency.this.euroto[3]).apply();
                Currency.this.sharedPreferences.edit().putFloat("euroto4", (float) Currency.this.euroto[4]).apply();
                Currency.this.sharedPreferences.edit().putFloat("euroto5", (float) Currency.this.euroto[5]).apply();
            } catch (Exception e) {
                Log.e("THe Currency Error", e.getMessage());
                Currency.this.euroto[0] = 1.209329d;
                Currency.this.euroto[1] = 88.522262d;
                Currency.this.euroto[3] = 4.441877d;
                Currency.this.euroto[4] = 125.802875d;
                Currency.this.euroto[5] = 0.888996d;
                Currency.this.euroto[2] = 1.0d;
                Currency.this.sharedPreferences.edit().putFloat("euroto0", (float) Currency.this.euroto[0]).apply();
                Currency.this.sharedPreferences.edit().putFloat("euroto1", (float) Currency.this.euroto[1]).apply();
                Currency.this.sharedPreferences.edit().putFloat("euroto2", (float) Currency.this.euroto[2]).apply();
                Currency.this.sharedPreferences.edit().putFloat("euroto3", (float) Currency.this.euroto[3]).apply();
                Currency.this.sharedPreferences.edit().putFloat("euroto4", (float) Currency.this.euroto[4]).apply();
                Currency.this.sharedPreferences.edit().putFloat("euroto5", (float) Currency.this.euroto[5]).apply();
            }
        }
    }

    public void calculate(View view) {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (!this.value.getText().toString().isEmpty()) {
            this.measurement = this.value.getText().toString();
        } else {
            this.value.setText("0");
            this.measurement = "0";
        }
        conversion();
    }

    public void conversion() {
        for (int i = 0; i <= 5; i++) {
            double[] dArr = this.displayanswer;
            double parseDouble = Double.parseDouble(this.measurement);
            double[] dArr2 = this.euroto;
            dArr[i] = parseDouble * (dArr2[i] / dArr2[this.choosen]);
        }
        for (int i2 = 0; i2 <= 5; i2++) {
            double[] dArr3 = this.displayanswer;
            if (dArr3[i2] == ((double) ((int) dArr3[i2]))) {
                dArr3[i2] = (double) ((int) dArr3[i2]);
            } else {
                this.displayanswer[i2] = new BigDecimal(dArr3[i2]).setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
        }
        for (int i3 = 0; i3 <= 5; i3++) {
            String[] strArr = this.finalanswer;
            strArr[i3] = this.displayanswer[i3] + "  " + this.units.get(i3);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.align_right, this.finalanswer);
        this.adapter = arrayAdapter;
        this.listView.setAdapter(arrayAdapter);
    }

    /* access modifiers changed from: package-private */
    public boolean internet_connection() {
        NetworkInfo activeNetwork = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected() && activeNetwork.isAvailable();
    }

    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_currency);
        this.nativeAds = getString(R.string.nativeAds);
        this.premiumLink = getString(R.string.premiumAppLink);
        this.appLink = getString(R.string.appLink);
        this.interAds = getString(R.string.InterstialAds);
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.madview = (AdView) findViewById(R.id.adView);
        this.madview.loadAd(new AdRequest.Builder().build());
        final UnifiedNativeAd[] nativeAd = new UnifiedNativeAd[1];
        new AdLoader.Builder((Context) this, this.nativeAds).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                nativeAd[0] = unifiedNativeAd;
                UnifiedNativeAdView adview = (UnifiedNativeAdView) Currency.this.getLayoutInflater().inflate(R.layout.nativeads, (ViewGroup) null);
                LinearLayout container = (LinearLayout) Currency.this.findViewById(R.id.nativead);
                container.setVisibility(0);
                adview.setHeadlineView(adview.findViewById(R.id.heading));
                adview.setAdvertiserView(adview.findViewById(R.id.advertisername));
                adview.setBodyView(adview.findViewById(R.id.body));
                adview.setStarRatingView(adview.findViewById(R.id.start_rating));
                adview.setMediaView((MediaView) adview.findViewById(R.id.media_view));
                adview.setCallToActionView(adview.findViewById(R.id.calltoaction));
                adview.setIconView(adview.findViewById(R.id.adicon));
                adview.getMediaView().setMediaContent(unifiedNativeAd.getMediaContent());
                ((TextView) adview.getHeadlineView()).setText(nativeAd[0].getHeadline());
                if (unifiedNativeAd.getBody() == null) {
                    adview.getBodyView().setVisibility(4);
                } else {
                    ((TextView) adview.getBodyView()).setText(unifiedNativeAd.getBody());
                    adview.getBodyView().setVisibility(0);
                }
                if (unifiedNativeAd.getAdvertiser() == null) {
                    adview.getAdvertiserView().setVisibility(4);
                } else {
                    ((TextView) adview.getAdvertiserView()).setText(unifiedNativeAd.getAdvertiser());
                    adview.getAdvertiserView().setVisibility(0);
                }
                if (unifiedNativeAd.getStarRating() == null) {
                    adview.getStarRatingView().setVisibility(4);
                } else {
                    ((RatingBar) adview.getStarRatingView()).setRating(unifiedNativeAd.getStarRating().floatValue());
                    adview.getStarRatingView().setVisibility(0);
                }
                if (unifiedNativeAd.getIcon() == null) {
                    adview.getIconView().setVisibility(4);
                } else {
                    ((ImageView) adview.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
                    adview.getIconView().setVisibility(0);
                }
                if (unifiedNativeAd.getCallToAction() == null) {
                    adview.getCallToActionView().setVisibility(4);
                } else {
                    ((Button) adview.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
                    adview.getCallToActionView().setVisibility(0);
                }
                adview.setNativeAd(unifiedNativeAd);
                container.removeAllViews();
                container.addView(adview);
            }
        }).build().loadAd(new AdRequest.Builder().build());
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        InterstitialAd interstitialAd = new InterstitialAd(this);
        this.ad = interstitialAd;
        interstitialAd.setAdUnitId(this.interAds);
        this.ad.loadAd(new AdRequest.Builder().build());
        if (this.ad.isLoaded()) {
            this.ad.show();
        }
        this.ad.loadAd(new AdRequest.Builder().build());
        ReviewManager create = ReviewManagerFactory.create(this);
        this.manger = create;
        create.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            public void onComplete(Task<ReviewInfo> task) {
                if (task.isSuccessful()) {
                    Currency.this.reviewInfo = task.getResult();
                    return;
                }
                Toast.makeText(Currency.this, "Error", 0).show();
            }
        });
        this.frontshare = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, 0);
        ((LinearLayout) findViewById(R.id.mainlinear)).startAnimation(AnimationUtils.loadAnimation(this, R.anim.slideup));
        this.value = (EditText) findViewById(R.id.amount);
        this.spiner = (Spinner) findViewById(R.id.spinner);
        this.sharedPreferences = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, 0);
        this.listView = (ListView) findViewById(R.id.currency);
        this.dateview = (TextView) findViewById(R.id.date);
        if (internet_connection()) {
            new Downloadtask().execute(new String[]{getString(R.string.apiLinkCurrencyConverter)});
        } else {
            final Dialog dialog2 = new Dialog(this);
            dialog2.setContentView(R.layout.nointernet);
            dialog2.setCancelable(true);
            dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog2.setCanceledOnTouchOutside(true);
            dialog2.getWindow().setLayout(-2, -2);
            dialog2.getWindow().getAttributes().windowAnimations = 16973826;
            dialog2.show();
            ((Button) dialog2.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog2.dismiss();
                }
            });
            this.euroto[0] = (double) this.sharedPreferences.getFloat("euroto0", 0.0f);
            this.euroto[1] = (double) this.sharedPreferences.getFloat("euroto1", 0.0f);
            this.euroto[2] = (double) this.sharedPreferences.getFloat("euroto2", 0.0f);
            this.euroto[3] = (double) this.sharedPreferences.getFloat("euroto3", 0.0f);
            this.euroto[4] = (double) this.sharedPreferences.getFloat("euroto4", 0.0f);
            this.euroto[5] = (double) this.sharedPreferences.getFloat("euroto5", 0.0f);
            Toast.makeText(this, String.valueOf(this.euroto[4]), 1).show();
        }
        this.units.add("DOLLAR ($)");
        this.units.add("INR (₹)");
        this.units.add("EURO (€)");
        this.units.add("DIHRAM (د.إ)");
        this.units.add("YEN (¥)");
        this.units.add("POUND (£)");
        for (int i = 0; i <= 5; i++) {
            this.displayanswer[i] = 0.0d;
        }
        for (int i2 = 0; i2 <= 5; i2++) {
            String[] strArr = this.finalanswer;
            strArr[i2] = "0  " + this.units.get(i2);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.align_right, this.finalanswer);
        this.adapter = arrayAdapter;
        this.listView.setAdapter(arrayAdapter);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, 17367048, this.units);
        dataAdapter.setDropDownViewResource(17367049);
        this.spiner.setAdapter(dataAdapter);
        this.spiner.setOnItemSelectedListener(this);
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scroller);
        if (Build.VERSION.SDK_INT >= 23) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY < 800) {
                        Currency.this.madview.animate().translationY(0.0f);
                    } else {
                        Currency.this.madview.animate().translationY(100.0f);
                    }
                }
            });
        }
    }

    public void onBackPressed() {
        int times = this.frontshare.getInt("times", 0);
        if ((this.reviewInfo != null && this.isCalculated && times == 1) || times % 3 == 0) {
            this.manger.launchReviewFlow(this, this.reviewInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(Task<Void> task) {
                    task.isSuccessful();
                }
            });
            super.onBackPressed();
        } else if (this.ad.isLoaded()) {
            this.ad.show();
            loadInterstitialAd();
            this.ad.setAdListener(new AdListener() {
                public void onAdClosed() {
                    Currency.this.finish();
                }
            });
        } else {
            super.onBackPressed();
        }
    }

    public void loadInterstitialAd() {
        this.ad.loadAd(new AdRequest.Builder().build());
    }
}
