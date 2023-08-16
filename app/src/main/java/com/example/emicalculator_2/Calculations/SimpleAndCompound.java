package com.example.emicalculator_2.Calculations;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class SimpleAndCompound extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    double Tenure;
    InterstitialAd ad;
    String appLink;
    Bitmap bmp;
    String choosen = "Compound interest";
    double expectedRate;
    SharedPreferences frontshare;
    RadioGroup group;
    String interAds;
    TextView interest;
    double investmentAmount;
    EditText investmentEdit;
    boolean isCalculated;
    AdView madview;
    ReviewManager manger;
    TextView maturity;
    double maturityValue;
    String nativeAds;
    TextView netAmount;
    String premiumLink;
    RadioButton radioButton;
    EditText rateEdit;
    ReviewInfo reviewInfo;
    Bitmap scalebmp;
    Spinner spiner;
    List<String> spinner = new ArrayList();
    EditText time;
    double totalInterest;
    double totalInvestment;

    public void calculate(View view) {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (this.investmentEdit.getText().toString().isEmpty() || this.rateEdit.getText().toString().isEmpty() || this.time.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Inputs", 0).show();
            return;
        }
        this.Tenure = Double.parseDouble(this.time.getText().toString());
        RadioButton radioButton2 = (RadioButton) findViewById(this.group.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.Tenure = Double.parseDouble(this.time.getText().toString()) * 12.0d;
        } else {
            this.Tenure = Double.parseDouble(this.time.getText().toString());
        }
        if (this.Tenure <= 360.0d && Double.parseDouble(this.rateEdit.getText().toString()) <= 50.0d) {
            calculation();
        } else if (this.Tenure > 360.0d) {
            Toast.makeText(this, "Tenure should be less than 30 years", 0).show();
        } else {
            Toast.makeText(this, "interest rate should be less than 50%", 0).show();
        }
    }

    public void share(View view) {
        if (this.investmentEdit.getText().toString().isEmpty() || this.rateEdit.getText().toString().isEmpty() || this.time.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Inputs", 0).show();
            return;
        }
        this.Tenure = Double.parseDouble(this.time.getText().toString());
        RadioButton radioButton2 = (RadioButton) findViewById(this.group.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.Tenure = Double.parseDouble(this.time.getText().toString()) * 12.0d;
        } else {
            this.Tenure = Double.parseDouble(this.time.getText().toString());
        }
        if (this.Tenure <= 360.0d && Double.parseDouble(this.rateEdit.getText().toString()) <= 50.0d) {
            calculation();
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", "EMI- A Calculator app");
            intent.putExtra("android.intent.extra.TEXT", "Simple & Compound interest Details-\n\ninvestmentEdit Amount : " + this.investmentAmount + "\nTenure : " + this.Tenure + "months\n\nTotal investmentEdit Amount: " + this.totalInvestment + "\ninterest Value: " + this.totalInterest + "\nmaturity Value: " + this.maturityValue + "\n\nCalculate by EMI\n" + this.appLink);
            startActivity(Intent.createChooser(intent, "Share Using"));
        } else if (this.Tenure > 360.0d) {
            Toast.makeText(this, "tenure should be less than 30 years", 0).show();
        } else {
            Toast.makeText(this, "interest rate should be less than 50%", 0).show();
        }
    }

    public void pdf(View view) {
        if (this.investmentEdit.getText().toString().isEmpty() || this.rateEdit.getText().toString().isEmpty() || this.time.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Inputs", 0).show();
            return;
        }
        this.Tenure = Double.parseDouble(this.time.getText().toString());
        int selectedid = this.group.getCheckedRadioButtonId();
        RadioButton radioButton2 = (RadioButton) findViewById(selectedid);
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.Tenure = Double.parseDouble(this.time.getText().toString()) * 12.0d;
        } else {
            this.Tenure = Double.parseDouble(this.time.getText().toString());
        }
        if (this.Tenure > 360.0d || Double.parseDouble(this.rateEdit.getText().toString()) > 50.0d) {
            if (this.Tenure > 360.0d) {
                Toast.makeText(this, "tenure should be less than 30 years", 0).show();
            } else {
                Toast.makeText(this, "interest rate should be less than 50%", 0).show();
            }
        } else {
            calculation();
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
            myPaint.setTextSize(33.0f);
            myPaint.setTextAlign(Paint.Align.CENTER);
            this.radioButton = (RadioButton) findViewById(this.group.getCheckedRadioButtonId());
            canvas.drawText(this.investmentAmount + " " + Constants.CURRENCY_STORED, 900.0f, 575.0f, myPaint);
            StringBuilder sb = new StringBuilder();
            sb.append(this.expectedRate * 1200.0d);
            sb.append(" %");
            canvas.drawText(sb.toString(), 900.0f, 700.0f, myPaint);
            canvas.drawText(this.Tenure + "  months", 900.0f, 850.0f, myPaint);
            canvas.drawText(this.totalInvestment + " " + Constants.CURRENCY_STORED, 300.0f, 1150.0f, myPaint);
            StringBuilder sb2 = new StringBuilder();
            int i = selectedid;
            sb2.append(this.totalInterest);
            sb2.append(" ");
            sb2.append(Constants.CURRENCY_STORED);
            canvas.drawText(sb2.toString(), 900.0f, 1150.0f, myPaint);
            canvas.drawText(this.maturityValue + " " + Constants.CURRENCY_STORED, 600.0f, 1450.0f, myPaint);
            canvas.drawText(this.choosen, 900.0f, 480.0f, myPaint);
            myPdf.finishPage(myPage);
            Calendar calendar = Calendar.getInstance();
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(externalStoragePublicDirectory, "/Interest" + calendar.get(5) + ".pdf");
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
                    ActivityNotFoundException activityNotFoundException = e2;
                    Toast.makeText(this, "Download", 0).show();
                }
            }
        }
    }

    public void loadInterstitialAd() {
        this.ad.loadAd(new AdRequest.Builder().build());
    }

    public void calculation() {
        ((NestedScrollView) findViewById(R.id.scroller)).smoothScrollTo(0, 800);
        this.investmentAmount = Double.parseDouble(this.investmentEdit.getText().toString());
        this.Tenure = Double.parseDouble(this.time.getText().toString());
        RadioButton radioButton2 = (RadioButton) findViewById(this.group.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.Tenure = Double.parseDouble(this.time.getText().toString()) * 12.0d;
        } else {
            this.Tenure = Double.parseDouble(this.time.getText().toString());
        }
        double parseDouble = Double.parseDouble(this.rateEdit.getText().toString());
        this.expectedRate = parseDouble;
        this.expectedRate = parseDouble / 1200.0d;
        if (this.choosen.equals("Compound interest")) {
            this.maturityValue = this.investmentAmount * Math.pow(this.expectedRate + 1.0d, this.Tenure);
        } else {
            double d = this.investmentAmount;
            this.maturityValue = d + (this.expectedRate * d * this.Tenure);
        }
        double d2 = this.investmentAmount;
        this.totalInvestment = d2;
        double d3 = this.maturityValue - d2;
        this.totalInterest = d3;
        if (((double) ((int) d3)) == d3) {
            this.interest.setText(String.valueOf((int) d3));
        } else {
            this.totalInterest = new BigDecimal(this.totalInterest).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView = this.interest;
            textView.setText(this.totalInterest + " " + Constants.CURRENCY_STORED);
        }
        PrasingTheDouble prasingTheDouble = new PrasingTheDouble(this.totalInterest);
        TextView textView2 = this.interest;
        textView2.setText(prasingTheDouble.getaDouble() + " " + Constants.CURRENCY_STORED);
        double d4 = this.totalInvestment;
        if (((double) ((int) d4)) == d4) {
            TextView textView3 = this.netAmount;
            textView3.setText(((int) d4) + " " + Constants.CURRENCY_STORED);
        } else {
            this.totalInvestment = new BigDecimal(this.totalInvestment).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView4 = this.netAmount;
            textView4.setText(this.totalInvestment + " " + Constants.CURRENCY_STORED);
        }
        PrasingTheDouble prasingTheDouble2 = new PrasingTheDouble(this.totalInvestment);
        TextView textView5 = this.netAmount;
        textView5.setText(prasingTheDouble2.getaDouble() + " " + Constants.CURRENCY_STORED);
        double d5 = this.maturityValue;
        if (((double) ((int) d5)) == d5) {
            this.maturity.setText(String.valueOf((int) d5));
        } else {
            this.maturityValue = new BigDecimal(this.maturityValue).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView6 = this.maturity;
            textView6.setText(this.maturityValue + " " + Constants.CURRENCY_STORED);
        }
        PrasingTheDouble prasingTheDouble3 = new PrasingTheDouble(this.maturityValue);
        TextView textView7 = this.maturity;
        textView7.setText(prasingTheDouble3.getaDouble() + " " + Constants.CURRENCY_STORED);
    }

    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_simple_and_compound);
        this.nativeAds = getString(R.string.nativeAds);
        this.premiumLink = getString(R.string.premiumAppLink);
        this.appLink = getString(R.string.appLink);
        this.interAds = getString(R.string.InterstialAds);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        ReviewManager create = ReviewManagerFactory.create(this);
        this.manger = create;
        create.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            public void onComplete(Task<ReviewInfo> task) {
                if (task.isSuccessful()) {
                    SimpleAndCompound.this.reviewInfo = task.getResult();
                    return;
                }
                Toast.makeText(SimpleAndCompound.this, "Error", 0).show();
            }
        });
        this.frontshare = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, 0);
        this.madview = (AdView) findViewById(R.id.adView);
        this.madview.loadAd(new AdRequest.Builder().build());
        final UnifiedNativeAd[] nativeAd = new UnifiedNativeAd[1];
        new AdLoader.Builder((Context) this, this.nativeAds).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                nativeAd[0] = unifiedNativeAd;
                UnifiedNativeAdView adview = (UnifiedNativeAdView) SimpleAndCompound.this.getLayoutInflater().inflate(R.layout.nativeads, (ViewGroup) null);
                LinearLayout container = (LinearLayout) SimpleAndCompound.this.findViewById(R.id.nativead);
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
        this.investmentEdit = (EditText) findViewById(R.id.amount);
        this.rateEdit = (EditText) findViewById(R.id.rateoftax);
        this.time = (EditText) findViewById(R.id.tenure);
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.simpledetails);
        this.bmp = decodeResource;
        this.scalebmp = Bitmap.createScaledBitmap(decodeResource, 1200, 2010, false);
        this.interest = (TextView) findViewById(R.id.interestValue);
        this.netAmount = (TextView) findViewById(R.id.netamount);
        this.maturity = (TextView) findViewById(R.id.maturityAmount);
        ((LinearLayout) findViewById(R.id.mainlinear)).startAnimation(AnimationUtils.loadAnimation(this, R.anim.slideup));
        this.group = (RadioGroup) findViewById(R.id.togle);
        this.spiner = (Spinner) findViewById(R.id.spinner);
        this.spinner.add("Compound interest");
        this.spinner.add("Simple interest");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, 17367048, this.spinner);
        dataAdapter.setDropDownViewResource(17367049);
        this.spiner.setAdapter(dataAdapter);
        this.spiner.setOnItemSelectedListener(this);
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scroller);
        if (Build.VERSION.SDK_INT >= 23) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY < 800) {
                        SimpleAndCompound.this.madview.animate().translationY(0.0f);
                    } else {
                        SimpleAndCompound.this.madview.animate().translationY(100.0f);
                    }
                }
            });
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.choosen = parent.getItemAtPosition(position).toString();
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
                    SimpleAndCompound.this.finish();
                }
            });
        } else {
            super.onBackPressed();
        }
    }
}
