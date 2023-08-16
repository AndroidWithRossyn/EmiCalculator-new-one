package com.example.emicalculator_2.Calculations;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.emicalculator_2.Constants;
import com.example.emicalculator_2.Fragments.Statement;
import com.example.emicalculator_2.Fragments.Statementsip;
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
import com.google.android.gms.measurement.api.AppMeasurementSdk;
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

public class Swp extends AppCompatActivity implements Statementsip.onMessageReadListner {
    String Mmonth;
    double Tenure;
    EditText Time;
    InterstitialAd ad;
    String appLink;
    Bitmap bmp;
    int date;
    Button day;
    double expectedRate;
    FrameLayout frameLayout;
    SharedPreferences frontshare;
    RadioGroup group;
    String interAds;
    EditText investment;
    double investmentAmount;
    boolean isCalculated;
    AdView madview;
    ReviewManager manger;
    TextView maturity;
    TextView maturityDate;
    double maturityValue;
    int mmonth;
    int month;
    SQLiteDatabase myDatabase;
    int myear;
    String name;
    String nativeAds;
    String premiumLink;
    RadioButton radioButton;
    EditText rate;
    ReviewInfo reviewInfo;
    Bitmap scalebmp;
    DatePickerDialog.OnDateSetListener setListener;
    String tmonth;
    TextView totalInterest;
    double totalInterestAmount;
    double totalInvestAmount;
    TextView totalInvestment;
    EditText withdrawal;
    double withdrawalAmount;
    int year;

    public void calculate(View view) {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (this.investment.getText().toString().isEmpty() || this.rate.getText().toString().isEmpty() || this.Time.getText().toString().isEmpty() || this.withdrawal.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Inputs", 0).show();
            return;
        }
        this.Tenure = Double.parseDouble(this.Time.getText().toString());
        RadioButton radioButton2 = (RadioButton) findViewById(this.group.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.Tenure = Double.parseDouble(this.Time.getText().toString()) * 12.0d;
        } else {
            this.Tenure = Double.parseDouble(this.Time.getText().toString());
        }
        if (this.Tenure <= 360.0d && Double.parseDouble(this.rate.getText().toString()) <= 50.0d) {
            calculation();
        } else if (this.Tenure > 360.0d) {
            Toast.makeText(this, "Tenure should be less than 30 years", 0).show();
        } else {
            Toast.makeText(this, "Interest rate should be less than 50%", 0).show();
        }
    }

    public void share(View view) {
        if (this.investment.getText().toString().isEmpty() || this.rate.getText().toString().isEmpty() || this.Time.getText().toString().isEmpty() || this.withdrawal.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Inputs", 0).show();
            return;
        }
        this.Tenure = Double.parseDouble(this.Time.getText().toString());
        RadioButton radioButton2 = (RadioButton) findViewById(this.group.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.Tenure = Double.parseDouble(this.Time.getText().toString()) * 12.0d;
        } else {
            this.Tenure = Double.parseDouble(this.Time.getText().toString());
        }
        if (this.Tenure <= 360.0d && Double.parseDouble(this.rate.getText().toString()) <= 50.0d) {
            calculation();
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", "EMi- A Calculator app");
            intent.putExtra("android.intent.extra.TEXT", "SWP Details-\n\ninvestment Amount : " + this.investmentAmount + "\nTenure : " + this.Tenure + "months\nFirst SIP: " + this.date + " " + this.tmonth + " " + this.year + "\n\nTotal investment Amount: " + this.totalInvestAmount + "\nTotal Interest: " + this.totalInterestAmount + "\nmaturity Value: " + this.maturityValue + "\nmaturity Date: " + this.date + " " + this.Mmonth + " " + this.myear + "\n\nCalculate by EMI\n" + this.appLink);
            startActivity(Intent.createChooser(intent, "Share Using"));
        } else if (this.Tenure > 360.0d) {
            Toast.makeText(this, "tenure should be less than 30 years", 0).show();
        } else {
            Toast.makeText(this, "Interest rate should be less than 50%", 0).show();
        }
    }

