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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
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
import java.util.Calendar;

public class compareloan extends AppCompatActivity {
    InterstitialAd ad;
    double amount1;
    double amount2;
    String appLink;
    Bitmap bmp;
    TextView difEmi;
    TextView difInter;
    TextView difTotal;
    double diffEmiVal;
    double diffInterest;
    double diffPayment;
    Bitmap dusrabmp;
    TextView emi1;
    TextView emi2;
    double emiVal1;
    double emiVal2;
    SharedPreferences frontshare;
    RadioGroup group;
    String interAds;
    TextView interest1;
    TextView interest2;
    boolean isCalculated;
    AdView madview;
    ReviewManager manger;
    String nativeAds;
    String premiumLink;
    EditText principal1;
    EditText principal2;
    RadioButton radioButton;
    EditText rate1;
    EditText rate2;
    double rateVal1;
    double rateVal2;
    ReviewInfo reviewInfo;
    Bitmap scalebmp;
    Bitmap scaledusrabmp;
    double tenure1;
    double tenure2;
    EditText time1;
    EditText time2;
    TextView total1;
    TextView total2;
    double totalInterest1;
    double totalInterest2;
    double totalPayment1;
    double totalPayment2;

    public void calculate(View view) {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (this.principal1.getText().toString().isEmpty() || this.principal2.getText().toString().isEmpty() || this.rate1.getText().toString().isEmpty() || this.rate2.getText().toString().isEmpty() || this.time1.getText().toString().isEmpty() || this.time2.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter the * value", 0).show();
            return;
        }
        this.tenure1 = Double.parseDouble(this.time1.getText().toString());
        this.tenure2 = Double.parseDouble(this.time2.getText().toString());
        RadioButton radioButton2 = (RadioButton) findViewById(this.group.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.tenure1 = Double.parseDouble(this.time1.getText().toString()) * 12.0d;
            this.tenure2 = Double.parseDouble(this.time2.getText().toString()) * 12.0d;
        } else {
            this.tenure1 = Double.parseDouble(this.time1.getText().toString());
            this.tenure2 = Double.parseDouble(this.time2.getText().toString());
        }
        if (this.tenure1 <= 30.0d || this.tenure2 <= 30.0d || Double.parseDouble(this.rate1.getText().toString()) <= 50.0d || Double.parseDouble(this.rate2.getText().toString()) <= 50.0d) {
            calculation();
        } else if (this.tenure1 > 30.0d || this.tenure2 > 30.0d) {
            Toast.makeText(this, "tenure should be less than 30 years", 0).show();
        } else {
            Toast.makeText(this, "Interest rate should be less than 50%", 0).show();
        }
    }

