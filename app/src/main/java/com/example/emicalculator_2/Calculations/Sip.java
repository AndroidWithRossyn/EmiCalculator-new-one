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

public class Sip extends AppCompatActivity implements Statementsip.onMessageReadListner {
    double Tenure;
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
    EditText investAmount;
    double investmentAmount;
    boolean isCalculated;
    String mMonth;
    AdView madview;
    ReviewManager manger;
    TextView maturityDate;
    double maturityVal;
    TextView maturityValue;
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
    EditText time;
    String tmonth;
    double totalInt;
    TextView totalInterest;
    double totalInvest;
    TextView totalInvestment;
    int year;

    public void calculate(View view) {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (this.investAmount.getText().toString().isEmpty() || this.rate.getText().toString().isEmpty() || this.time.getText().toString().isEmpty()) {
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
        if (this.Tenure <= 360.0d && Double.parseDouble(this.rate.getText().toString()) <= 50.0d) {
            calculation();
        } else if (this.Tenure > 360.0d) {
            Toast.makeText(this, "Tenure should be less than 30 years", 0).show();
        } else {
            Toast.makeText(this, "Interest rate should be less than 50%", 0).show();
        }
    }

    public void share(View view) {
        if (this.investAmount.getText().toString().isEmpty() || this.rate.getText().toString().isEmpty() || this.time.getText().toString().isEmpty()) {
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
        if (this.Tenure <= 360.0d && Double.parseDouble(this.rate.getText().toString()) <= 50.0d) {
            calculation();
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", "EMI- A Calculator app");
            intent.putExtra("android.intent.extra.TEXT", "SIP Details-\n\nInvestment Amount : " + this.investmentAmount + "\nTenure : " + this.Tenure + "months\nFirst SIP: " + this.date + " " + this.tmonth + " " + this.year + "\n\nTotal Investment Amount: " + this.totalInvest + "\nTotal Interest: " + this.totalInt + "\nMaturity Value: " + this.maturityVal + "\nMaturity Date: " + this.date + " " + this.mMonth + " " + this.myear + "\n\nCalculate by EMI\n" + this.appLink);
            startActivity(Intent.createChooser(intent, "Share Using"));
        } else if (this.Tenure > 360.0d) {
            Toast.makeText(this, "tenure should be less than 30 years", 0).show();
        } else {
            Toast.makeText(this, "Interest rate should be less than 50%", 0).show();
        }
    }

    public void pdf(View view) {
        if (this.investAmount.getText().toString().isEmpty() || this.rate.getText().toString().isEmpty() || this.time.getText().toString().isEmpty()) {
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
            canvas.drawText(this.investmentAmount + " " + Constants.CURRENCY_STORED, 900.0f, 575.0f, myPaint);
            StringBuilder sb = new StringBuilder();
            sb.append(this.expectedRate * 1200.0d);
            sb.append(" %");
            canvas.drawText(sb.toString(), 900.0f, 700.0f, myPaint);
            canvas.drawText(this.Tenure + "  " + this.radioButton.getText().toString(), 900.0f, 850.0f, myPaint);
            canvas.drawText(this.totalInvest + " " + Constants.CURRENCY_STORED, 300.0f, 1150.0f, myPaint);
            StringBuilder sb2 = new StringBuilder();
            int i = selectedid;
            sb2.append(this.totalInt);
            sb2.append(" ");
            sb2.append(Constants.CURRENCY_STORED);
            canvas.drawText(sb2.toString(), 900.0f, 1150.0f, myPaint);
            canvas.drawText(this.maturityVal + " " + Constants.CURRENCY_STORED, 600.0f, 1450.0f, myPaint);
            canvas.drawText(this.date + " " + this.mMonth + " " + this.myear, 600.0f, 1660.0f, myPaint);
            canvas.drawText(this.date + " " + this.tmonth + " " + this.year, 900.0f, 480.0f, myPaint);
            myPdf.finishPage(myPage);
            Calendar calendar = Calendar.getInstance();
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(externalStoragePublicDirectory, "/Sip" + calendar.get(5) + ".pdf");
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
        this.investmentAmount = Double.parseDouble(this.investAmount.getText().toString());
        this.Tenure = Double.parseDouble(this.time.getText().toString());
        RadioButton radioButton2 = (RadioButton) findViewById(this.group.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.Tenure = Double.parseDouble(this.time.getText().toString()) * 12.0d;
        } else {
            this.Tenure = Double.parseDouble(this.time.getText().toString());
        }
        double parseDouble = Double.parseDouble(this.rate.getText().toString());
        this.expectedRate = parseDouble;
        double d = parseDouble / 1200.0d;
        this.expectedRate = d;
        double pow = this.investmentAmount * (Math.pow(d + 1.0d, this.Tenure) - 1.0d);
        this.maturityVal = pow;
        double d2 = this.expectedRate;
        double d3 = (pow * (1.0d + d2)) / d2;
        this.maturityVal = d3;
        double d4 = this.investmentAmount * this.Tenure;
        this.totalInvest = d4;
        double d5 = d3 - d4;
        this.totalInt = d5;
        if (((double) ((int) d5)) == d5) {
            this.totalInterest.setText(String.valueOf((int) d5));
        } else {
            this.totalInt = new BigDecimal(this.totalInt).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView = this.totalInterest;
            textView.setText(this.totalInt + " " + Constants.CURRENCY_STORED);
        }
        PrasingTheDouble prasingTheDouble = new PrasingTheDouble(this.totalInt);
        TextView textView2 = this.totalInterest;
        textView2.setText(prasingTheDouble.getaDouble() + " " + Constants.CURRENCY_STORED);
        double d6 = this.totalInvest;
        if (((double) ((int) d6)) == d6) {
            TextView textView3 = this.totalInvestment;
            textView3.setText(((int) d6) + " " + Constants.CURRENCY_STORED);
        } else {
            this.totalInvest = new BigDecimal(this.totalInvest).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView4 = this.totalInvestment;
            textView4.setText(this.totalInvest + " " + Constants.CURRENCY_STORED);
        }
        PrasingTheDouble prasingTheDouble2 = new PrasingTheDouble(this.totalInvest);
        TextView textView5 = this.totalInvestment;
        textView5.setText(prasingTheDouble2.getaDouble() + " " + Constants.CURRENCY_STORED);
        double d7 = this.maturityVal;
        if (((double) ((int) d7)) == d7) {
            this.maturityValue.setText(String.valueOf((int) d7));
        } else {
            this.maturityVal = new BigDecimal(this.maturityVal).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView6 = this.maturityValue;
            textView6.setText(this.maturityVal + " " + Constants.CURRENCY_STORED);
        }
        PrasingTheDouble prasingTheDouble3 = new PrasingTheDouble(this.maturityVal);
        TextView textView7 = this.maturityValue;
        textView7.setText(prasingTheDouble3.getaDouble() + " " + Constants.CURRENCY_STORED);
        double d8 = (double) this.month;
        double d9 = this.Tenure;
        Double.isNaN(d8);
        int i = (int) (d8 + d9);
        this.mmonth = i;
        int index = (i - 1) / 12;
        this.myear = this.year + index;
        int i2 = i - (index * 12);
        this.mmonth = i2;
        switch (i2) {
            case 1:
                this.mMonth = "Jan";
                break;
            case 2:
                this.mMonth = "Feb";
                break;
            case 3:
                this.mMonth = "Mar";
                break;
            case 4:
                this.mMonth = "April";
                break;
            case 5:
                this.mMonth = "May";
                break;
            case 6:
                this.mMonth = "Jun";
                break;
            case 7:
                this.mMonth = "July";
                break;
            case 8:
                this.mMonth = "Aug";
                break;
            case 9:
                this.mMonth = "Sep";
                break;
            case 10:
                this.mMonth = "Oct";
                break;
            case 11:
                this.mMonth = "Nov";
                break;
            case 12:
                this.mMonth = "Dec";
                break;
        }
        TextView textView8 = this.maturityDate;
        textView8.setText(this.date + " " + this.mMonth + " " + this.myear);
    }

    public void statistic(View view) {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (this.investAmount.getText().toString().isEmpty() || this.rate.getText().toString().isEmpty() || this.time.getText().toString().isEmpty()) {
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
        if (this.Tenure <= 360.0d && Double.parseDouble(this.rate.getText().toString()) <= 50.0d) {
            calculation();
            this.frameLayout.setBackgroundColor(Color.parseColor("#59000000"));
            Statementsip statementsip = new Statementsip(0.0d, this.Tenure, this.expectedRate, this.investmentAmount, this.maturityVal, this.totalInt, this.date, this.month, this.year, "SIP");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack((String) null);
            transaction.setCustomAnimations(R.anim.segmentup, R.anim.segmentdown);
            transaction.add((int) R.id.frame, (Fragment) statementsip, "Fragment").commit();
        } else if (this.Tenure > 360.0d) {
            Toast.makeText(this, "tenure should be less than 30 years", 0).show();
        } else {
            Toast.makeText(this, "Interest rate should be less than 50%", 0).show();
        }
    }

    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_sip);
        this.nativeAds = getString(R.string.nativeAds);
        this.premiumLink = getString(R.string.premiumAppLink);
        this.appLink = getString(R.string.appLink);
        this.interAds = getString(R.string.InterstialAds);
        this.manger = ReviewManagerFactory.create(this);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.manger.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            public void onComplete(Task<ReviewInfo> task) {
                if (task.isSuccessful()) {
                    Sip.this.reviewInfo = task.getResult();
                    return;
                }
                Toast.makeText(Sip.this, "Error", 0).show();
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
                UnifiedNativeAdView adview = (UnifiedNativeAdView) Sip.this.getLayoutInflater().inflate(R.layout.nativeads, (ViewGroup) null);
                LinearLayout container = (LinearLayout) Sip.this.findViewById(R.id.nativead);
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
        SQLiteDatabase openOrCreateDatabase = openOrCreateDatabase("EMI", 0, (SQLiteDatabase.CursorFactory) null);
        this.myDatabase = openOrCreateDatabase;
        openOrCreateDatabase.execSQL("CREATE TABLE IF NOT EXISTS sipTable(name TEXT,principalAmount DOUBLE,interest DOUBLE,tenure DOUBLE,date TEXT,id INTEGER PRIMARY KEY)");
        this.investAmount = (EditText) findViewById(R.id.InvestmentAmoount);
        this.rate = (EditText) findViewById(R.id.interestAmount);
        this.time = (EditText) findViewById(R.id.tenure);
        this.day = (Button) findViewById(R.id.date);
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.sipdetails);
        this.bmp = decodeResource;
        this.scalebmp = Bitmap.createScaledBitmap(decodeResource, 1200, 2010, false);
        this.totalInterest = (TextView) findViewById(R.id.totalInterest);
        this.totalInvestment = (TextView) findViewById(R.id.totalInvestment);
        this.maturityValue = (TextView) findViewById(R.id.MaturityValue);
        this.maturityDate = (TextView) findViewById(R.id.Matuirtydate);
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
                Sip.this.date = i2;
                Sip.this.month = i1 + 1;
                Sip.this.year = i;
                switch (Sip.this.month) {
                    case 1:
                        Sip.this.tmonth = "Jan";
                        break;
                    case 2:
                        Sip.this.tmonth = "Feb";
                        break;
                    case 3:
                        Sip.this.tmonth = "Mar";
                        break;
                    case 4:
                        Sip.this.tmonth = "April";
                        break;
                    case 5:
                        Sip.this.tmonth = "May";
                        break;
                    case 6:
                        Sip.this.tmonth = "Jun";
                        break;
                    case 7:
                        Sip.this.tmonth = "July";
                        break;
                    case 8:
                        Sip.this.tmonth = "Aug";
                        break;
                    case 9:
                        Sip.this.tmonth = "Sep";
                        break;
                    case 10:
                        Sip.this.tmonth = "Oct";
                        break;
                    case 11:
                        Sip.this.tmonth = "Nov";
                        break;
                    case 12:
                        Sip.this.tmonth = "Dec";
                        break;
                }
                Button button = Sip.this.day;
                button.setText("First EMI: " + Sip.this.date + " " + Sip.this.tmonth + " " + Sip.this.year);
            }
        };
        String ext = getIntent().getStringExtra("Open");
        if (ext != null) {
            if (this.ad.isLoaded()) {
                this.ad.show();
            }
            this.ad.loadAd(new AdRequest.Builder().build());
            openingSaved(Integer.parseInt(ext));
        }
        this.day.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Sip sip = Sip.this;
                DatePickerDialog datePickerDialog = new DatePickerDialog(sip, 16973936, sip.setListener, Sip.this.year, Sip.this.month - 1, Sip.this.date);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                datePickerDialog.show();
            }
        });
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scroller);
        if (Build.VERSION.SDK_INT >= 23) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY < 800) {
                        Sip.this.madview.animate().translationY(0.0f);
                    } else {
                        Sip.this.madview.animate().translationY(100.0f);
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
                    Sip.this.finish();
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
        Cursor cursor = this.myDatabase.rawQuery("SELECT * FROM sipTable", null);
        List<Double> principle = new ArrayList<>();
        List<Integer> id = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<Double> interest = new ArrayList<>();
        List<Double> time = new ArrayList<>();
        List<String> dateHistory = new ArrayList<>();
        int idIndex = cursor.getColumnIndex("id");
        int nameIndex = cursor.getColumnIndex(AppMeasurementSdk.ConditionalUserProperty.NAME);
        int principalIndex = cursor.getColumnIndex("principalAmount");
        int interetIndex = cursor.getColumnIndex("interest");
        int tenureIndex = cursor.getColumnIndex("tenure");
        int dateIndex = cursor.getColumnIndex("date");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            name.add(cursor.getString(nameIndex));
            id.add(Integer.valueOf(cursor.getInt(idIndex)));
            principle.add(Double.valueOf(cursor.getDouble(principalIndex)));
            interest.add(Double.valueOf(cursor.getDouble(interetIndex)));
            time.add(Double.valueOf(cursor.getDouble(tenureIndex)));
            dateHistory.add(cursor.getString(dateIndex));
            cursor.moveToNext();
        }
        this.investAmount.setText(String.valueOf(principle.get(position)));
        this.time.setText(String.valueOf(time.get(position)));
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
        this.Tenure = Double.valueOf(this.time.getText().toString()).doubleValue() * 12.0d;
        calculation();
    }


    public void history(View view) {
        historyFunction();
    }

    public void historyFunction() {
        Cursor cursor = this.myDatabase.rawQuery("SELECT * FROM sipTable", (String[]) null);
        List<Double> principle = new ArrayList<>();
        List<Integer> id = new ArrayList<>();
        List<String> name2 = new ArrayList<>();
        List<Double> interest = new ArrayList<>();
        List<Double> time2 = new ArrayList<>();
        List<String> dateHistory = new ArrayList<>();
        int idIndex = cursor.getColumnIndex("id");
        int nameIndex = cursor.getColumnIndex(AppMeasurementSdk.ConditionalUserProperty.NAME);
        int principalIndex = cursor.getColumnIndex("principalAmount");
        int interetIndex = cursor.getColumnIndex("interest");
        int tenureIndex = cursor.getColumnIndex("tenure");
        int dateIndex = cursor.getColumnIndex("date");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            name2.add(cursor.getString(nameIndex));
            id.add(Integer.valueOf(cursor.getInt(idIndex)));
            principle.add(Double.valueOf(cursor.getDouble(principalIndex)));
            interest.add(Double.valueOf(cursor.getDouble(interetIndex)));
            time2.add(Double.valueOf(cursor.getDouble(tenureIndex)));
            dateHistory.add(cursor.getString(dateIndex));
            cursor.moveToNext();
        }
        Dialog history = new Dialog(this);
        history.setContentView(R.layout.history);
        history.setCancelable(true);
        int i = dateIndex;
        int i2 = tenureIndex;
        history.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        history.setCanceledOnTouchOutside(true);
        history.getWindow().setLayout(-2, -1);
        history.getWindow().getAttributes().windowAnimations = 16973826;
        history.show();
        int i3 = interetIndex;
        int i4 = principalIndex;
        int i5 = nameIndex;
        int i6 = idIndex;
        final Dialog history2 = history;
        ((ListView) history2.findViewById(R.id.historyList)).setAdapter(new MyListAdapter(this, name2, principle, dateHistory, id, time2, (List<Double>) null));
        ((ImageButton) history2.findViewById(R.id.canceling)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                history2.dismiss();
            }
        });
    }

    public void save(View view) {
        double periodSql;
        if (this.investAmount.getText().toString().isEmpty() || this.rate.getText().toString().isEmpty() || this.time.getText().toString().isEmpty()) {
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
        if (this.Tenure > 360.0d || Double.parseDouble(this.rate.getText().toString()) > 50.0d) {
            if (this.Tenure > 360.0d) {
                Toast.makeText(this, "tenure should be less than 30 years", 0).show();
            } else {
                Toast.makeText(this, "Interest rate should be less than 50%", 0).show();
            }
        } else {
            calculation();
            double loanSql = Double.parseDouble(this.investAmount.getText().toString());
            double interestSql = Double.parseDouble(this.rate.getText().toString());
            String sqlDate = this.date + " " + this.month + " " + this.year;
            if (this.radioButton.getText().toString().equals("year")) {
                periodSql = Double.parseDouble(this.time.getText().toString());
            } else {
                periodSql = Double.valueOf(this.time.getText().toString()).doubleValue() / 12.0d;
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
                    Sip.this.name = editText.getText().toString();
                    if (Sip.this.name.isEmpty()) {
                        Sip.this.name = "EMPTY";
                    }
                    SQLiteDatabase sQLiteDatabase = Sip.this.myDatabase;
                    sQLiteDatabase.execSQL("INSERT INTO sipTable(name,principalAmount,interest,tenure,date) VALUES ('" + Sip.this.name + "'," + d + "," + d2 + "," + d3 + ",'" + str + "')");
                    entry.dismiss();
                    if (Sip.this.ad.isLoaded()) {
                        Sip.this.ad.show();
                    }
                    Sip.this.loadInterstitialAd();
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
