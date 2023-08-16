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

public class loaneligibility extends AppCompatActivity {
    TextView Emiofloan;
    EditText Otheremi;
    InterstitialAd ad;
    String appLink;
    Bitmap bmp;
    double eligbleloan;
    String eligibility;
    TextView eligible;
    double eligibleemi;
    TextView emielgible;
    double foir;
    SharedPreferences frontshare;
    double givenemi;
    RadioGroup group;
    double income;
    String interAds;
    double interest;
    EditText interestrate;
    boolean isCalculated;
    double loanamout;
    EditText loanaomunt;
    TextView loaneligible;
    AdView madview;
    ReviewManager manger;
    EditText monthlyincome;
    String nativeAds;
    double preemi;
    String premiumLink;
    RadioButton radioButton;
    ReviewInfo reviewInfo;
    Bitmap scalebmp;
    LinearLayout showeligible;
    LinearLayout showemi;
    double tenure;
    EditText time;

    public void calculate(View view) {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (this.monthlyincome.getText().toString().isEmpty() || this.interestrate.getText().toString().isEmpty() || this.time.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter the * value", 0).show();
            return;
        }
        this.tenure = Double.parseDouble(this.time.getText().toString());
        RadioButton radioButton2 = (RadioButton) findViewById(this.group.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.tenure = Double.parseDouble(this.time.getText().toString()) * 12.0d;
        } else {
            this.tenure = Double.parseDouble(this.time.getText().toString());
        }
        if (this.tenure > 360.0d || Double.parseDouble(this.interestrate.getText().toString()) > 50.0d || Double.parseDouble(this.monthlyincome.getText().toString()) < 8000.0d) {
            if (Double.parseDouble(this.monthlyincome.getText().toString()) < 8000.0d) {
                Toast.makeText(this, "Monthly Income should be greater than 8000Rs", 0).show();
            }
            if (this.tenure > 360.0d) {
                Toast.makeText(this, "tenure should be less than 30 years", 0).show();
            } else {
                Toast.makeText(this, "Interest rate should be less than 50%", 0).show();
            }
        } else {
            if (this.Otheremi.getText().toString().isEmpty()) {
                this.preemi = 0.0d;
            }
            if (this.loanaomunt.getText().toString().isEmpty()) {
                this.loanamout = 0.0d;
            }
            calculation();
        }
    }

    public void share(View view) {
        if (this.monthlyincome.getText().toString().isEmpty() || this.interestrate.getText().toString().isEmpty() || this.time.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter the mandatory value value", 0).show();
            return;
        }
        this.tenure = Double.parseDouble(this.time.getText().toString());
        RadioButton radioButton2 = (RadioButton) findViewById(this.group.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.tenure = Double.parseDouble(this.time.getText().toString()) * 12.0d;
        } else {
            this.tenure = Double.parseDouble(this.time.getText().toString());
        }
        if (this.tenure > 30.0d || Double.parseDouble(this.interestrate.getText().toString()) > 50.0d || Double.parseDouble(this.monthlyincome.getText().toString()) < 8000.0d) {
            if (Double.parseDouble(this.monthlyincome.getText().toString()) > 8000.0d) {
                Toast.makeText(this, "tenure should be less than 8000Rs", 0).show();
            }
            if (this.tenure > 30.0d) {
                Toast.makeText(this, "tenure should be less than 30 years", 0).show();
            } else {
                Toast.makeText(this, "Interest rate should be less than 50%", 0).show();
            }
        } else {
            if (this.Otheremi.getText().toString().isEmpty()) {
                this.preemi = 0.0d;
            }
            if (this.loanaomunt.getText().toString().isEmpty()) {
                this.loanamout = 0.0d;
            }
            calculation();
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", "EMI- A Calculator app");
            intent.putExtra("android.intent.extra.TEXT", "Loan Eligibility-\n \nGross Monthly Income: " + this.income + "\nTotal Monthly EMI: " + this.preemi + "\nLoan Amount: " + this.loanamout + "\nAnnual Interest Rate: " + (this.interest * 1200.0d) + "\nTenure: " + this.tenure + " months\n\nAre you eligible: " + this.eligibility + "\nEMI of Loan: " + this.givenemi + "\nLoan Amount you are eligible for : " + this.eligbleloan + "\nEMI you are eligible for : " + this.eligibleemi + "\n\n" + this.appLink);
            startActivity(Intent.createChooser(intent, "Share Using"));
        }
    }

