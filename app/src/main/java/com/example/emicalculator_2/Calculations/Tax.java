package com.example.emicalculator_2.Calculations;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import com.example.emicalculator_2.Constants;
import com.example.emicalculator_2.PrasingTheDouble;
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
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Tax extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    InterstitialAd ad;
    double am;
    EditText amount;
    String appLink;
    Bitmap bmp;
    String choosen = "GST is Added";
    SharedPreferences frontshare;
    double gst;
    double gsta;
    TextView gstamount;
    EditText gstrate;
    String interAds;
    boolean isCalculated;
    AdView madview;
    ReviewManager manger;
    double n;
    String nativeAds;
    TextView net;
    String premiumLink;
    ReviewInfo reviewInfo;
    Bitmap scalebmp;
    Spinner spiner;
    List<String> spinner = new ArrayList();
    double t;
    TextView total;

    public void calculate(View view) {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        calculation();
    }

    public void calculation() {
        ((NestedScrollView) findViewById(R.id.scroller)).smoothScrollTo(0, 800);
        if (String.valueOf(this.amount.getText()).isEmpty() || String.valueOf(this.amount.getText()).isEmpty()) {
            Toast.makeText(this, "Enter the value", 0).show();
        } else if (Double.parseDouble(this.gstrate.getText().toString()) <= 50.0d) {
            this.am = Double.parseDouble(String.valueOf(this.amount.getText()));
            this.gst = Double.parseDouble(String.valueOf(this.gstrate.getText()));
            if (this.choosen.contentEquals("GST is Added")) {
                double d = this.am;
                this.t = d;
                double d2 = this.gst;
                double d3 = (d2 * d) / (d2 + 100.0d);
                this.gsta = d3;
                this.n = d - d3;
            } else {
                double d4 = this.gst;
                double d5 = this.am;
                double d6 = (d4 * d5) / 100.0d;
                this.gsta = d6;
                this.n = d5;
                this.t = d5 + d6;
            }
            double d7 = this.gsta;
            if (((double) ((int) d7)) == d7) {
                this.gstamount.setText(String.valueOf((int) d7));
            } else {
                double doubleValue = new BigDecimal(this.gsta).setScale(2, RoundingMode.HALF_UP).doubleValue();
                this.gsta = doubleValue;
                this.gstamount.setText(String.valueOf(doubleValue));
            }
            PrasingTheDouble prasingTheDouble = new PrasingTheDouble(this.gsta);
            TextView textView = this.gstamount;
            textView.setText(prasingTheDouble.getaDouble() + " " + Constants.CURRENCY_STORED);
            double d8 = this.n;
            if (((double) ((int) d8)) == d8) {
                this.net.setText(String.valueOf((int) d8));
            } else {
                double doubleValue2 = new BigDecimal(this.n).setScale(2, RoundingMode.HALF_UP).doubleValue();
                this.n = doubleValue2;
                this.net.setText(String.valueOf(doubleValue2));
            }
            PrasingTheDouble prasingTheDouble2 = new PrasingTheDouble(this.n);
            TextView textView2 = this.net;
            textView2.setText(prasingTheDouble2.getaDouble() + " " + Constants.CURRENCY_STORED);
            double d9 = this.t;
            if (((double) ((int) d9)) == d9) {
                this.total.setText(String.valueOf((int) d9));
            } else {
                double doubleValue3 = new BigDecimal(this.t).setScale(2, RoundingMode.HALF_UP).doubleValue();
                this.t = doubleValue3;
                this.total.setText(String.valueOf(doubleValue3));
            }
            PrasingTheDouble prasingTheDouble3 = new PrasingTheDouble(this.t);
            TextView textView3 = this.total;
            textView3.setText(prasingTheDouble3.getaDouble() + " " + Constants.CURRENCY_STORED);
        } else {
            Toast.makeText(this, "Interest rate should be less than 50%", 0).show();
        }
    }

    public void share(View view) {
        if (String.valueOf(this.amount.getText()).isEmpty() || String.valueOf(this.amount.getText()).isEmpty()) {
            Toast.makeText(this, "Enter the value", 0).show();
            return;
        }
        calculation();
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", "EMI- A Calculator app");
        intent.putExtra("android.intent.extra.TEXT", "Tax Details-\n \nAmount: " + this.am + "\nRate of GST: " + this.gst + "\n\nNet Amount: " + this.n + "\nGST Amount: " + this.gsta + "\nTotal Amount: " + this.t + "\n\nCalculate by EMI\n" + this.appLink);
        startActivity(Intent.createChooser(intent, "Share Using"));
    }

    public void pdf(View view) {
        if (this.ad.isLoaded()) {
            this.ad.show();
        }
        loadInterstitialAd();
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 0);
        PdfDocument myPdf = new PdfDocument();
        Paint myPaint = new Paint();
        PdfDocument.Page myPage = myPdf.startPage(new PdfDocument.PageInfo.Builder(1200, 2010, 1).create());
        Canvas canvas = myPage.getCanvas();
        canvas.drawBitmap(this.scalebmp, 0.0f, 0.0f, myPaint);
        myPaint.setTextSize(27.0f);
        myPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(this.choosen, 1100.0f, 500.0f, myPaint);
        Paint p = new Paint();
        p.setColor(Color.parseColor("#000000"));
        p.setTextSize(29.0f);
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(this.am + " " + Constants.CURRENCY_STORED, 900.0f, 575.0f, p);
        StringBuilder sb = new StringBuilder();
        sb.append(this.gst);
        sb.append(" %");
        canvas.drawText(sb.toString(), 900.0f, 715.0f, p);
        canvas.drawText(this.n + " " + Constants.CURRENCY_STORED, 900.0f, 980.0f, p);
        canvas.drawText(this.gsta + " " + Constants.CURRENCY_STORED, 900.0f, 1110.0f, p);
        canvas.drawText(this.t + " " + Constants.CURRENCY_STORED, 900.0f, 1245.0f, p);
        myPdf.finishPage(myPage);
        Calendar calendar = Calendar.getInstance();
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(externalStoragePublicDirectory, "/Tax" + calendar.get(5) + ".pdf");
        try {
            FileOutputStream out = new FileOutputStream(file);
            myPdf.writeTo(out);
            out.flush();
            out.close();
            myPdf.writeTo(new FileOutputStream(file));
        } catch (Exception e) {
            Log.e("PDF ISSUE", e.getMessage());
        }
        myPdf.close();
        if (file.exists()) {
            Intent intent1 = new Intent("android.intent.action.VIEW");
            intent1.setDataAndType(FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file), "application/pdf");
            intent1.setFlags(67108864);
            intent1.addFlags(1);
            try {
                startActivity(intent1);
            } catch (ActivityNotFoundException e2) {
                Toast.makeText(this, "Download", 0).show();
            }
        }
    }

    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_tax);
        this.nativeAds = getString(R.string.nativeAds);
        this.premiumLink = getString(R.string.premiumAppLink);
        this.appLink = getString(R.string.appLink);
        this.interAds = getString(R.string.InterstialAds);
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        ReviewManager create = ReviewManagerFactory.create(this);
        this.manger = create;
        create.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            public void onComplete(Task<ReviewInfo> task) {
                if (task.isSuccessful()) {
                    Tax.this.reviewInfo = task.getResult();
                    return;
                }
                Toast.makeText(Tax.this, "Error", 0).show();
            }
        });
        this.frontshare = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, 0);
        this.madview = (AdView) findViewById(R.id.adView);
        this.madview.loadAd(new AdRequest.Builder().build());
        final UnifiedNativeAd[] nativeAd = new UnifiedNativeAd[1];
        new AdLoader.Builder((Context) this, this.nativeAds).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                nativeAd[0] = unifiedNativeAd;
                UnifiedNativeAdView adview = (UnifiedNativeAdView) Tax.this.getLayoutInflater().inflate(R.layout.nativeads, (ViewGroup) null);
                LinearLayout container = (LinearLayout) Tax.this.findViewById(R.id.nativead);
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
        InterstitialAd interstitialAd = new InterstitialAd(this);
        this.ad = interstitialAd;
        interstitialAd.setAdUnitId(this.interAds);
        this.ad.loadAd(new AdRequest.Builder().build());
        if (this.ad.isLoaded()) {
            this.ad.show();
        }
        this.ad.loadAd(new AdRequest.Builder().build());
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.tax);
        this.bmp = decodeResource;
        this.scalebmp = Bitmap.createScaledBitmap(decodeResource, 1200, 2010, false);
        this.spiner = (Spinner) findViewById(R.id.spinner);
        this.spinner.add("GST is Added");
        this.spinner.add("GST is Removed");
        ((LinearLayout) findViewById(R.id.mainlinear)).startAnimation(AnimationUtils.loadAnimation(this, R.anim.slideup));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, 17367048, this.spinner);
        dataAdapter.setDropDownViewResource(17367049);
        this.spiner.setAdapter(dataAdapter);
        this.spiner.setOnItemSelectedListener(this);
        this.amount = (EditText) findViewById(R.id.amount);
        this.gstrate = (EditText) findViewById(R.id.rateoftax);
        this.net = (TextView) findViewById(R.id.netamount);
        this.gstamount = (TextView) findViewById(R.id.gstamount);
        this.total = (TextView) findViewById(R.id.totalamount);
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scroller);
        if (Build.VERSION.SDK_INT >= 23) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY < 800) {
                        Tax.this.madview.animate().translationY(0.0f);
                    } else {
                        Tax.this.madview.animate().translationY(100.0f);
                    }
                }
            });
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        this.choosen = adapterView.getItemAtPosition(i).toString();
        if (!this.amount.getText().toString().isEmpty() && !this.gstrate.getText().toString().isEmpty()) {
            calculation();
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
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
                    Tax.this.finish();
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
