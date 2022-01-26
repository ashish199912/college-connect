package com.ior.cmreclh;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.google.android.gms.ads.AdRequest.Builder;
import static com.ior.cmreclh.R.id;
import static com.ior.cmreclh.R.layout;
import static com.ior.cmreclh.R.layout.sac_mech;


/**
 * Created by Abhishek Jha on 26.06.2017.
 */

public class SacMECH extends AppCompatActivity {

    Button btn1,btn2,btn3;
    LinearLayout ll1,ll2,ll3;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(sac_mech);


        btn1 = (Button) findViewById(id.btn1);
        ll1 = (LinearLayout) findViewById(id.ll1);
        btn2 = (Button) findViewById(id.btn2);
        ll2 = (LinearLayout) findViewById(id.ll2);
        btn3 = (Button) findViewById(id.btn3);
        ll3 = (LinearLayout) findViewById(id.ll3);

        adView = (AdView) findViewById(id.adView);
        AdRequest adRequest = new Builder().build();
        /*AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .addTestDevice("894C56D512CCCEB28D02522FAFBD0F53")  // My Galaxy Nexus test phone
                .build();*/
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                adView.setVisibility(GONE);
            }
        });

        /*StartAppSDK.init(this, "209189446", false);
        StartAppAd.disableSplash();*/
    }

    public void btn1_click(View view)
    {
        int v=ll1.getVisibility();
        if(v==8) {
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.GONE);
            ll3.setVisibility(View.GONE);

        }
        else
            ll1.setVisibility(View.GONE);
    }

    public void btn2_click(View view)
    {
        int v=ll2.getVisibility();
        if(v==8) {
            ll2.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.GONE);
            ll3.setVisibility(View.GONE);

        }
        else
            ll2.setVisibility(View.GONE);
    }

    public void btn3_click(View view)
    {
        int v=ll3.getVisibility();
        if(v==8) {
            ll3.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);

        }
        else
            ll3.setVisibility(View.GONE);
    }


    @Override
    public void onPause()
    {
        if(adView != null)
            adView.pause();
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(adView != null)
            adView.resume();
    }

    @Override
    public void onDestroy()
    {
        if(adView != null)
            adView.destroy();
        super.onDestroy();
    }
}