    public void pdf(View view) {
        if (this.monthlyincome.getText().toString().isEmpty() || this.interestrate.getText().toString().isEmpty() || this.time.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter the mandatory inputs", 0).show();
            return;
        }
        this.tenure = Double.parseDouble(this.time.getText().toString());
        RadioButton radioButton2 = (RadioButton) findViewById(this.group.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.tenure = Double.parseDouble(this.time.getText().toString()) * 12.0d;
        } else {
            this.tenure = Double.parseDouble(this.time.getText().toString());
        }
        if (this.tenure > 360.0d || Double.parseDouble(this.interestrate.getText().toString()) > 50.0d || Double.parseDouble(this.monthlyincome.getText().toString()) < 8000.0d) {
            if (Double.parseDouble(this.monthlyincome.getText().toString()) < 8000.0d) {
                Toast.makeText(this, "income should be greater than 8000Rs", 0).show();
            }
            if (this.tenure > 30.0d) {
                Toast.makeText(this, "tenure should be less than 30 years", 0).show();
            } else {
                Toast.makeText(this, "Interest rate should be less than 50%", 0).show();
            }
        } else {
            if (this.Otheremi.getText().toString().isEmpty()) {
                this.preemi = 0.0d;
            }
            if (this.loanaomunt.getText().toString().isEmpty()) {
                this.loanamout = 0.0d;
            }
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
            canvas.drawText(this.preemi + " " + Constants.CURRENCY_STORED, 900.0f, 600.0f, myPaint);
            canvas.drawText(this.loanamout + " " + Constants.CURRENCY_STORED, 900.0f, 725.0f, myPaint);
            StringBuilder sb = new StringBuilder();
            sb.append(this.interest * 1200.0d);
            sb.append(" %");
            canvas.drawText(sb.toString(), 900.0f, 875.0f, myPaint);
            canvas.drawText(this.tenure + " months", 900.0f, 1025.0f, myPaint);
            canvas.drawText(this.eligibility, 300.0f, 1325.0f, myPaint);
            canvas.drawText(this.givenemi + " " + Constants.CURRENCY_STORED, 900.0f, 1325.0f, myPaint);
            canvas.drawText(this.eligbleloan + " " + Constants.CURRENCY_STORED, 300.0f, 1660.0f, myPaint);
            canvas.drawText(this.eligibleemi + " " + Constants.CURRENCY_STORED, 900.0f, 1660.0f, myPaint);
            canvas.drawText(String.valueOf(this.income), 900.0f, 480.0f, myPaint);
            myPdf.finishPage(myPage);
            Calendar calendar = Calendar.getInstance();
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(externalStoragePublicDirectory, "/LoanEligibility" + calendar.get(5) + ".pdf");
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

    public void calculation() {
        ((NestedScrollView) findViewById(R.id.scroller)).smoothScrollTo(0, 800);
        this.income = Double.parseDouble(this.monthlyincome.getText().toString());
        double parseDouble = Double.parseDouble(this.interestrate.getText().toString());
        this.interest = parseDouble;
        this.interest = parseDouble / 1200.0d;
        this.preemi = Double.parseDouble(this.Otheremi.getText().toString());
        this.loanamout = Double.parseDouble(this.loanaomunt.getText().toString());
        double d = this.income;
        if (d < 8000.0d || d >= 10000.0d) {
            double d2 = this.income;
            if (d2 < 10000.0d || d2 >= 25000.0d) {
                double d3 = this.income;
                if (d3 < 25000.0d || d3 >= 50000.0d) {
                    double d4 = this.income;
                    if (d4 >= 50000.0d && d4 <= 100000.0d) {
                        this.foir = 0.5d;
                    } else if (this.income > 100000.0d) {
                        this.foir = 0.55d;
                    }
                } else {
                    this.foir = 0.45d;
                }
            } else {
                this.foir = 0.4d;
            }
        } else {
            this.foir = 0.35d;
        }
        double d5 = (this.foir * this.income) - this.preemi;
        this.eligibleemi = d5;
        double d6 = this.tenure;
        this.eligbleloan = ((1.0d - Math.pow(this.interest + 1.0d, -d6)) * (d5 * d6)) / (this.tenure * this.interest);
        if (this.loanaomunt.getText().toString().isEmpty()) {
            this.showemi.setVisibility(4);
            this.showeligible.setVisibility(4);
        } else {
            this.showemi.setVisibility(0);
            this.showeligible.setVisibility(0);
            if (this.loanamout <= this.eligbleloan) {
                this.eligibility = "Yes";
                this.eligible.setTextColor(Color.parseColor("#00C853"));
            } else {
                this.eligibility = "No";
                this.eligible.setTextColor(Color.parseColor("#ffcc0000"));
            }
            double p = Math.pow(this.interest + 1.0d, this.tenure);
            this.givenemi = ((this.loanamout * this.interest) * p) / (p - 1.0d);
            this.eligible.setText(this.eligibility);
            double d7 = this.givenemi;
            if (((double) ((int) d7)) == d7) {
                this.Emiofloan.setText(String.valueOf((int) d7));
            } else {
                double doubleValue = new BigDecimal(this.givenemi).setScale(1, RoundingMode.HALF_UP).doubleValue();
                this.givenemi = doubleValue;
                this.Emiofloan.setText(String.valueOf(doubleValue));
            }
            PrasingTheDouble prasingTheDouble = new PrasingTheDouble(this.givenemi);
            TextView textView = this.Emiofloan;
            textView.setText(prasingTheDouble.getaDouble() + " " + Constants.CURRENCY_STORED);
        }
        double d8 = this.eligbleloan;
        if (((double) ((int) d8)) == d8) {
            this.loaneligible.setText(String.valueOf((int) d8));
        } else {
            double doubleValue2 = new BigDecimal(this.eligbleloan).setScale(1, RoundingMode.HALF_UP).doubleValue();
            this.eligbleloan = doubleValue2;
            this.loaneligible.setText(String.valueOf(doubleValue2));
        }
        PrasingTheDouble prasingTheDouble2 = new PrasingTheDouble(this.eligbleloan);
        TextView textView2 = this.loaneligible;
        textView2.setText(prasingTheDouble2.getaDouble() + " " + Constants.CURRENCY_STORED);
        double d9 = this.eligibleemi;
        if (((double) ((int) d9)) == d9) {
            this.emielgible.setText(String.valueOf((int) d9));
        } else {
            double doubleValue3 = new BigDecimal(this.eligibleemi).setScale(1, RoundingMode.HALF_UP).doubleValue();
            this.eligibleemi = doubleValue3;
            this.emielgible.setText(String.valueOf(doubleValue3));
        }
        PrasingTheDouble prasingTheDouble3 = new PrasingTheDouble(this.eligibleemi);
        TextView textView3 = this.emielgible;
        textView3.setText(prasingTheDouble3.getaDouble() + " " + Constants.CURRENCY_STORED);
    }

    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_loaneligibility);
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
                    loaneligibility.this.reviewInfo = task.getResult();
                    return;
                }
                Toast.makeText(loaneligibility.this, "Error", 0).show();
            }
        });
        this.frontshare = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, 0);
        this.madview = (AdView) findViewById(R.id.adView);
        this.madview.loadAd(new AdRequest.Builder().build());
        final UnifiedNativeAd[] nativeAd = new UnifiedNativeAd[1];
        new AdLoader.Builder((Context) this, this.nativeAds).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                nativeAd[0] = unifiedNativeAd;
                UnifiedNativeAdView adview = (UnifiedNativeAdView) loaneligibility.this.getLayoutInflater().inflate(R.layout.nativeads, (ViewGroup) null);
                LinearLayout container = (LinearLayout) loaneligibility.this.findViewById(R.id.nativead);
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
        this.eligible = (TextView) findViewById(R.id.eligibility);
        this.Emiofloan = (TextView) findViewById(R.id.emiofloan);
        this.loaneligible = (TextView) findViewById(R.id.eligibleloan);
        this.emielgible = (TextView) findViewById(R.id.eligibleemi);
        this.monthlyincome = (EditText) findViewById(R.id.income);
        this.Otheremi = (EditText) findViewById(R.id.otheremi);
        this.loanaomunt = (EditText) findViewById(R.id.loan);
        this.interestrate = (EditText) findViewById(R.id.interest);
        this.time = (EditText) findViewById(R.id.tenure);
        ((LinearLayout) findViewById(R.id.mainlinear)).startAnimation(AnimationUtils.loadAnimation(this, R.anim.slideup));
        this.showeligible = (LinearLayout) findViewById(R.id.showeligible);
        this.showemi = (LinearLayout) findViewById(R.id.showemi);
        this.group = (RadioGroup) findViewById(R.id.togle);
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.loaneligibilty);
        this.bmp = decodeResource;
        this.scalebmp = Bitmap.createScaledBitmap(decodeResource, 1200, 2010, false);
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scroller);
        if (Build.VERSION.SDK_INT >= 23) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY < 800) {
                        loaneligibility.this.madview.animate().translationY(0.0f);
                    } else {
                        loaneligibility.this.madview.animate().translationY(100.0f);
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
                    loaneligibility.this.finish();
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
