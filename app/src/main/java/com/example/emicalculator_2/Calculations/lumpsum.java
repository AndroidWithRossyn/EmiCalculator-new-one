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

public class lumpsum extends AppCompatActivity implements Statementsip.onMessageReadListner {
    InterstitialAd ad;
    TextView ansInvestment;
    String appLink;
    Bitmap bmp;
    int date;
    Button day;
    double expectedRate;
    FrameLayout frameLayout;
    SharedPreferences frontshare;
    RadioGroup group;
    String interAds;
    TextView interestText;
    double investmentAmount;
    EditText investmentEdit;
    boolean isCalculated;
    int mMonth;
    int mYear;
    AdView madview;
    ReviewManager manger;
    TextView maturityDate;
    double maturityValue;
    int month;
    String msMonth;
    SQLiteDatabase myDatabase;
    String name;
    String nativeAds;
    String premiumLink;
    RadioButton radioButton;
    EditText rateEdit;
    ReviewInfo reviewInfo;
    String sMonth;
    Bitmap scalebmp;
    DatePickerDialog.OnDateSetListener setListener;
    double tenure;
    EditText timeEdit;
    double totalInterest;
    TextView valueMaturity;
    int year;

    public void calculate(View view) {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (this.investmentEdit.getText().toString().isEmpty() || this.rateEdit.getText().toString().isEmpty() || this.timeEdit.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Inputs", 0).show();
            return;
        }
        this.tenure = Double.parseDouble(this.timeEdit.getText().toString());
        RadioButton radioButton2 = (RadioButton) findViewById(this.group.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.tenure = Double.parseDouble(this.timeEdit.getText().toString()) * 12.0d;
        } else {
            this.tenure = Double.parseDouble(this.timeEdit.getText().toString());
        }
        if (this.tenure <= 360.0d && Double.parseDouble(this.rateEdit.getText().toString()) <= 50.0d) {
            calculation();
        } else if (this.tenure > 360.0d) {
            Toast.makeText(this, "tenure should be less than 30 years", 0).show();
        } else {
            Toast.makeText(this, "interestText rate should be less than 50%", 0).show();
        }
    }

    public void share(View view) {
        if (this.investmentEdit.getText().toString().isEmpty() || this.rateEdit.getText().toString().isEmpty() || this.timeEdit.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Inputs", 0).show();
            return;
        }
        this.tenure = Double.parseDouble(this.timeEdit.getText().toString());
        RadioButton radioButton2 = (RadioButton) findViewById(this.group.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.tenure = Double.parseDouble(this.timeEdit.getText().toString()) * 12.0d;
        } else {
            this.tenure = Double.parseDouble(this.timeEdit.getText().toString());
        }
        if (this.tenure <= 360.0d && Double.parseDouble(this.rateEdit.getText().toString()) <= 50.0d) {
            calculation();
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", "EMI- A Calculator app");
            intent.putExtra("android.intent.extra.TEXT", "Lumpsum Details-\n\ninvestmentEdit Amount : " + this.investmentAmount + "\ntenure : " + this.tenure + "months\nFirst SIP: " + this.date + " " + this.mMonth + " " + this.year + "\n\nTotal investmentEdit Amount: " + this.investmentAmount + "\nTotal interestText: " + this.totalInterest + "\nMaturity Value: " + this.maturityValue + "\nMaturity Date: " + this.date + " " + this.msMonth + " " + this.mYear + "\n\nCalculate by EMI\n" + this.appLink);
            startActivity(Intent.createChooser(intent, "Share Using"));
        } else if (this.tenure > 360.0d) {
            Toast.makeText(this, "tenure should be less than 30 years", 0).show();
        } else {
            Toast.makeText(this, "interestText rate should be less than 50%", 0).show();
        }
    }