    public void pdf(View view) {
        if (this.investment.getText().toString().isEmpty() || this.rate.getText().toString().isEmpty() || this.Time.getText().toString().isEmpty() || this.withdrawal.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Inputs", 0).show();
            return;
        }
        this.Tenure = Double.parseDouble(this.Time.getText().toString());
        int selectedid = this.group.getCheckedRadioButtonId();
        RadioButton radioButton2 = (RadioButton) findViewById(selectedid);
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.Tenure = Double.parseDouble(this.Time.getText().toString()) * 12.0d;
        } else {
            this.Tenure = Double.parseDouble(this.Time.getText().toString());
        }
        if (this.Tenure > 360.0d || Double.parseDouble(this.rate.getText().toString()) > 50.0d) {
            if (this.Tenure > 360.0d) {
                Toast.makeText(this, "tenure should be less than 30 years", 0).show();
            } else {
                Toast.makeText(this, "Interest rate should be less than 50%", 0).show();
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
            canvas.drawText(this.investmentAmount + " ₹", 900.0f, 575.0f, myPaint);
            canvas.drawText((this.expectedRate * 1200.0d) + " %", 900.0f, 700.0f, myPaint);
            canvas.drawText(this.Tenure + "  " + this.radioButton.getText().toString(), 900.0f, 850.0f, myPaint);
            StringBuilder sb = new StringBuilder();
            sb.append(this.totalInvestAmount);
            sb.append(" ₹");
            canvas.drawText(sb.toString(), 300.0f, 1150.0f, myPaint);
            StringBuilder sb2 = new StringBuilder();
            int i = selectedid;
            sb2.append(this.totalInterestAmount);
            sb2.append(" ₹");
            canvas.drawText(sb2.toString(), 900.0f, 1150.0f, myPaint);
            canvas.drawText(this.maturityValue + " ₹", 600.0f, 1450.0f, myPaint);
            canvas.drawText(this.date + " " + this.Mmonth + " " + this.myear, 600.0f, 1660.0f, myPaint);
            canvas.drawText(this.date + " " + this.tmonth + " " + this.year, 900.0f, 480.0f, myPaint);
            myPdf.finishPage(myPage);
            Calendar calendar = Calendar.getInstance();
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(externalStoragePublicDirectory, "/swp" + calendar.get(5) + ".pdf");
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
        double d;
        ((NestedScrollView) findViewById(R.id.scroller)).smoothScrollTo(0, 800);
        this.investmentAmount = Double.parseDouble(this.investment.getText().toString());
        this.Tenure = Double.parseDouble(this.Time.getText().toString());
        this.withdrawalAmount = Double.parseDouble(this.withdrawal.getText().toString());
        RadioButton radioButton2 = (RadioButton) findViewById(this.group.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.Tenure = Double.parseDouble(this.Time.getText().toString()) * 12.0d;
        } else {
            this.Tenure = Double.parseDouble(this.Time.getText().toString());
        }
        double parseDouble = Double.parseDouble(this.rate.getText().toString());
        this.expectedRate = parseDouble;
        this.expectedRate = parseDouble / 1200.0d;
        double ans = 0.0d;
        int i = 1;
        while (true) {
            d = this.Tenure;
            if (((double) i) > d) {
                break;
            }
            ans += Math.pow(this.expectedRate + 1.0d, d - 1.0d);
            i++;
        }
        double pow = this.investmentAmount * Math.pow(this.expectedRate + 1.0d, d);
        double d2 = this.withdrawalAmount;
        double d3 = pow - (d2 * ans);
        this.maturityValue = d3;
        this.totalInvestAmount = this.investmentAmount;
        double d4 = d3 - (d2 * this.Tenure);
        this.totalInterestAmount = d4;
        if (((double) ((int) d4)) == d4) {
            this.totalInterest.setText(String.valueOf((int) d4));
        } else {
            this.totalInterestAmount = new BigDecimal(this.totalInterestAmount).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView = this.totalInterest;
            textView.setText(this.totalInterestAmount + " " + Constants.CURRENCY_STORED);
        }
        PrasingTheDouble prasingTheDouble = new PrasingTheDouble(this.totalInterestAmount);
        TextView textView2 = this.totalInterest;
        textView2.setText(prasingTheDouble.getaDouble() + " ₹");
        double d5 = this.totalInvestAmount;
        if (((double) ((int) d5)) == d5) {
            TextView textView3 = this.totalInvestment;
            textView3.setText(((int) d5) + " ₹");
        } else {
            this.totalInvestAmount = new BigDecimal(this.totalInvestAmount).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView4 = this.totalInvestment;
            textView4.setText(this.totalInvestAmount + " ₹");
        }
        PrasingTheDouble prasingTheDouble2 = new PrasingTheDouble(this.totalInvestAmount);
        TextView textView5 = this.totalInvestment;
        textView5.setText(prasingTheDouble2.getaDouble() + " ₹");
        double d6 = this.maturityValue;
        if (((double) ((int) d6)) == d6) {
            this.maturity.setText(String.valueOf((int) d6));
        } else {
            this.maturityValue = new BigDecimal(this.maturityValue).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView6 = this.maturity;
            textView6.setText(this.maturityValue + " ₹");
        }
        PrasingTheDouble prasingTheDouble3 = new PrasingTheDouble(this.maturityValue);
        TextView textView7 = this.maturity;
        textView7.setText(prasingTheDouble3.getaDouble() + " ₹");
        double d7 = (double) this.month;
        double d8 = this.Tenure;
        Double.isNaN(d7);
        int i2 = (int) (d7 + d8);
        this.mmonth = i2;
        int index = (i2 - 1) / 12;
        this.myear = this.year + index;
        int i3 = i2 - (index * 12);
        this.mmonth = i3;
        switch (i3) {
            case 1:
                this.Mmonth = "Jan";
                break;
            case 2:
                this.Mmonth = "Feb";
                break;
            case 3:
                this.Mmonth = "Mar";
                break;
            case 4:
                this.Mmonth = "April";
                break;
            case 5:
                this.Mmonth = "May";
                break;
            case 6:
                this.Mmonth = "Jun";
                break;
            case 7:
                this.Mmonth = "July";
                break;
            case 8:
                this.Mmonth = "Aug";
                break;
            case 9:
                this.Mmonth = "Sep";
                break;
            case 10:
                this.Mmonth = "Oct";
                break;
            case 11:
                this.Mmonth = "Nov";
                break;
            case 12:
                this.Mmonth = "Dec";
                break;
        }
        TextView textView8 = this.maturityDate;
        textView8.setText(this.date + " " + this.Mmonth + " " + this.myear);
    }

    public void statistic(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService("input_method");
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (this.investment.getText().toString().isEmpty() || this.rate.getText().toString().isEmpty() || this.Time.getText().toString().isEmpty()) {
        } else if (this.withdrawal.getText().toString().isEmpty()) {
            InputMethodManager inputMethodManager = imm;
        } else {
            this.Tenure = Double.parseDouble(this.Time.getText().toString());
            int selectedid = this.group.getCheckedRadioButtonId();
            RadioButton radioButton2 = (RadioButton) findViewById(selectedid);
            this.radioButton = radioButton2;
            if (radioButton2.getText().toString().equals("year")) {
                this.Tenure = Double.parseDouble(this.Time.getText().toString()) * 12.0d;
            } else {
                this.Tenure = Double.parseDouble(this.Time.getText().toString());
            }
            if (this.Tenure > 360.0d || Double.parseDouble(this.rate.getText().toString()) > 50.0d) {
                int i = selectedid;
                if (this.Tenure > 360.0d) {
                    Toast.makeText(this, "tenure should be less than 30 years", 0).show();
                    return;
                } else {
                    Toast.makeText(this, "Interest rate should be less than 50%", 0).show();
                    return;
                }
            } else {
                calculation();
                this.frameLayout.setBackgroundColor(Color.parseColor("#59000000"));
                InputMethodManager inputMethodManager2 = imm;
                int i2 = selectedid;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack((String) null);
                transaction.setCustomAnimations(R.anim.segmentup, R.anim.segmentdown);
                transaction.add((int) R.id.frame, (Fragment) new Statementsip(this.withdrawalAmount, this.Tenure, this.expectedRate, this.investmentAmount, this.maturityValue, this.totalInterestAmount, this.date, this.month, this.year, "SWP"), "Fragment").commit();
                return;
            }
        }
        Toast.makeText(getApplicationContext(), "Enter Inputs", 0).show();
    }

    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_swp);
        this.nativeAds = getString(R.string.nativeAds);
        this.premiumLink = getString(R.string.premiumAppLink);
        this.appLink = getString(R.string.appLink);
        this.interAds = getString(R.string.InterstialAds);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ReviewManager create = ReviewManagerFactory.create(this);
        this.manger = create;
        create.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            public void onComplete(Task<ReviewInfo> task) {
                if (task.isSuccessful()) {
                    Swp.this.reviewInfo = task.getResult();
                    return;
                }
                Toast.makeText(Swp.this, "Error", 0).show();
            }
        });
        this.frontshare = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, 0);
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
                UnifiedNativeAdView adview = (UnifiedNativeAdView) Swp.this.getLayoutInflater().inflate(R.layout.nativeads, (ViewGroup) null);
                LinearLayout container = (LinearLayout) Swp.this.findViewById(R.id.nativead);
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
        this.investment = (EditText) findViewById(R.id.InvestmentAmoount);
        this.rate = (EditText) findViewById(R.id.interestAmount);
        this.Time = (EditText) findViewById(R.id.tenure);
        this.withdrawal = (EditText) findViewById(R.id.withdrawalAmoount);
        this.totalInvestment = (TextView) findViewById(R.id.totalInvestment);
        this.totalInterest = (TextView) findViewById(R.id.totalInterest);
        this.maturityDate = (TextView) findViewById(R.id.Matuirtydate);
        this.maturity = (TextView) findViewById(R.id.MaturityValue);
        InterstitialAd interstitialAd = new InterstitialAd(this);
        this.ad = interstitialAd;
        interstitialAd.setAdUnitId(this.interAds);
        this.ad.loadAd(new AdRequest.Builder().build());
        SQLiteDatabase openOrCreateDatabase = openOrCreateDatabase("EMI", 0, (SQLiteDatabase.CursorFactory) null);
        this.myDatabase = openOrCreateDatabase;
        openOrCreateDatabase.execSQL("CREATE TABLE IF NOT EXISTS swpTable(name TEXT,principalAmount DOUBLE,interest DOUBLE,tenure DOUBLE,date TEXT,id INTEGER PRIMARY KEY,withdrawal DOUBLE)");
        this.day = (Button) findViewById(R.id.date);
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.swpdetail);
        this.bmp = decodeResource;
        this.scalebmp = Bitmap.createScaledBitmap(decodeResource, 1200, 2010, false);
        ((LinearLayout) findViewById(R.id.mainlinear)).startAnimation(AnimationUtils.loadAnimation(this, R.anim.slideup));
        this.frameLayout = (FrameLayout) findViewById(R.id.blur);
        this.group = (RadioGroup) findViewById(R.id.togle);
        Calendar c = Calendar.getInstance();
        this.year = c.get(1);
        this.month = c.get(2) + 1;
        this.date = c.get(5);
        switch (this.month) {
            case 1:
                this.tmonth = "Jan";
                break;
            case 2:
                this.tmonth = "Feb";
                break;
            case 3:
                this.tmonth = "Mar";
                break;
            case 4:
                this.tmonth = "April";
                break;
            case 5:
                this.tmonth = "May";
                break;
            case 6:
                this.tmonth = "Jun";
                break;
            case 7:
                this.tmonth = "July";
                break;
            case 8:
                this.tmonth = "Aug";
                break;
            case 9:
                this.tmonth = "Sep";
                break;
            case 10:
                this.tmonth = "Oct";
                break;
            case 11:
                this.tmonth = "Nov";
                break;
            case 12:
                this.tmonth = "Dec";
                break;
        }
        Button button = this.day;
        button.setText("First EMI: " + this.date + " " + this.tmonth + " " + this.year);
        this.setListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Swp.this.date = i2;
                Swp.this.month = i1 + 1;
                Swp.this.year = i;
                switch (Swp.this.month) {
                    case 1:
                        Swp.this.tmonth = "Jan";
                        break;
                    case 2:
                        Swp.this.tmonth = "Feb";
                        break;
                    case 3:
                        Swp.this.tmonth = "Mar";
                        break;
                    case 4:
                        Swp.this.tmonth = "April";
                        break;
                    case 5:
                        Swp.this.tmonth = "May";
                        break;
                    case 6:
                        Swp.this.tmonth = "Jun";
                        break;
                    case 7:
                        Swp.this.tmonth = "July";
                        break;
                    case 8:
                        Swp.this.tmonth = "Aug";
                        break;
                    case 9:
                        Swp.this.tmonth = "Sep";
                        break;
                    case 10:
                        Swp.this.tmonth = "Oct";
                        break;
                    case 11:
                        Swp.this.tmonth = "Nov";
                        break;
                    case 12:
                        Swp.this.tmonth = "Dec";
                        break;
                }
                Button button = Swp.this.day;
                button.setText("First EMI: " + Swp.this.date + " " + Swp.this.tmonth + " " + Swp.this.year);
            }
        };
        String ext = getIntent().getStringExtra("Open");
        if (ext != null) {
            if (this.ad.isLoaded()) {
                this.ad.show();
            }
            loadInterstitialAd();
            openingSaved(Integer.parseInt(ext));
        }
        this.day.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Swp swp = Swp.this;
                DatePickerDialog datePickerDialog = new DatePickerDialog(swp, 16973936, swp.setListener, Swp.this.year, Swp.this.month - 1, Swp.this.date);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                datePickerDialog.show();
            }
        });
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scroller);
        if (Build.VERSION.SDK_INT >= 23) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY < 800) {
                        Swp.this.madview.animate().translationY(0.0f);
                    } else {
                        Swp.this.madview.animate().translationY(100.0f);
                    }
                }
            });
        }
    }

    public void onMessage() {
        this.frameLayout.setBackgroundColor(Color.parseColor("#00FFFFFF"));
    }

    public void onBackPressed() {
        Statement statement = (Statement) getSupportFragmentManager().findFragmentByTag("TAG_FRAGMENT");
        this.frameLayout.setBackgroundColor(Color.parseColor("#00FFFFFF"));
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
                    Swp.this.finish();
                }
            });
        } else {
            super.onBackPressed();
        }
    }

    public void loadInterstitialAd() {
        this.ad.loadAd(new AdRequest.Builder().build());
    }

    public void openingSaved(int position) {
        char c;
        char c2;
        Cursor cursor = this.myDatabase.rawQuery("SELECT * FROM swpTable", null);
        List<Double> principle = new ArrayList<>();
        List<Integer> id = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<Double> interest = new ArrayList<>();
        List<Double> time = new ArrayList<>();
        List<String> dateHistory = new ArrayList<>();
        List<Double> withdraw = new ArrayList<>();
        int withIndex = cursor.getColumnIndex("withdrawal");
        int idIndex = cursor.getColumnIndex("id");
        int nameIndex = cursor.getColumnIndex(AppMeasurementSdk.ConditionalUserProperty.NAME);
        int principalIndex = cursor.getColumnIndex("principalAmount");
        int interetIndex = cursor.getColumnIndex("interest");
        int tenureIndex = cursor.getColumnIndex("tenure");
        int dateIndex = cursor.getColumnIndex("date");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            withdraw.add(Double.valueOf(cursor.getDouble(withIndex)));
            name.add(cursor.getString(nameIndex));
            id.add(Integer.valueOf(cursor.getInt(idIndex)));
            principle.add(Double.valueOf(cursor.getDouble(principalIndex)));
            interest.add(Double.valueOf(cursor.getDouble(interetIndex)));
            time.add(Double.valueOf(cursor.getDouble(tenureIndex)));
            dateHistory.add(cursor.getString(dateIndex));
            cursor.moveToNext();
            withIndex = withIndex;
        }
        this.withdrawal.setText(String.valueOf(withdraw.get(position)));
        this.investment.setText(String.valueOf(principle.get(position)));
        this.Time.setText(String.valueOf(time.get(position)));
        this.rate.setText(String.valueOf(interest.get(position)));
        this.day.setText(String.valueOf(dateHistory.get(position)));
        String[] dateregex = dateHistory.get(position).split(" ");
        this.date = Integer.parseInt(dateregex[0]);
        String str = dateregex[1];
        this.tmonth = str;
        switch (str.hashCode()) {
            case 66195:
                if (str.equals("Aug")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 68578:
                if (str.equals("Dec")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 70499:
                if (str.equals("Feb")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 74231:
                if (str.equals("Jan")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 74851:
                if (str.equals("Jun")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 77118:
                if (str.equals("Mar")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 77125:
                if (str.equals("May")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 78517:
                if (str.equals("Nov")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 79104:
                if (str.equals("Oct")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 83006:
                if (str.equals("Sep")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 2320440:
                if (str.equals("July")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 63478374:
                if (str.equals("April")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                c2 = 2;
                this.month = 1;
                break;
            case 1:
                c2 = 2;
                this.month = 2;
                break;
            case 2:
                this.month = 3;
                c2 = 2;
                break;
            case 3:
                this.month = 4;
                c2 = 2;
                break;
            case 4:
                this.month = 5;
                c2 = 2;
                break;
            case 5:
                this.month = 6;
                c2 = 2;
                break;
            case 6:
                this.month = 7;
                c2 = 2;
                break;
            case 7:
                this.month = 8;
                c2 = 2;
                break;
            case '\b':
                this.month = 9;
                c2 = 2;
                break;
            case '\t':
                this.month = 10;
                c2 = 2;
                break;
            case '\n':
                this.month = 11;
                c2 = 2;
                break;
            case 11:
                this.month = 12;
                c2 = 2;
                break;
            default:
                c2 = 2;
                break;
        }
        this.year = Integer.parseInt(dateregex[c2]);
        this.Tenure = Double.valueOf(this.Time.getText().toString()).doubleValue() * 12.0d;
        calculation();
    }


    public void history(View view) {
        historyFunction();
    }

    public void historyFunction() {
        Cursor cursor = this.myDatabase.rawQuery("SELECT * FROM swpTable", (String[]) null);
        List<Double> principle = new ArrayList<>();
        List<Integer> id = new ArrayList<>();
        List<String> name2 = new ArrayList<>();
        List<Double> interest = new ArrayList<>();
        List<Double> time = new ArrayList<>();
        List<String> dateHistory = new ArrayList<>();
        ArrayList arrayList = new ArrayList();
        int withIndex = cursor.getColumnIndex("withdrawal");
        int idIndex = cursor.getColumnIndex("id");
        int nameIndex = cursor.getColumnIndex(AppMeasurementSdk.ConditionalUserProperty.NAME);
        int principalIndex = cursor.getColumnIndex("principalAmount");
        int interetIndex = cursor.getColumnIndex("interest");
        int tenureIndex = cursor.getColumnIndex("tenure");
        int dateIndex = cursor.getColumnIndex("date");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(Double.valueOf(cursor.getDouble(withIndex)));
            name2.add(cursor.getString(nameIndex));
            id.add(Integer.valueOf(cursor.getInt(idIndex)));
            principle.add(Double.valueOf(cursor.getDouble(principalIndex)));
            interest.add(Double.valueOf(cursor.getDouble(interetIndex)));
            time.add(Double.valueOf(cursor.getDouble(tenureIndex)));
            dateHistory.add(cursor.getString(dateIndex));
            cursor.moveToNext();
            withIndex = withIndex;
        }
        Dialog history = new Dialog(this);
        int i = dateIndex;
        history.setContentView(R.layout.history);
        history.setCancelable(true);
        int i2 = tenureIndex;
        int i3 = interetIndex;
        history.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        history.setCanceledOnTouchOutside(true);
        history.getWindow().setLayout(-2, -1);
        history.getWindow().getAttributes().windowAnimations = 16973826;
        history.show();
        int i4 = principalIndex;
        int i5 = nameIndex;
        int i6 = idIndex;
        Cursor cursor2 = cursor;
        final Dialog history2 = history;
        ArrayList arrayList2 = arrayList;
        ((ListView) history2.findViewById(R.id.historyList)).setAdapter(new MyListAdapter(this, name2, principle, dateHistory, id, time, arrayList));
        ((ImageButton) history2.findViewById(R.id.canceling)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                history2.dismiss();
            }
        });
    }

    public void save(View view) {
        double periodSql;
        if (this.investment.getText().toString().isEmpty() || this.rate.getText().toString().isEmpty() || this.Time.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Inputs", 0).show();
            return;
        }
        this.Tenure = Double.parseDouble(this.Time.getText().toString());
        int selectedid = this.group.getCheckedRadioButtonId();
        RadioButton radioButton2 = (RadioButton) findViewById(selectedid);
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.Tenure = Double.parseDouble(this.Time.getText().toString()) * 12.0d;
        } else {
            this.Tenure = Double.parseDouble(this.Time.getText().toString());
        }
        if (this.Tenure > 360.0d || Double.parseDouble(this.rate.getText().toString()) > 50.0d) {
            if (this.Tenure > 360.0d) {
                Toast.makeText(this, "tenure should be less than 30 years", 0).show();
            } else {
                Toast.makeText(this, "Interest rate should be less than 50%", 0).show();
            }
        } else {
            calculation();
            double loanSql = Double.parseDouble(this.investment.getText().toString());
            double interestSql = Double.parseDouble(this.rate.getText().toString());
            this.withdrawalAmount = Double.parseDouble(this.withdrawal.getText().toString());
            String sqlDate = this.date + " " + this.month + " " + this.year;
            if (this.radioButton.getText().toString().equals("year")) {
                periodSql = Double.parseDouble(this.Time.getText().toString());
            } else {
                periodSql = Double.parseDouble(this.Time.getText().toString()) / 12.0d;
            }
            final Dialog entry = new Dialog(this);
            entry.setContentView(R.layout.file_name);
            entry.setCancelable(true);
            entry.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            entry.setCanceledOnTouchOutside(true);
            entry.getWindow().setLayout(-2, -2);
            entry.getWindow().getAttributes().windowAnimations = 16973826;
            entry.show();
            ImageButton cancel = (ImageButton) entry.findViewById(R.id.canceling);
            cancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    entry.dismiss();
                }
            });
            final EditText editText = (EditText) entry.findViewById(R.id.naming);
            final double d = loanSql;
            final double d2 = interestSql;
            ImageButton imageButton = cancel;
            int i = selectedid;
//            AnonymousClass11 r12 = r0;
            final double d3 = periodSql;
            double d4 = loanSql;
            Button save = (Button) entry.findViewById(R.id.save);
            final String str = sqlDate;
            Dialog dialog = entry;
            View.OnClickListener r0 = new View.OnClickListener() {
                public void onClick(View v) {
                    Swp.this.name = editText.getText().toString();
                    if (Swp.this.name.isEmpty()) {
                        Swp.this.name = "EMPTY";
                    }
                    SQLiteDatabase sQLiteDatabase = Swp.this.myDatabase;
                    sQLiteDatabase.execSQL("INSERT INTO swpTable(name,principalAmount,interest,tenure,date,withdrawal) VALUES ('" + Swp.this.name + "'," + d + "," + d2 + "," + d3 + ",'" + str + "'," + Swp.this.withdrawalAmount + ")");
                    entry.dismiss();
                    if (Swp.this.ad.isLoaded()) {
                        Swp.this.ad.show();
                    }
                    Swp.this.ad.loadAd(new AdRequest.Builder().build());
                }
            };
            save.setOnClickListener(r0);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        historyFunction();
        return super.onOptionsItemSelected(item);
    }
}
