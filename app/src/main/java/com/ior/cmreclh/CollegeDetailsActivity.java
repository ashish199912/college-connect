package com.ior.cmreclh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;


/**
 * Created by Abhishek Jha on 20-01-2017.
 */

public class CollegeDetailsActivity extends AppCompatActivity {

    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10;
    LinearLayout ll1,ll2,ll3,ll4,ll5,ll6,ll7,ll8,ll9,ll10;

    private AdView adView;

    InterstitialAd interstitialAd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clg_details);



        btn1=(Button)findViewById(R.id.btn1);
        ll1=(LinearLayout)findViewById(R.id.ll1);
        btn2=(Button)findViewById(R.id.btn2);
        ll2=(LinearLayout)findViewById(R.id.ll2);
        btn3=(Button)findViewById(R.id.btn3);
        ll3=(LinearLayout)findViewById(R.id.ll3);
        btn4=(Button)findViewById(R.id.btn4);
        ll4=(LinearLayout)findViewById(R.id.ll4);
        btn5=(Button)findViewById(R.id.btn5);
        ll5=(LinearLayout)findViewById(R.id.ll5);
        btn6=(Button)findViewById(R.id.btn6);
        ll6=(LinearLayout)findViewById(R.id.ll6);
        btn7=(Button)findViewById(R.id.btn7);
        ll7=(LinearLayout)findViewById(R.id.ll7);
        btn8=(Button)findViewById(R.id.btn8);
        ll8=(LinearLayout)findViewById(R.id.ll8);
        btn9=(Button)findViewById(R.id.btn9);
        ll9=(LinearLayout)findViewById(R.id.ll9);
        btn10=(Button)findViewById(R.id.btn10);
        ll10=(LinearLayout)findViewById(R.id.ll10);

        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        /*AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .addTestDevice("894C56D512CCCEB28D02522FAFBD0F53")  // My Galaxy Nexus test phone
                .build();*/
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener()
        {
            @Override
            public void onAdLoaded()
            {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode)
            {
                super.onAdFailedToLoad(errorCode);
                adView.setVisibility(View.GONE);
            }
        });

        /*StartAppSDK.init(this, "209189446", false);
        StartAppAd.disableSplash();*/

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.cmrec_ad_mob_Interstitial));

         AdRequest IadRequest = new AdRequest.Builder().build();
        /*AdRequest IadRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .addTestDevice("894C56D512CCCEB28D02522FAFBD0F53")  // My Galaxy Nexus test phone
                .build();*/
        interstitialAd.loadAd(IadRequest);
    }

    public void btn1_click(View view)
    {
        int v=ll1.getVisibility();
        if(v==8) {
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.GONE);
            ll3.setVisibility(View.GONE);
            ll4.setVisibility(View.GONE);
            ll5.setVisibility(View.GONE);
            ll6.setVisibility(View.GONE);
            ll7.setVisibility(View.GONE);
            ll8.setVisibility(View.GONE);
            ll9.setVisibility(View.GONE);
            ll10.setVisibility(View.GONE);
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
            ll4.setVisibility(View.GONE);
            ll5.setVisibility(View.GONE);
            ll6.setVisibility(View.GONE);
            ll7.setVisibility(View.GONE);
            ll8.setVisibility(View.GONE);
            ll9.setVisibility(View.GONE);
            ll10.setVisibility(View.GONE);
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
            ll4.setVisibility(View.GONE);
            ll5.setVisibility(View.GONE);
            ll6.setVisibility(View.GONE);
            ll7.setVisibility(View.GONE);
            ll8.setVisibility(View.GONE);
            ll9.setVisibility(View.GONE);
            ll10.setVisibility(View.GONE);
        }
        else
            ll3.setVisibility(View.GONE);
    }

    public void btn4_click(View view)
    {
        int v=ll4.getVisibility();
        if(v==8) {
            ll4.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
            ll3.setVisibility(View.GONE);
            ll5.setVisibility(View.GONE);
            ll6.setVisibility(View.GONE);
            ll7.setVisibility(View.GONE);
            ll8.setVisibility(View.GONE);
            ll9.setVisibility(View.GONE);
            ll10.setVisibility(View.GONE);
        }
        else
            ll4.setVisibility(View.GONE);
    }

    public void btn5_click(View view)
    {
        int v=ll5.getVisibility();
        if(v==8) {
            ll5.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
            ll3.setVisibility(View.GONE);
            ll4.setVisibility(View.GONE);
            ll6.setVisibility(View.GONE);
            ll7.setVisibility(View.GONE);
            ll8.setVisibility(View.GONE);
            ll9.setVisibility(View.GONE);
            ll10.setVisibility(View.GONE);
        }
        else
            ll5.setVisibility(View.GONE);
    }

    public void btn6_click(View view)
    {
        int v=ll6.getVisibility();
        if(v==8) {
            ll6.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
            ll3.setVisibility(View.GONE);
            ll4.setVisibility(View.GONE);
            ll5.setVisibility(View.GONE);
            ll7.setVisibility(View.GONE);
            ll8.setVisibility(View.GONE);
            ll9.setVisibility(View.GONE);
            ll10.setVisibility(View.GONE);
        }
        else
            ll6.setVisibility(View.GONE);
    }

    public void btn7_click(View view)
    {
        int v=ll7.getVisibility();
        if(v==8) {
            ll7.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
            ll3.setVisibility(View.GONE);
            ll4.setVisibility(View.GONE);
            ll5.setVisibility(View.GONE);
            ll6.setVisibility(View.GONE);
            ll8.setVisibility(View.GONE);
            ll9.setVisibility(View.GONE);
            ll10.setVisibility(View.GONE);
        }
        else
            ll7.setVisibility(View.GONE);
    }

    public void btn8_click(View view)
    {
        int v=ll8.getVisibility();
        if(v==8) {
            ll8.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
            ll3.setVisibility(View.GONE);
            ll4.setVisibility(View.GONE);
            ll5.setVisibility(View.GONE);
            ll6.setVisibility(View.GONE);
            ll7.setVisibility(View.GONE);
            ll9.setVisibility(View.GONE);
            ll10.setVisibility(View.GONE);
        }
        else
            ll8.setVisibility(View.GONE);
    }

    public void btn9_click(View view)
    {
        int v=ll9.getVisibility();
        if(v==8) {
            ll9.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
            ll3.setVisibility(View.GONE);
            ll4.setVisibility(View.GONE);
            ll5.setVisibility(View.GONE);
            ll6.setVisibility(View.GONE);
            ll7.setVisibility(View.GONE);
            ll8.setVisibility(View.GONE);
            ll10.setVisibility(View.GONE);
        }
        else
            ll9.setVisibility(View.GONE);
    }

    public void btn10_click(View view)
    {
        int v=ll10.getVisibility();
        if(v==8) {
            ll10.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
            ll3.setVisibility(View.GONE);
            ll4.setVisibility(View.GONE);
            ll5.setVisibility(View.GONE);
            ll6.setVisibility(View.GONE);
            ll7.setVisibility(View.GONE);
            ll8.setVisibility(View.GONE);
            ll9.setVisibility(View.GONE);
        }
        else
            ll10.setVisibility(View.GONE);
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

    @Override
    public void onBackPressed()
    {
        if(interstitialAd.isLoaded())
        {
            interstitialAd.show();
            interstitialAd.setAdListener(new AdListener()
            {
                @Override
                public void onAdClosed()
                {
                    super.onAdClosed();
                    finish();
                }
            });
        } else
        {
            super.onBackPressed();
        }
    }


}