    public void pdf(View view) {
        if (this.investmentEdit.getText().toString().isEmpty() || this.rateEdit.getText().toString().isEmpty() || this.timeEdit.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Inputs", 0).show();
            return;
        }
        this.tenure = Double.parseDouble(this.timeEdit.getText().toString());
        int selectedid = this.group.getCheckedRadioButtonId();
        RadioButton radioButton2 = (RadioButton) findViewById(selectedid);
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.tenure = Double.parseDouble(this.timeEdit.getText().toString()) * 12.0d;
        } else {
            this.tenure = Double.parseDouble(this.timeEdit.getText().toString());
        }
        if (this.tenure > 360.0d || Double.parseDouble(this.rateEdit.getText().toString()) > 50.0d) {
            if (this.tenure > 360.0d) {
                Toast.makeText(this, "tenure should be less than 30 years", 0).show();
            } else {
                Toast.makeText(this, "interestText rate should be less than 50%", 0).show();
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
            canvas.drawText(this.tenure + "  " + this.radioButton.getText().toString(), 900.0f, 850.0f, myPaint);
            canvas.drawText(this.investmentAmount + " " + Constants.CURRENCY_STORED, 300.0f, 1150.0f, myPaint);
            StringBuilder sb2 = new StringBuilder();
            int i = selectedid;
            sb2.append(this.totalInterest);
            sb2.append(" ");
            sb2.append(Constants.CURRENCY_STORED);
            canvas.drawText(sb2.toString(), 900.0f, 1150.0f, myPaint);
            canvas.drawText(this.maturityValue + " " + Constants.CURRENCY_STORED, 600.0f, 1450.0f, myPaint);
            canvas.drawText(this.date + " " + this.msMonth + " " + this.mYear, 600.0f, 1660.0f, myPaint);
            canvas.drawText(this.date + " " + this.mMonth + " " + this.year, 900.0f, 480.0f, myPaint);
            myPdf.finishPage(myPage);
            Calendar calendar = Calendar.getInstance();
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(externalStoragePublicDirectory, "/Lumpsum" + calendar.get(5) + ".pdf");
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
        this.investmentAmount = Double.parseDouble(this.investmentEdit.getText().toString());
        this.tenure = Double.parseDouble(this.timeEdit.getText().toString());
        RadioButton radioButton2 = (RadioButton) findViewById(this.group.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.tenure = Double.parseDouble(this.timeEdit.getText().toString()) * 12.0d;
        } else {
            this.tenure = Double.parseDouble(this.timeEdit.getText().toString());
        }
        double parseDouble = Double.parseDouble(this.rateEdit.getText().toString());
        this.expectedRate = parseDouble;
        double d = parseDouble / 1200.0d;
        this.expectedRate = d;
        double pow = this.investmentAmount * Math.pow(d + 1.0d, this.tenure);
        this.maturityValue = pow;
        double d2 = pow - this.investmentAmount;
        this.totalInterest = d2;
        if (((double) ((int) d2)) == d2) {
            this.interestText.setText(String.valueOf((int) d2));
        } else {
            this.totalInterest = new BigDecimal(this.totalInterest).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView = this.interestText;
            textView.setText(this.totalInterest + " " + Constants.CURRENCY_STORED);
        }
        PrasingTheDouble prasingTheDouble = new PrasingTheDouble(this.totalInterest);
        TextView textView2 = this.interestText;
        textView2.setText(prasingTheDouble.getaDouble() + " " + Constants.CURRENCY_STORED);
        double d3 = this.investmentAmount;
        if (((double) ((int) d3)) == d3) {
            TextView textView3 = this.ansInvestment;
            textView3.setText(((int) d3) + " " + Constants.CURRENCY_STORED);
        } else {
            this.investmentAmount = new BigDecimal(this.investmentAmount).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView4 = this.ansInvestment;
            textView4.setText(this.investmentAmount + " " + Constants.CURRENCY_STORED);
        }
        PrasingTheDouble prasingTheDouble2 = new PrasingTheDouble(this.investmentAmount);
        TextView textView5 = this.ansInvestment;
        textView5.setText(prasingTheDouble2.getaDouble() + " " + Constants.CURRENCY_STORED);
        double d4 = this.maturityValue;
        if (((double) ((int) d4)) == d4) {
            this.valueMaturity.setText(String.valueOf((int) d4));
        } else {
            this.maturityValue = new BigDecimal(this.maturityValue).setScale(1, RoundingMode.HALF_UP).doubleValue();
            TextView textView6 = this.valueMaturity;
            textView6.setText(this.maturityValue + " " + Constants.CURRENCY_STORED);
        }
        PrasingTheDouble prasingTheDouble3 = new PrasingTheDouble(this.maturityValue);
        TextView textView7 = this.valueMaturity;
        textView7.setText(prasingTheDouble3.getaDouble() + " " + Constants.CURRENCY_STORED);
        double d5 = (double) this.month;
        double d6 = this.tenure;
        Double.isNaN(d5);
        int i = (int) (d5 + d6);
        this.mMonth = i;
        int index = (i - 1) / 12;
        this.mYear = this.year + index;
        int i2 = i - (index * 12);
        this.mMonth = i2;
        switch (i2) {
            case 1:
                this.msMonth = "Jan";
                break;
            case 2:
                this.msMonth = "Feb";
                break;
            case 3:
                this.msMonth = "Mar";
                break;
            case 4:
                this.msMonth = "April";
                break;
            case 5:
                this.msMonth = "May";
                break;
            case 6:
                this.msMonth = "Jun";
                break;
            case 7:
                this.msMonth = "July";
                break;
            case 8:
                this.msMonth = "Aug";
                break;
            case 9:
                this.msMonth = "Sep";
                break;
            case 10:
                this.msMonth = "Oct";
                break;
            case 11:
                this.msMonth = "Nov";
                break;
            case 12:
                this.msMonth = "Dec";
                break;
        }
        TextView textView8 = this.maturityDate;
        textView8.setText(this.date + " " + this.msMonth + " " + this.mYear);
    }

    public void statistic(View view) {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        if (this.investmentEdit.getText().toString().isEmpty() || this.rateEdit.getText().toString().isEmpty() || this.timeEdit.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Inputs", 0).show();
            return;
        }
        this.tenure = Double.parseDouble(this.timeEdit.getText().toString());
        RadioButton radioButton2 = (RadioButton) findViewById(this.group.getCheckedRadioButtonId());
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.tenure = Double.parseDouble(this.timeEdit.getText().toString()) * 12.0d;
        } else {
            this.tenure = Double.parseDouble(this.timeEdit.getText().toString());
        }
        if (this.tenure <= 360.0d && Double.parseDouble(this.rateEdit.getText().toString()) <= 50.0d) {
            calculation();
            this.frameLayout.setBackgroundColor(Color.parseColor("#59000000"));
            Statementsip statementsip = new Statementsip(0.0d, this.tenure, this.expectedRate, this.investmentAmount, this.maturityValue, this.totalInterest, this.date, this.month, this.year, "Lumpsum");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack((String) null);
            transaction.setCustomAnimations(R.anim.segmentup, R.anim.segmentdown);
            transaction.add((int) R.id.frame, (Fragment) statementsip, "Fragment").commit();
        } else if (this.tenure > 360.0d) {
            Toast.makeText(this, "tenure should be less than 30 years", 0).show();
        } else {
            Toast.makeText(this, "interestText rate should be less than 50%", 0).show();
        }
    }

    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_lumpsum);
        this.nativeAds = getString(R.string.nativeAds);
        this.premiumLink = getString(R.string.premiumAppLink);
        this.appLink = getString(R.string.appLink);
        this.interAds = getString(R.string.InterstialAds);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        this.madview = (AdView) findViewById(R.id.adView);
        this.madview.loadAd(new AdRequest.Builder().build());
        ReviewManager create = ReviewManagerFactory.create(this);
        this.manger = create;
        create.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            public void onComplete(Task<ReviewInfo> task) {
                if (task.isSuccessful()) {
                    lumpsum.this.reviewInfo = task.getResult();
                    return;
                }
                Toast.makeText(lumpsum.this, "Error", 0).show();
            }
        });
        this.frontshare = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, 0);
        final UnifiedNativeAd[] nativeAd = new UnifiedNativeAd[1];
        new AdLoader.Builder((Context) this, this.nativeAds).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                nativeAd[0] = unifiedNativeAd;
                UnifiedNativeAdView adview = (UnifiedNativeAdView) lumpsum.this.getLayoutInflater().inflate(R.layout.nativeads, (ViewGroup) null);
                LinearLayout container = (LinearLayout) lumpsum.this.findViewById(R.id.nativead);
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
        ((LinearLayout) findViewById(R.id.mainlinear)).startAnimation(AnimationUtils.loadAnimation(this, R.anim.slideup));
        SQLiteDatabase openOrCreateDatabase = openOrCreateDatabase("EMI", 0, (SQLiteDatabase.CursorFactory) null);
        this.myDatabase = openOrCreateDatabase;
        openOrCreateDatabase.execSQL("CREATE TABLE IF NOT EXISTS lumpsumTable(name TEXT,principalAmount DOUBLE,interest DOUBLE,tenure DOUBLE,date TEXT,id INTEGER PRIMARY KEY)");
        this.investmentEdit = (EditText) findViewById(R.id.InvestmentAmount);
        this.rateEdit = (EditText) findViewById(R.id.interestamoount);
        this.timeEdit = (EditText) findViewById(R.id.tenure);
        this.day = (Button) findViewById(R.id.date);
        this.ansInvestment = (TextView) findViewById(R.id.investment);
        this.interestText = (TextView) findViewById(R.id.totalinterest);
        this.valueMaturity = (TextView) findViewById(R.id.MaturityValue);
        this.maturityDate = (TextView) findViewById(R.id.finaldate);
        this.frameLayout = (FrameLayout) findViewById(R.id.blur);
        this.group = (RadioGroup) findViewById(R.id.togle);
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.lumpsumdetail);
        this.bmp = decodeResource;
        this.scalebmp = Bitmap.createScaledBitmap(decodeResource, 1200, 2010, false);
        Calendar c = Calendar.getInstance();
        this.year = c.get(1);
        this.month = c.get(2) + 1;
        this.date = c.get(5);
        switch (this.month) {
            case 1:
                this.sMonth = "Jan";
                break;
            case 2:
                this.sMonth = "Feb";
                break;
            case 3:
                this.sMonth = "Mar";
                break;
            case 4:
                this.sMonth = "April";
                break;
            case 5:
                this.sMonth = "May";
                break;
            case 6:
                this.sMonth = "Jun";
                break;
            case 7:
                this.sMonth = "July";
                break;
            case 8:
                this.sMonth = "Aug";
                break;
            case 9:
                this.sMonth = "Sep";
                break;
            case 10:
                this.sMonth = "Oct";
                break;
            case 11:
                this.sMonth = "Nov";
                break;
            case 12:
                this.sMonth = "Dec";
                break;
        }
        Button button = this.day;
        button.setText("First EMI: " + this.date + " " + this.sMonth + " " + this.year);
        this.setListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                lumpsum.this.date = i2;
                lumpsum.this.month = i1 + 1;
                lumpsum.this.year = i;
                switch (lumpsum.this.month) {
                    case 1:
                        lumpsum.this.sMonth = "Jan";
                        break;
                    case 2:
                        lumpsum.this.sMonth = "Feb";
                        break;
                    case 3:
                        lumpsum.this.sMonth = "Mar";
                        break;
                    case 4:
                        lumpsum.this.sMonth = "April";
                        break;
                    case 5:
                        lumpsum.this.sMonth = "May";
                        break;
                    case 6:
                        lumpsum.this.sMonth = "Jun";
                        break;
                    case 7:
                        lumpsum.this.sMonth = "July";
                        break;
                    case 8:
                        lumpsum.this.sMonth = "Aug";
                        break;
                    case 9:
                        lumpsum.this.sMonth = "Sep";
                        break;
                    case 10:
                        lumpsum.this.sMonth = "Oct";
                        break;
                    case 11:
                        lumpsum.this.sMonth = "Nov";
                        break;
                    case 12:
                        lumpsum.this.sMonth = "Dec";
                        break;
                }
                Button button = lumpsum.this.day;
                button.setText("First EMI: " + lumpsum.this.date + " " + lumpsum.this.sMonth + " " + lumpsum.this.year);
            }
        };
        String ext = getIntent().getStringExtra("Open");
        if (ext != null) {
            if (this.ad.isLoaded()) {
                this.ad.show();
            }
            loadInterstitialAd();
            this.ad.loadAd(new AdRequest.Builder().build());
            openingSaved(Integer.parseInt(ext));
        }
        this.day.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lumpsum lumpsum = lumpsum.this;
                DatePickerDialog datePickerDialog = new DatePickerDialog(lumpsum, 16973936, lumpsum.setListener, lumpsum.this.year, lumpsum.this.month - 1, lumpsum.this.date);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                datePickerDialog.show();
            }
        });
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scroller);
        if (Build.VERSION.SDK_INT >= 23) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY < 800) {
                        lumpsum.this.madview.animate().translationY(0.0f);
                    } else {
                        lumpsum.this.madview.animate().translationY(100.0f);
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
                    lumpsum.this.finish();
                }
            });
        } else {
            super.onBackPressed();
        }
    }

    public void loadInterstitialAd() {
        this.ad.loadAd(new AdRequest.Builder().build());
    }

    public void history(View view) {
        historyFunction();
    }

    public void historyFunction() {
        Cursor cursor = this.myDatabase.rawQuery("SELECT * FROM lumpsumTable", (String[]) null);
        List<Double> principle = new ArrayList<>();
        List<Integer> id = new ArrayList<>();
        List<String> name2 = new ArrayList<>();
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
            name2.add(cursor.getString(nameIndex));
            id.add(Integer.valueOf(cursor.getInt(idIndex)));
            principle.add(Double.valueOf(cursor.getDouble(principalIndex)));
            interest.add(Double.valueOf(cursor.getDouble(interetIndex)));
            time.add(Double.valueOf(cursor.getDouble(tenureIndex)));
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
        ((ListView) history2.findViewById(R.id.historyList)).setAdapter(new MyListAdapter(this, name2, principle, dateHistory, id, time, (List<Double>) null));
        ((ImageButton) history2.findViewById(R.id.canceling)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                history2.dismiss();
            }
        });
    }

    public void save(View view) {
        double periodSql;
        if (this.investmentEdit.getText().toString().isEmpty() || this.rateEdit.getText().toString().isEmpty() || this.timeEdit.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Inputs", 0).show();
            return;
        }
        this.tenure = Double.valueOf(this.timeEdit.getText().toString()).doubleValue();
        int selectedid = this.group.getCheckedRadioButtonId();
        RadioButton radioButton2 = (RadioButton) findViewById(selectedid);
        this.radioButton = radioButton2;
        if (radioButton2.getText().toString().equals("year")) {
            this.tenure = Double.valueOf(this.timeEdit.getText().toString()).doubleValue() * 12.0d;
        } else {
            this.tenure = Double.valueOf(this.timeEdit.getText().toString()).doubleValue();
        }
        if (this.tenure > 360.0d || Double.valueOf(this.rateEdit.getText().toString()).doubleValue() > 50.0d) {
            if (this.tenure > 360.0d) {
                Toast.makeText(this, "tenure should be less than 30 years", 0).show();
            } else {
                Toast.makeText(this, "Interest rate should be less than 50%", 0).show();
            }
        } else {
            calculation();
            double loanSql = Double.parseDouble(this.investmentEdit.getText().toString());
            double interestSql = Double.parseDouble(this.rateEdit.getText().toString());
            String sqlDate = this.date + " " + this.month + " " + this.year;
            if (this.radioButton.getText().toString().equals("year")) {
                periodSql = Double.valueOf(this.timeEdit.getText().toString()).doubleValue();
            } else {
                periodSql = Double.valueOf(this.timeEdit.getText().toString()).doubleValue() / 12.0d;
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
//            AnonymousClass12 r12 = r0;
            final double d3 = periodSql;
            double d4 = loanSql;
            Button save = (Button) entry.findViewById(R.id.save);
            final String str = sqlDate;
            Dialog dialog = entry;
            View.OnClickListener r0 = new View.OnClickListener() {
                public void onClick(View v) {
                    lumpsum.this.name = editText.getText().toString();
                    if (lumpsum.this.name.isEmpty()) {
                        lumpsum.this.name = "EMPTY";
                    }
                    SQLiteDatabase sQLiteDatabase = lumpsum.this.myDatabase;
                    sQLiteDatabase.execSQL("INSERT INTO lumpsumTable(name,principalAmount,interest,tenure,date) VALUES ('" + lumpsum.this.name + "'," + d + "," + d2 + "," + d3 + ",'" + str + "')");
                    if (lumpsum.this.ad.isLoaded()) {
                        lumpsum.this.ad.show();
                    }
                    lumpsum.this.ad.loadAd(new AdRequest.Builder().build());
                    entry.dismiss();
                }
            };
            save.setOnClickListener(r0);
        }
    }

    public void openingSaved(int position) {
        char c;
        char c2;
        Cursor cursor = this.myDatabase.rawQuery("SELECT * FROM lumpsumTable", null);
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
        this.investmentEdit.setText(String.valueOf(principle.get(position)));
        this.timeEdit.setText(String.valueOf(time.get(position)));
        this.rateEdit.setText(String.valueOf(interest.get(position)));
        this.day.setText(String.valueOf(dateHistory.get(position)));
        String[] dateregex = dateHistory.get(position).split(" ");
        this.date = Integer.parseInt(dateregex[0]);
        String str = dateregex[1];
        this.sMonth = str;
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
        this.tenure = Double.valueOf(this.timeEdit.getText().toString()).doubleValue() * 12.0d;
        calculation();
    }
}
