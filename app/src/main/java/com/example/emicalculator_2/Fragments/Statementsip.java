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

public class Statementsip extends Fragment {
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
    double maturityvalue;
    onMessageReadListner messageReadListner;
    UnifiedNativeAd nativeAd;
    String nativeAds;
    String premiumLink;
    double principal;
    double principia;
    int tdate;
    double time;
    TextView title;
    String tmonth;
    int tomonth;
    double totalinterest;
    int tyear;
    String what;

    public interface onMessageReadListner {
        void onMessage();
    }

    public Statementsip(double with, double period, double inte, double princi, double maurity, double totalint, int todate, int tomonth2, int toyear, String which) {
        double d = period;
        double d2 = princi;
        int i = tomonth2;
        int i2 = toyear;
        String str = which;
        this.time = d;
        this.what = str;
        this.interestinmonth = inte;
        this.principal = d2;
        if (str == "SIP" || str == "RD") {
            double d3 = this.principal;
            this.principia = d3;
            this.balance = d3;
        } else if (str == "SWP") {
            this.principia = 0.0d - with;
            this.balance = d2;
        } else {
            this.balance = d2;
            this.principia = 0.0d;
        }
        this.maturityvalue = maurity;
        this.totalinterest = totalint;
        this.tdate = todate;
        this.tomonth = i;
        this.tyear = i2;
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
        String str2 = "Mar";
        String str3 = "Feb";
        double d4 = (double) i;
        Double.isNaN(d4);
        int i3 = (int) (d4 + d);
        this.fomonth = i3;
        int index = (i3 - 1) / 12;
        this.fyear = toyear + index;
        int i4 = i3 - (index * 12);
        this.fomonth = i4;
        switch (i4) {
            case 1:
                this.fmonth = "Jan";
                return;
            case 2:
                this.fmonth = str3;
                return;
            case 3:
                this.fmonth = str2;
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
        String str;
        final View v = inflater.inflate(R.layout.statementsip, container, false);
        this.cancel = (Button) v.findViewById(R.id.cancel_action);
        this.Share = (Button) v.findViewById(R.id.Share);
        TextView textView = (TextView) v.findViewById(R.id.which);
        this.title = textView;
        textView.setText(this.what);
        LinearLayout month = (LinearLayout) v.findViewById(R.id.months);
        LinearLayout balan = (LinearLayout) v.findViewById(R.id.Balance);
        LinearLayout interest = (LinearLayout) v.findViewById(R.id.interest);
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
                Statementsip.this.nativeAd = unifiedNativeAd;
                UnifiedNativeAdView adview = (UnifiedNativeAdView) Statementsip.this.getLayoutInflater().inflate(R.layout.nativeads, (ViewGroup) null);
                LinearLayout container = (LinearLayout) v.findViewById(R.id.nativead);
                adview.setHeadlineView(adview.findViewById(R.id.heading));
                adview.setAdvertiserView(adview.findViewById(R.id.advertisername));
                adview.setBodyView(adview.findViewById(R.id.body));
                adview.setStarRatingView(adview.findViewById(R.id.start_rating));
                adview.setMediaView((MediaView) adview.findViewById(R.id.media_view));
                adview.setCallToActionView(adview.findViewById(R.id.calltoaction));
                adview.setIconView(adview.findViewById(R.id.adicon));
                adview.getMediaView().setMediaContent(unifiedNativeAd.getMediaContent());
                ((TextView) adview.getHeadlineView()).setText(Statementsip.this.nativeAd.getHeadline());
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
            this.actualinterest = this.interestinmonth * this.balance;
            TextView ainterest = new TextView(getContext());
            ainterest.setLayoutParams(new ViewGroup.LayoutParams(-1, 60));
            ainterest.setGravity(17);
            double d = this.actualinterest;
            LinearLayout month2 = month;
            if (d == ((double) ((int) d))) {
                ainterest.setText(String.valueOf(Integer.valueOf((int) d)));
            } else {
                ainterest.setText(String.valueOf(new BigDecimal(this.actualinterest).setScale(1, RoundingMode.HALF_UP).doubleValue()));
            }
            interest.addView(ainterest);
            if (((double) i) == this.time && ((str = this.what) == "SIP" || str == "RD")) {
                this.principia = 0.0d;
            }
            this.balance = this.balance + this.actualinterest + this.principia;
            TextView pr = new TextView(getContext());
            pr.setLayoutParams(new ViewGroup.LayoutParams(-1, 60));
            pr.setGravity(17);
            balan.addView(pr);
            double d2 = this.balance;
            if (d2 == ((double) ((int) d2))) {
                pr.setText(String.valueOf(Integer.valueOf(((int) d2) + ((int) this.principia))));
            } else {
                pr.setText(String.valueOf(new BigDecimal(this.balance).setScale(1, RoundingMode.HALF_UP).doubleValue()));
            }
            if (i % 2 == 0) {
                months.setBackgroundColor(Color.parseColor("#F1DED0"));
                pr.setBackgroundColor(Color.parseColor("#F1DED0"));
                ainterest.setBackgroundColor(Color.parseColor("#F1DED0"));
            } else {
                months.setBackgroundColor(Color.parseColor("#F8F4F4"));
                pr.setBackgroundColor(Color.parseColor("#F8F4F4"));
                ainterest.setBackgroundColor(Color.parseColor("#F8F4F4"));
            }
            i++;
            LayoutInflater layoutInflater = inflater;
            month = month2;
        }
        this.cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Statementsip.this.messageReadListner.onMessage();
                Statementsip.this.getFragmentManager().popBackStack();
            }
        });
        this.Share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Double total;
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                if (Statementsip.this.what == "SIP" || Statementsip.this.what == "RD") {
                    total = Double.valueOf(Statementsip.this.principal * Statementsip.this.time);
                } else {
                    total = Double.valueOf(Statementsip.this.principal);
                }
                intent.putExtra("android.intent.extra.SUBJECT", "EMI- A Calculator app");
                intent.putExtra("android.intent.extra.TEXT", Statementsip.this.what + "Details-\n\nInvestment Amount : " + Statementsip.this.principal + "\nTenure : " + Statementsip.this.time + "months\nFirst " + Statementsip.this.what + ": " + Statementsip.this.tdate + " " + Statementsip.this.tmonth + " " + Statementsip.this.tyear + "\n\nTotal Investment Amount: " + total + "\nTotal Interest: " + Statementsip.this.totalinterest + "\nMaturity Value: " + (Statementsip.this.totalinterest + Statementsip.this.principal) + "\nMaturity Date: " + Statementsip.this.tdate + " " + Statementsip.this.fmonth + " " + Statementsip.this.fyear + "\n\nCalculate by EMI\n" + Statementsip.this.appLink);
                Statementsip.this.startActivity(Intent.createChooser(intent, "Share Using"));
            }
        });
        return v;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.messageReadListner = (onMessageReadListner) ((Activity) context);
    }
}
