package com.example.emicalculator_2.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.emicalculator_2.R;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Statement extends Fragment {
    Button Pdf;
    Button Share;
    double actualinterest;
    String appLink;
    double balance;
    Button cancel;
    String fmonth;
    int fomonth;
    int fyear;
    String interAds;
    double interestinmonth;
    onMessageReadListner messageReadListner;
    double monthlyemi;
    UnifiedNativeAd nativeAd;
    String nativeAds;
    String premiumLink;
    double principal;
    double principia;
    int tdate;
    double time;
    String tmonth;
    int tomonth;
    double totalinterest;
    int tyear;

    public interface onMessageReadListner {
        void onMessage();
    }

    public Statement(double period, double inte, double princi, double emi, double totalint, int todate, int tomonth2, int toyear) {
        double d = period;
        double d2 = princi;
        int i = tomonth2;
        this.time = d;
        this.interestinmonth = inte;
        this.principal = d2;
        this.principia = d2;
        this.monthlyemi = emi;
        this.totalinterest = totalint;
        this.tdate = todate;
        this.tomonth = i;
        this.tyear = toyear;
        switch (i) {
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
        String str = "Mar";
        String str2 = "Feb";
        double d3 = (double) i;
        Double.isNaN(d3);
        int i2 = (int) (d3 + d);
        this.fomonth = i2;
        int index = (i2 - 1) / 12;
        this.fyear = toyear + index;
        int i3 = i2 - (index * 12);
        this.fomonth = i3;
        switch (i3) {
            case 1:
                this.fmonth = "Jan";
                return;
            case 2:
                this.fmonth = str2;
                return;
            case 3:
                this.fmonth = str;
                return;
            case 4:
                this.fmonth = "April";
                return;
            case 5:
                this.fmonth = "May";
                return;
            case 6:
                this.fmonth = "Jun";
                return;
            case 7:
                this.fmonth = "July";
                return;
            case 8:
                this.fmonth = "Aug";
                return;
            case 9:
                this.fmonth = "Sep";
                return;
            case 10:
                this.fmonth = "Oct";
                return;
            case 11:
                this.fmonth = "Nov";
                return;
            case 12:
                this.fmonth = "Dec";
                return;
            default:
                return;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.statistics, container, false);
        this.cancel = (Button) v.findViewById(R.id.cancel_action);
        this.Share = (Button) v.findViewById(R.id.Share);
        LinearLayout month = (LinearLayout) v.findViewById(R.id.months);
        LinearLayout interset = (LinearLayout) v.findViewById(R.id.Interest);
        LinearLayout balan = (LinearLayout) v.findViewById(R.id.Balance);
        LinearLayout princip = (LinearLayout) v.findViewById(R.id.principal);
        this.nativeAds = getString(R.string.nativeAds);
        this.premiumLink = getString(R.string.premiumAppLink);
        this.appLink = getString(R.string.appLink);
        this.interAds = getString(R.string.InterstialAds);
        MobileAds.initialize((Context) getActivity(), (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        new AdLoader.Builder((Context) getActivity(), this.nativeAds).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                Statement.this.nativeAd = unifiedNativeAd;
                UnifiedNativeAdView adview = (UnifiedNativeAdView) Statement.this.getLayoutInflater().inflate(R.layout.nativeads, (ViewGroup) null);
                LinearLayout container = (LinearLayout) v.findViewById(R.id.nativead);
                adview.setHeadlineView(adview.findViewById(R.id.heading));
                adview.setAdvertiserView(adview.findViewById(R.id.advertisername));
                adview.setBodyView(adview.findViewById(R.id.body));
                adview.setStarRatingView(adview.findViewById(R.id.start_rating));
                adview.setMediaView((MediaView) adview.findViewById(R.id.media_view));
                adview.setCallToActionView(adview.findViewById(R.id.calltoaction));
                adview.setIconView(adview.findViewById(R.id.adicon));
                adview.getMediaView().setMediaContent(unifiedNativeAd.getMediaContent());
                ((TextView) adview.getHeadlineView()).setText(Statement.this.nativeAd.getHeadline());
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
        int i = 1;
        while (((double) i) <= this.time) {
            TextView months = new TextView(getContext());
            months.setLayoutParams(new ViewGroup.LayoutParams(-1, 60));
            months.setText(String.valueOf(i));
            months.setGravity(17);
            month.addView(months);
            this.actualinterest = this.interestinmonth * this.principal;
            TextView ainterest = new TextView(getContext());
            ainterest.setLayoutParams(new ViewGroup.LayoutParams(-1, 60));
            ainterest.setGravity(17);
            interset.addView(ainterest);
            LinearLayout month2 = month;
            this.balance = this.monthlyemi - this.actualinterest;
            TextView pr = new TextView(getContext());
            pr.setLayoutParams(new ViewGroup.LayoutParams(-1, 60));
            pr.setGravity(17);
            princip.addView(pr);
            this.principal -= this.balance;
            TextView ba = new TextView(getContext());
            ba.setLayoutParams(new ViewGroup.LayoutParams(-1, 60));
            ba.setGravity(17);
            balan.addView(ba);
            if (i % 2 == 0) {
                months.setBackgroundColor(Color.parseColor("#F1DED0"));
                ba.setBackgroundColor(Color.parseColor("#F1DED0"));
                pr.setBackgroundColor(Color.parseColor("#F1DED0"));
                ainterest.setBackgroundColor(Color.parseColor("#F1DED0"));
            } else {
                months.setBackgroundColor(Color.parseColor("#F8F4F4"));
                ba.setBackgroundColor(Color.parseColor("#F8F4F4"));
                pr.setBackgroundColor(Color.parseColor("#F8F4F4"));
                ainterest.setBackgroundColor(Color.parseColor("#F8F4F4"));
            }
            double d = this.actualinterest;
            LinearLayout interset2 = interset;
            LinearLayout balan2 = balan;
            if (d == ((double) ((int) d))) {
                ainterest.setText(String.valueOf(Integer.valueOf((int) d)));
            } else {
                double doubleValue = new BigDecimal(this.actualinterest).setScale(1, RoundingMode.HALF_UP).doubleValue();
                this.actualinterest = doubleValue;
                ainterest.setText(String.valueOf(doubleValue));
            }
            double d2 = this.balance;
            if (d2 == ((double) ((int) d2))) {
                pr.setText(String.valueOf(Integer.valueOf((int) d2)));
            } else {
                double doubleValue2 = new BigDecimal(this.balance).setScale(1, RoundingMode.HALF_UP).doubleValue();
                this.balance = doubleValue2;
                pr.setText(String.valueOf(doubleValue2));
            }
            double d3 = this.principal;
            if (d3 == ((double) ((int) d3))) {
                ba.setText(String.valueOf(Integer.valueOf((int) d3)));
            } else {
                double doubleValue3 = new BigDecimal(this.principal).setScale(1, RoundingMode.HALF_UP).doubleValue();
                this.principal = doubleValue3;
                ba.setText(String.valueOf(doubleValue3));
            }
            i++;
            LayoutInflater layoutInflater = inflater;
            ViewGroup viewGroup = container;
            month = month2;
            interset = interset2;
            balan = balan2;
        }
        this.cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Statement.this.messageReadListner.onMessage();
                Statement.this.getFragmentManager().popBackStack();
            }
        });
        this.Share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.SUBJECT", "EMI- A Financial Calculator");
                intent.putExtra("android.intent.extra.TEXT", "EMI Details-\n \nPrincipal Loan Amount: " + Statement.this.principia + "\nLoan term: " + Statement.this.time + "\nFirst EMI at: " + Statement.this.tdate + " " + Statement.this.tmonth + " " + Statement.this.tyear + "\n\nMonthly EMI: " + Statement.this.monthlyemi + "\nTotal Interest: " + Statement.this.totalinterest + "\nTotal payment: " + (Statement.this.totalinterest + Statement.this.principal) + "\nLast Loan Date: " + Statement.this.tdate + " " + Statement.this.fmonth + " " + Statement.this.fyear + "\n\nCalculate by EMI\n" + Statement.this.appLink);
                Statement.this.startActivity(Intent.createChooser(intent, "Share Using"));
            }
        });
        return v;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.messageReadListner = (onMessageReadListner) ((Activity) context);
    }
}
