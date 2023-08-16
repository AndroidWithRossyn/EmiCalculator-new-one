package com.example.emicalculator_2.FIles;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.emicalculator_2.Calculations.Currency;
import com.example.emicalculator_2.Calculations.Fd;
import com.example.emicalculator_2.Calculations.Rd;
import com.example.emicalculator_2.Calculations.SimpleAndCompound;
import com.example.emicalculator_2.Calculations.Sip;
import com.example.emicalculator_2.Calculations.Swp;
import com.example.emicalculator_2.Calculations.Tax;
import com.example.emicalculator_2.Calculations.compareloan;
import com.example.emicalculator_2.Calculations.emi;
import com.example.emicalculator_2.Calculations.loaneligibility;
import com.example.emicalculator_2.Calculations.lumpsum;
import com.example.emicalculator_2.Calculations.ppf;
import com.example.emicalculator_2.Calculations.saving;
import com.example.emicalculator_2.Constants;
import com.example.emicalculator_2.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.scrounger.countrycurrencypicker.library.Country;
import com.scrounger.countrycurrencypicker.library.CountryCurrencyPicker;
import com.scrounger.countrycurrencypicker.library.Listener.CountryCurrencyPickerListener;
import com.scrounger.countrycurrencypicker.library.PickerType;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    InterstitialAd ad;
    String appLink;
    Dialog dialog;
    Dialog dialog1;
    int displayAds;
    SharedPreferences frontshare;
    String interAds;
    UnifiedNativeAd nativeAd;
    String nativeAds;
    String premiumLink;
    SharedPreferences sharedPreferences;
    SharedPreferences themePreference;

    public void finder(final View view) {
        String find;
        String find2;
        displayAds = frontshare.getInt("open", 0);
        if (!ad.isLoaded() || displayAds % 2 != 0) {
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
            if (view.getTag().toString().equals("0")) {
                find = "bank";
            } else {
                find = "atm";
            }
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.google.co.in/maps/search/" + find));
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(268435456);
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps"));
                intent2.setFlags(268435456);
                startActivity(intent2);
            }
        } else {
            ad.show();
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
            if (view.getTag().toString().equals("0")) {
                find2 = "bank";
            } else {
                find2 = "atm";
            }
            try {
                Intent intent3 = new Intent("android.intent.action.VIEW", Uri.parse("https://www.google.co.in/maps/search/" + find2));
                intent3.setPackage("com.google.android.apps.maps");
                intent3.setFlags(268435456);
                startActivity(intent3);
            } catch (ActivityNotFoundException e2) {
                Intent intent4 = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps"));
                intent4.setFlags(268435456);
                startActivity(intent4);
            }
        }
        ad.setAdListener(new AdListener() {
            public void onAdLoaded() {
                String find;
                loadInterstitialAd();
                if (view.getTag().toString().equals("0")) {
                    find = "bank";
                } else {
                    find = "atm";
                }
                try {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.google.co.in/maps/search/" + find));
                    intent.setPackage("com.google.android.apps.maps");
                    intent.setFlags(268435456);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps"));
                    intent2.setFlags(268435456);
                    startActivity(intent2);
                }
            }

            public void onAdFailedToLoad(LoadAdError adError) {
                String find;
                loadInterstitialAd();
                if (view.getTag().toString().equals("0")) {
                    find = "bank";
                } else {
                    find = "atm";
                }
                try {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.google.co.in/maps/search/" + find));
                    intent.setPackage("com.google.android.apps.maps");
                    intent.setFlags(268435456);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps"));
                    intent2.setFlags(268435456);
                    startActivity(intent2);
                }
            }
        });
    }

    public void emi(View view) {
        displayAds = frontshare.getInt("open", 0);
        if (!ad.isLoaded() || displayAds % 2 != 0) {
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
            startActivity(new Intent(getApplicationContext(), emi.class));
            overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
        } else {
            ad.show();
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
        }
        ad.setAdListener(new AdListener() {
            public void onAdClosed() {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), emi.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }

            public void onAdFailedToLoad(LoadAdError adError) {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), emi.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }
        });
    }

    public void tax(View view) {
        displayAds = frontshare.getInt("open", 0);
        if (!ad.isLoaded() || displayAds % 2 != 0) {
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
            startActivity(new Intent(getApplicationContext(), Tax.class));
            overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
        } else {
            ad.show();
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
        }
        ad.setAdListener(new AdListener() {
            public void onAdClosed() {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), Tax.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }

            public void onAdFailedToLoad(LoadAdError adError) {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), Tax.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }
        });
    }

    public void loanelgibility(View view) {
        displayAds = frontshare.getInt("open", 0);
        if (ad.isLoaded()) {
            int i = displayAds;
            if (i % 2 == 0) {
                displayAds = i + 1;
                frontshare.edit().putInt("open", displayAds).apply();
                ad.show();
                ad.setAdListener(new AdListener() {
                    public void onAdClosed() {
                        loadInterstitialAd();
                        startActivity(new Intent(getApplicationContext(), loaneligibility.class));
                        overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
                    }

                    public void onAdFailedToLoad(LoadAdError adError) {
                        loadInterstitialAd();
                        startActivity(new Intent(getApplicationContext(), loaneligibility.class));
                        overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
                    }
                });
            }
        }
        displayAds++;
        frontshare.edit().putInt("open", displayAds).apply();
        startActivity(new Intent(getApplicationContext(), loaneligibility.class));
        overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
        ad.setAdListener(new AdListener() {
            public void onAdClosed() {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), loaneligibility.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }

            public void onAdFailedToLoad(LoadAdError adError) {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), loaneligibility.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }
        });
    }

    public void compareloan(View view) {
        displayAds = frontshare.getInt("open", 0);
        if (!ad.isLoaded() || displayAds % 2 != 0) {
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
            startActivity(new Intent(getApplicationContext(), compareloan.class));
            overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
        } else {
            ad.show();
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
        }
        ad.setAdListener(new AdListener() {
            public void onAdClosed() {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), compareloan.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }

            public void onAdFailedToLoad(LoadAdError adError) {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), compareloan.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }
        });
    }

    public void currency(View view) {
        displayAds = frontshare.getInt("open", 0);
        if (!ad.isLoaded() || displayAds % 2 != 0) {
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
            startActivity(new Intent(getApplicationContext(), Currency.class));
            overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
        } else {
            ad.show();
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
        }
        ad.setAdListener(new AdListener() {
            public void onAdClosed() {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), Currency.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }

            public void onAdFailedToLoad(LoadAdError adError) {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), Currency.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }
        });
    }

    public void fd(View view) {
        displayAds = frontshare.getInt("open", 0);
        if (!ad.isLoaded() || displayAds % 2 != 0) {
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
            startActivity(new Intent(getApplicationContext(), Fd.class));
            overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
        } else {
            ad.show();
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
        }
        ad.setAdListener(new AdListener() {
            public void onAdClosed() {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), Fd.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }

            public void onAdFailedToLoad(LoadAdError adError) {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), Fd.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }
        });
    }

    public void sip(View view) {
        displayAds = frontshare.getInt("open", 0);
        if (!ad.isLoaded() || displayAds % 2 != 0) {
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
            startActivity(new Intent(getApplicationContext(), Sip.class));
            overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
        } else {
            ad.show();
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
        }
        ad.setAdListener(new AdListener() {
            public void onAdClosed() {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), Sip.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }

            public void onAdFailedToLoad(LoadAdError adError) {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), Sip.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }
        });
    }

    public void ppf(View view) {
        displayAds = frontshare.getInt("open", 0);
        if (!ad.isLoaded() || displayAds % 2 != 0) {
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
            startActivity(new Intent(getApplicationContext(), ppf.class));
            overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
        } else {
            ad.show();
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
        }
        ad.setAdListener(new AdListener() {
            public void onAdClosed() {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), ppf.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }

            public void onAdFailedToLoad(LoadAdError adError) {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), ppf.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }
        });
    }

    public void rd(View view) {
        displayAds = frontshare.getInt("open", 0);
        if (ad.isLoaded()) {
            int i = displayAds;
            if (i % 2 == 0) {
                displayAds = i + 1;
                frontshare.edit().putInt("open", displayAds).apply();
                ad.show();
                ad.setAdListener(new AdListener() {
                    public void onAdClosed() {
                        loadInterstitialAd();
                        startActivity(new Intent(getApplicationContext(), Rd.class));
                        overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
                    }

                    public void onAdFailedToLoad(LoadAdError adError) {
                        loadInterstitialAd();
                        startActivity(new Intent(getApplicationContext(), Rd.class));
                        overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
                    }
                });
            }
        }
        displayAds++;
        frontshare.edit().putInt("open", displayAds).apply();
        startActivity(new Intent(getApplicationContext(), Rd.class));
        overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
        ad.setAdListener(new AdListener() {
            public void onAdClosed() {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), Rd.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }

            public void onAdFailedToLoad(LoadAdError adError) {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), Rd.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }
        });
    }

    public void swp(View view) {
        displayAds = frontshare.getInt("open", 0);
        if (!ad.isLoaded() || displayAds % 2 != 0) {
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
            startActivity(new Intent(getApplicationContext(), Swp.class));
            overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
        } else {
            ad.show();
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
        }
        ad.setAdListener(new AdListener() {
            public void onAdClosed() {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), Swp.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }

            public void onAdFailedToLoad(LoadAdError adError) {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), Swp.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }
        });
    }

    public void saving(View view) {
        displayAds = frontshare.getInt("open", 0);
        if (!ad.isLoaded() || displayAds % 2 != 0) {
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
            startActivity(new Intent(getApplicationContext(), saving.class));
            overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
        } else {
            ad.show();
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
        }
        ad.setAdListener(new AdListener() {
            public void onAdClosed() {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), saving.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }

            public void onAdFailedToLoad(LoadAdError adError) {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), saving.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }
        });
    }

    public void lumpsum(View view) {
        displayAds = frontshare.getInt("open", 0);
        if (!ad.isLoaded() || displayAds % 2 != 0) {
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
            startActivity(new Intent(getApplicationContext(), lumpsum.class));
            overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
        } else {
            ad.show();
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
        }
        ad.setAdListener(new AdListener() {
            public void onAdClosed() {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), lumpsum.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }

            public void onAdFailedToLoad(LoadAdError adError) {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), lumpsum.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }
        });
    }

    public void simpleandcomp(View view) {
        displayAds = frontshare.getInt("open", 0);
        if (!ad.isLoaded() || displayAds % 2 != 0) {
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
            startActivity(new Intent(getApplicationContext(), SimpleAndCompound.class));
            overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
        } else {
            ad.show();
            displayAds++;
            frontshare.edit().putInt("open", displayAds).apply();
        }
        ad.setAdListener(new AdListener() {
            public void onAdClosed() {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), SimpleAndCompound.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }

            public void onAdFailedToLoad(LoadAdError adError) {
                loadInterstitialAd();
                startActivity(new Intent(getApplicationContext(), SimpleAndCompound.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeeout);
            }
        });
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        frontshare = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, 0);
        nativeAds = getString(R.string.nativeAds);
        premiumLink = getString(R.string.premiumAppLink);
        appLink = getString(R.string.appLink);
        interAds = getString(R.string.InterstialAds);
        SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, 0);
        themePreference = sharedPreferences2;
        Constants.LIGHT_THEME = sharedPreferences2.getBoolean("THEME", true);
        if (!Constants.LIGHT_THEME) {
            AppCompatDelegate.setDefaultNightMode(2);
        }
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        dialog1 = new Dialog(this);
        dialog = new Dialog(this);
        new AdLoader.Builder((Context) this, nativeAds).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                nativeAd = unifiedNativeAd;
                UnifiedNativeAdView adview = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.nativeads, (ViewGroup) null);
                LinearLayout container = (LinearLayout) findViewById(R.id.nativead);
                container.setVisibility(0);
                adview.setHeadlineView(adview.findViewById(R.id.heading));
                adview.setAdvertiserView(adview.findViewById(R.id.advertisername));
                adview.setBodyView(adview.findViewById(R.id.body));
                adview.setStarRatingView(adview.findViewById(R.id.start_rating));
                adview.setMediaView((MediaView) adview.findViewById(R.id.media_view));
                adview.setCallToActionView(adview.findViewById(R.id.calltoaction));
                adview.setIconView(adview.findViewById(R.id.adicon));
                adview.getMediaView().setMediaContent(unifiedNativeAd.getMediaContent());
                ((TextView) adview.getHeadlineView()).setText(nativeAd.getHeadline());
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
        int times = frontshare.getInt("times", 0);
        displayAds = frontshare.getInt("Opened", 0);
        if (times == 0) {
            frontshare.edit().putInt("times", 1).apply();
            final Dialog dialog2 = new Dialog(this);
            dialog2.setContentView(R.layout.frontpage);
            dialog2.setCancelable(true);
            dialog2.getWindow().setGravity(17);
            dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog2.setCanceledOnTouchOutside(true);
            dialog2.getWindow().setLayout(-2, -2);
            dialog2.getWindow().getAttributes().windowAnimations = 16973826;
            dialog2.show();
            ((Button) dialog2.findViewById(R.id.okay)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog2.dismiss();
                }
            });
        }
        MobileAds.initialize((Context) this, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        ((AdView) findViewById(R.id.innerAdView)).loadAd(new AdRequest.Builder().build());
        InterstitialAd interstitialAd = new InterstitialAd(this);
        ad = interstitialAd;
        interstitialAd.setAdUnitId(interAds);
        ad.loadAd(new AdRequest.Builder().build());
    }

    public void loadInterstitialAd() {
        ad.loadAd(new AdRequest.Builder().build());
    }

    public void premium() {
        dialog1.setContentView(R.layout.premium);
        dialog1.setCancelable(true);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.getWindow().setLayout(-2, -2);
        dialog1.getWindow().getAttributes().windowAnimations = 16973826;
        dialog1.show();
        ((Button) dialog1.findViewById(R.id.share)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(premiumLink));
                startActivity(intent);
                dialog1.dismiss();
                MainActivity.super.onBackPressed();
            }
        });
        ((Button) dialog1.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog1.dismiss();
                MainActivity.super.onBackPressed();
            }
        });
        dialog1.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                MainActivity.super.onBackPressed();
            }
        });
    }

    public void end() {
        ((FrameLayout) findViewById(R.id.blur)).setBackgroundColor(Color.parseColor("#59000000"));
        ((LinearLayout) findViewById(R.id.exit)).setVisibility(0);
    }

    public void yes(View view) {
        super.onBackPressed();
    }

    public void no(View view) {
        ((FrameLayout) findViewById(R.id.blur)).setBackgroundColor(Color.parseColor("#00FFFFFF"));
        ((LinearLayout) findViewById(R.id.exit)).setVisibility(8);
    }

    public void onBackPressed() {
        int times = frontshare.getInt("times", 0);
        if (times % 2 == 0) {
            frontshare.edit().putInt("times", times + 1).apply();
            if (Build.VERSION.SDK_INT >= 22) {
                premium();
                return;
            }
            return;
        }
        frontshare.edit().putInt("times", times + 1).apply();
        end();
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.rate) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(appLink));
            startActivity(intent);
            finish();
        } else if (id == R.id.theme) {
            newTheme();
        } else if (id == R.id.currency) {
            newCurrency();
        } else if (id == R.id.share) {
            Intent intent2 = new Intent("android.intent.action.SEND");
            intent2.setType("text/plain");
            intent2.putExtra("android.intent.extra.TEXT", appLink);
            intent2.putExtra("android.intent.extra.SUBJECT", "EMI A Financial Calculator");
            startActivity(Intent.createChooser(intent2, "Share Using"));
        }
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer((int) GravityCompat.START);
        return true;
    }

    private void newCurrency() {
        try {
            sharedPreferences = getApplicationContext().getSharedPreferences(Constants.PACKAGE_NAME, 0);
            CountryCurrencyPicker.newInstance(PickerType.CURRENCY, new CountryCurrencyPickerListener() {
                public void onSelectCountry(Country country) {
                    sharedPreferences.edit().putString(Constants.CURRENCY, country.getCurrency().getSymbol()).apply();
                    Toast.makeText(MainActivity.this, country.getCurrency().getSymbol(), 0).show();
                    Constants.CURRENCY_STORED = country.getCurrency().getSymbol();
                }

                public void onSelectCurrency(com.scrounger.countrycurrencypicker.library.Currency currency) {
                    sharedPreferences.edit().putString(Constants.CURRENCY, currency.getSymbol()).apply();
                    Toast.makeText(MainActivity.this, currency.getSymbol(), 0).show();
                    Constants.CURRENCY_STORED = currency.getSymbol();
                }
            }).show(getSupportFragmentManager(), CountryCurrencyPicker.DIALOG_NAME);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), 1).show();
        }
    }

    private void newTheme() {
        final Dialog theme = new Dialog(this);
        theme.setContentView(R.layout.theme_dialog);
        theme.setCancelable(true);
        theme.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        theme.getWindow().setLayout(-2, -2);
        theme.getWindow().setGravity(17);
        theme.show();
        SwitchCompat dark = (SwitchCompat) theme.findViewById(R.id.dark);
        Constants.LIGHT_THEME = themePreference.getBoolean("THEME", true);
        if (!Constants.LIGHT_THEME) {
            dark.setChecked(true);
        }
        dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AppCompatDelegate.setDefaultNightMode(2);
                    themePreference.edit().putBoolean("THEME", false).apply();
                    Constants.LIGHT_THEME = false;
                    theme.dismiss();
                    return;
                }
                AppCompatDelegate.setDefaultNightMode(1);
                themePreference.edit().putBoolean("THEME", true).apply();
                Constants.LIGHT_THEME = true;
                theme.dismiss();
            }
        });
    }

    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(premiumLink));
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