    public void calculation() {
        this.isCalculated = true;
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scroller);
        scrollView.smoothScrollTo(0, 800);
        this.amount1 = Double.parseDouble(this.principal1.getText().toString());
        this.amount2 = Double.parseDouble(this.principal2.getText().toString());
        this.rateVal1 = Double.parseDouble(this.rate1.getText().toString()) / 1200.0d;
        this.rateVal2 = Double.parseDouble(this.rate2.getText().toString()) / 1200.0d;
        double d = this.amount1;
        double d2 = this.rateVal1;
        double pmt = (d * d2) / (1.0d - Math.pow(d2 + 1.0d, -this.tenure1));
        double d3 = this.tenure1;
        double d4 = this.amount1;
        double d5 = (pmt * d3) - d4;
        this.totalInterest1 = d5;
        this.totalPayment1 = d5 + d4;
        double p = Math.pow(this.rateVal1 + 1.0d, d3);
        this.emiVal1 = ((this.amount1 * this.rateVal1) * p) / (p - 1.0d);
        double d6 = this.amount2;
        double d7 = this.rateVal2;
        double pmt2 = this.tenure2;
        double d8 = this.amount2;
        double pow = (((d6 * d7) / (1.0d - Math.pow(d7 + 1.0d, -this.tenure2))) * pmt2) - d8;
        this.totalInterest2 = pow;
        this.totalPayment2 = pow + d8;
        double p2 = Math.pow(this.rateVal2 + 1.0d, pmt2);
        this.emiVal2 = ((this.amount2 * this.rateVal2) * p2) / (p2 - 1.0d);
        double d9 = this.totalPayment2;
        if (((double) ((int) d9)) == d9) {
            this.total2.setText(String.valueOf((int) d9));
        } else {
            this.totalPayment2 = new BigDecimal(this.totalPayment2).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView = this.total2;
            textView.setText(this.totalPayment2 + " " + Constants.CURRENCY_STORED);
        }
        double d10 = this.totalPayment1;
        if (((double) ((int) d10)) == d10) {
            this.total1.setText(String.valueOf((int) d10));
        } else {
            this.totalPayment1 = new BigDecimal(this.totalPayment1).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView2 = this.total1;
            textView2.setText(this.totalPayment1 + " " + Constants.CURRENCY_STORED);
        }
        PrasingTheDouble prasingTheDouble = new PrasingTheDouble(this.totalPayment1);
        TextView textView3 = this.total1;
        textView3.setText(prasingTheDouble.getaDouble() + " " + Constants.CURRENCY_STORED);
        PrasingTheDouble prasingTheDouble2 = new PrasingTheDouble(this.totalPayment2);
        TextView textView4 = this.total2;
        textView4.setText(prasingTheDouble2.getaDouble() + " " + Constants.CURRENCY_STORED);
        double d11 = this.totalPayment1;
        double d12 = this.totalPayment2;
        if (d11 > d12) {
            this.total1.setTextColor(Color.parseColor("#26CA6A"));
            this.total2.setTextColor(Color.parseColor("#F80606"));
            this.diffPayment = this.totalPayment1 - this.totalPayment2;
        } else if (d11 == d12) {
            this.total1.setTextColor(Color.parseColor("#FF6D00"));
            this.total2.setTextColor(Color.parseColor("#FF6D00"));
            this.diffPayment = 0.0d;
        } else {
            this.total2.setTextColor(Color.parseColor("#26CA6A"));
            this.total1.setTextColor(Color.parseColor("#F80606"));
            this.diffPayment = this.totalPayment2 - this.totalPayment1;
        }
        this.diffPayment = new BigDecimal(this.diffPayment).setScale(1, RoundingMode.HALF_UP).doubleValue();
        TextView textView5 = this.difTotal;
        textView5.setText("Difference:  " + this.diffPayment + " " + Constants.CURRENCY_STORED);
        double d13 = this.totalInterest2;
        NestedScrollView nestedScrollView = scrollView;
        if (((double) ((int) d13)) == d13) {
            this.interest2.setText(String.valueOf((int) d13));
        } else {
            this.totalInterest2 = new BigDecimal(this.totalInterest2).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView6 = this.interest2;
            textView6.setText(this.totalInterest2 + " " + Constants.CURRENCY_STORED);
        }
        PrasingTheDouble prasingTheDouble3 = new PrasingTheDouble(this.totalInterest2);
        TextView textView7 = this.interest2;
        textView7.setText(prasingTheDouble3.getaDouble() + " " + Constants.CURRENCY_STORED);
        double d14 = this.totalInterest1;
        String str = "Difference:  ";
        if (((double) ((int) d14)) == d14) {
            this.interest1.setText(String.valueOf((int) d14));
        } else {
            this.totalInterest1 = new BigDecimal(this.totalInterest1).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView8 = this.interest1;
            textView8.setText(this.totalInterest1 + " " + Constants.CURRENCY_STORED);
        }
        PrasingTheDouble prasingTheDouble4 = new PrasingTheDouble(this.totalInterest1);
        TextView textView9 = this.interest1;
        textView9.setText(prasingTheDouble4.getaDouble() + " " + Constants.CURRENCY_STORED);
        double d15 = this.totalInterest1;
        PrasingTheDouble prasingTheDouble5 = prasingTheDouble4;
        double d16 = this.totalInterest2;
        if (d15 > d16) {
            this.diffInterest = d15 - d16;
            this.interest1.setTextColor(Color.parseColor("#26CA6A"));
            this.interest2.setTextColor(Color.parseColor("#F80606"));
        } else if (d15 == d16) {
            this.diffInterest = 0.0d;
            this.interest1.setTextColor(Color.parseColor("#FF6D00"));
            this.interest2.setTextColor(Color.parseColor("#FF6D00"));
        } else {
            this.diffInterest = d16 - d15;
            this.interest2.setTextColor(Color.parseColor("#26CA6A"));
            this.interest1.setTextColor(Color.parseColor("#F80606"));
        }
        BigDecimal bd = new BigDecimal(this.diffInterest).setScale(1, RoundingMode.HALF_UP);
        this.diffInterest = bd.doubleValue();
        TextView textView10 = this.difInter;
        textView10.setText(str + this.diffInterest + " " + Constants.CURRENCY_STORED);
        double d17 = this.emiVal2;
        BigDecimal bd2 = bd;
        if (((double) ((int) d17)) == d17) {
            this.emi2.setText(String.valueOf((int) d17));
            BigDecimal bigDecimal = bd2;
        } else {
            this.emiVal2 = new BigDecimal(this.emiVal2).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView11 = this.emi2;
            textView11.setText(this.emiVal2 + " " + Constants.CURRENCY_STORED);
        }
        PrasingTheDouble prasingTheDouble6 = new PrasingTheDouble(this.emiVal2);
        TextView textView12 = this.emi2;
        textView12.setText(prasingTheDouble6.getaDouble() + " " + Constants.CURRENCY_STORED);
        double d18 = this.emiVal1;
        if (((double) ((int) d18)) == d18) {
            this.emi1.setText(String.valueOf((int) d18));
        } else {
            this.emiVal1 = new BigDecimal(this.emiVal1).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView13 = this.emi1;
            textView13.setText(this.emiVal1 + " " + Constants.CURRENCY_STORED);
        }
        PrasingTheDouble prasingTheDouble7 = new PrasingTheDouble(this.emiVal1);
        TextView textView14 = this.emi1;
        textView14.setText(prasingTheDouble7.getaDouble() + " " + Constants.CURRENCY_STORED);
        double d19 = this.emiVal1;
        double d20 = this.emiVal2;
        if (d19 > d20) {
            this.diffEmiVal = d19 - d20;
            this.emi1.setTextColor(Color.parseColor("#26CA6A"));
            this.emi2.setTextColor(Color.parseColor("#F80606"));
        } else if (d19 == d20) {
            this.diffEmiVal = 0.0d;
            this.emi1.setTextColor(Color.parseColor("#FF6D00"));
            this.emi2.setTextColor(Color.parseColor("#FF6D00"));
        } else {
            this.diffEmiVal = d20 - d19;
            this.emi2.setTextColor(Color.parseColor("#26CA6A"));
            this.emi1.setTextColor(Color.parseColor("#F80606"));
        }
        this.diffEmiVal = new BigDecimal(this.diffEmiVal).setScale(1, RoundingMode.HALF_UP).doubleValue();
        TextView textView15 = this.difEmi;
        textView15.setText(str + this.diffEmiVal + " " + Constants.CURRENCY_STORED);
    }

    public void pdf(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService("input_method");
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (this.principal1.getText().toString().isEmpty() || this.principal2.getText().toString().isEmpty() || this.rate1.getText().toString().isEmpty() || this.rate2.getText().toString().isEmpty() || this.time1.getText().toString().isEmpty()) {
        } else if (this.time2.getText().toString().isEmpty()) {
            InputMethodManager inputMethodManager = imm;
        } else {
            this.tenure1 = Double.parseDouble(this.time1.getText().toString());
            this.tenure2 = Double.parseDouble(this.time2.getText().toString());
            int selectedid = this.group.getCheckedRadioButtonId();
            RadioButton radioButton2 = (RadioButton) findViewById(selectedid);
            this.radioButton = radioButton2;
            if (radioButton2.getText().toString().equals("year")) {
                this.tenure1 = Double.parseDouble(this.time1.getText().toString()) * 12.0d;
                this.tenure2 = Double.parseDouble(this.time2.getText().toString()) * 12.0d;
            } else {
                this.tenure1 = Double.parseDouble(this.time1.getText().toString());
                this.tenure2 = Double.parseDouble(this.time2.getText().toString());
            }
            if (this.tenure1 <= 30.0d || this.tenure2 <= 30.0d || Double.parseDouble(this.rate1.getText().toString()) <= 50.0d || Double.parseDouble(this.rate2.getText().toString()) <= 50.0d) {
                calculation();
                if (this.ad.isLoaded()) {
                    this.ad.show();
                }
                loadInterstitialAd();
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 0);
                PdfDocument myPdf = new PdfDocument();
                Paint myPaint = new Paint();
                PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                PdfDocument.Page myPage = myPdf.startPage(myPageInfo);
                Canvas canvas = myPage.getCanvas();
                canvas.drawBitmap(this.scalebmp, 0.0f, 0.0f, myPaint);
                myPaint.setTextSize(33.0f);
                myPaint.setTextAlign(Paint.Align.CENTER);
                int selectedd = this.group.getCheckedRadioButtonId();
                this.radioButton = (RadioButton) findViewById(selectedd);
                canvas.drawText(this.amount1 + " " + Constants.CURRENCY_STORED, 300.0f, 735.0f, myPaint);
                StringBuilder sb = new StringBuilder();
                int i = selectedd;
                sb.append(this.amount2);
                sb.append(" ");
                sb.append(Constants.CURRENCY_STORED);
                canvas.drawText(sb.toString(), 900.0f, 735.0f, myPaint);
                canvas.drawText((this.rateVal1 * 1200.0d) + " " + Constants.CURRENCY_STORED, 300.0f, 985.0f, myPaint);
                StringBuilder sb2 = new StringBuilder();
                PdfDocument.PageInfo pageInfo = myPageInfo;
                sb2.append(this.rateVal2 * 1200.0d);
                sb2.append(" ");
                sb2.append(Constants.CURRENCY_STORED);
                canvas.drawText(sb2.toString(), 900.0f, 985.0f, myPaint);
                canvas.drawText(this.tenure1 + " months", 300.0f, 1235.0f, myPaint);
                StringBuilder sb3 = new StringBuilder();
                int selectedid2 = selectedid;
                sb3.append(this.tenure2);
                sb3.append(" months");
                canvas.drawText(sb3.toString(), 900.0f, 1235.0f, myPaint);
                canvas.drawText(this.emiVal1 + " " + Constants.CURRENCY_STORED, 300.0f, 1550.0f, myPaint);
                canvas.drawText(this.emiVal2 + " " + Constants.CURRENCY_STORED, 900.0f, 1550.0f, myPaint);
                canvas.drawText(String.valueOf(this.diffEmiVal), 750.0f, 1695.0f, myPaint);
                myPdf.finishPage(myPage);
                PdfDocument.Page myPage2 = myPdf.startPage(new PdfDocument.PageInfo.Builder(1200, 2010, 2).create());
                Canvas canvas2 = myPage2.getCanvas();
                canvas2.drawBitmap(this.scaledusrabmp, 0.0f, 0.0f, myPaint);
                StringBuilder sb4 = new StringBuilder();
                PdfDocument.Page myPage22 = myPage2;
                sb4.append(this.totalInterest1);
                sb4.append(" ");
                sb4.append(Constants.CURRENCY_STORED);
                canvas2.drawText(sb4.toString(), 300.0f, 340.0f, myPaint);
                StringBuilder sb5 = new StringBuilder();
                int i2 = selectedid2;
                sb5.append(this.totalInterest2);
                sb5.append(" ");
                sb5.append(Constants.CURRENCY_STORED);
                canvas2.drawText(sb5.toString(), 900.0f, 340.0f, myPaint);
                canvas2.drawText(String.valueOf(this.diffInterest), 750.0f, 480.0f, myPaint);
                canvas2.drawText(this.totalPayment1 + " " + Constants.CURRENCY_STORED, 300.0f, 825.0f, myPaint);
                canvas2.drawText(this.totalPayment2 + " " + Constants.CURRENCY_STORED, 900.0f, 825.0f, myPaint);
                canvas2.drawText(String.valueOf(this.diffInterest), 750.0f, 960.0f, myPaint);
                PdfDocument.Page myPage23 = myPage22;
                myPdf.finishPage(myPage23);
                Calendar calendar = Calendar.getInstance();
                File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(externalStoragePublicDirectory, "/CompareLoan" + calendar.get(5) + ".pdf");
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
                    InputMethodManager inputMethodManager2 = imm;
                    intent1.addFlags(1);
                    try {
                        startActivity(intent1);
                        return;
                    } catch (ActivityNotFoundException e2) {
                        ActivityNotFoundException activityNotFoundException = e2;
                        PdfDocument.Page page = myPage23;
                        Toast.makeText(this, "Download", 0).show();
                        return;
                    }
                } else {
                    PdfDocument.Page page2 = myPage23;
                    return;
                }
            } else if (this.tenure1 > 30.0d || this.tenure2 > 30.0d) {
                Toast.makeText(this, "tenure should be less than 30 years", 0).show();
                InputMethodManager inputMethodManager3 = imm;
                return;
            } else {
                Toast.makeText(this, "Interest rate should be less than 50%", 0).show();
                InputMethodManager inputMethodManager4 = imm;
                return;
            }
        }
        Toast.makeText(this, "Enter the * value", 0).show();
    }

    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_compareloan);
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
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scroller);
        if (Build.VERSION.SDK_INT >= 23) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY < 800) {
                        compareloan.this.madview.animate().translationY(0.0f);
                    } else {
                        compareloan.this.madview.animate().translationY(100.0f);
                    }
                }
            });
        }
        final UnifiedNativeAd[] nativeAd = new UnifiedNativeAd[1];
        new AdLoader.Builder((Context) this, this.nativeAds).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                nativeAd[0] = unifiedNativeAd;
                UnifiedNativeAdView adview = (UnifiedNativeAdView) compareloan.this.getLayoutInflater().inflate(R.layout.nativeads, (ViewGroup) null);
                LinearLayout container = (LinearLayout) compareloan.this.findViewById(R.id.nativead);
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
        this.principal1 = (EditText) findViewById(R.id.principalamoount1);
        this.principal2 = (EditText) findViewById(R.id.principalamoount2);
        this.rate1 = (EditText) findViewById(R.id.interestamoount1);
        this.rate2 = (EditText) findViewById(R.id.interestamoount2);
        this.time1 = (EditText) findViewById(R.id.tenure1);
        this.time2 = (EditText) findViewById(R.id.tenure2);
        this.emi1 = (TextView) findViewById(R.id.emi1);
        this.emi2 = (TextView) findViewById(R.id.emi2);
        this.interest1 = (TextView) findViewById(R.id.totalinterest1);
        this.interest2 = (TextView) findViewById(R.id.totalinterest2);
        this.total1 = (TextView) findViewById(R.id.totalamount1);
        this.total2 = (TextView) findViewById(R.id.totalamount2);
        this.difEmi = (TextView) findViewById(R.id.emidifference);
        this.difInter = (TextView) findViewById(R.id.interestdifference);
        this.difTotal = (TextView) findViewById(R.id.paymentdifference);
        this.group = (RadioGroup) findViewById(R.id.togle);
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.compareone);
        this.bmp = decodeResource;
        this.scalebmp = Bitmap.createScaledBitmap(decodeResource, 1200, 2010, false);
        Bitmap decodeResource2 = BitmapFactory.decodeResource(getResources(), R.drawable.comparetwo);
        this.dusrabmp = decodeResource2;
        this.scaledusrabmp = Bitmap.createScaledBitmap(decodeResource2, 1200, 2010, false);
        ReviewManager create = ReviewManagerFactory.create(this);
        this.manger = create;
        create.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            public void onComplete(Task<ReviewInfo> task) {
                if (task.isSuccessful()) {
                    compareloan.this.reviewInfo = task.getResult();
                }
            }
        });
        this.frontshare = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, 0);
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
            this.ad.setAdListener(new AdListener() {
                public void onAdClosed() {
                    compareloan.this.finish();
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
