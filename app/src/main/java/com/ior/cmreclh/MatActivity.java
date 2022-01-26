package com.ior.cmreclh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static android.view.View.GONE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;
import static com.google.android.gms.ads.AdRequest.Builder;
import static com.ior.cmreclh.R.id;
import static com.ior.cmreclh.R.layout;
import static com.ior.cmreclh.R.layout.act_mat;

/**
 * Created by Abhishek Jha on 21-01-2017.
 */

public class MatActivity extends AppCompatActivity {

    Button mat_imp,mat_brief;
    String SubjectId,SubjectName;
    String FileName,Type;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(act_mat);


        mat_imp = (Button) findViewById(id.mat_imp);
        mat_brief = (Button) findViewById(id.mat_brief);

        mat_imp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Type = "Imp";
                redirectToPreview();
            }
        });


        mat_brief.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Type = "Brief";
                redirectToPreview();
            }
        });

        String Details = getIntent().getExtras().get("SubjectDetails").toString();

        StringTokenizer str = new StringTokenizer(Details, ",");
        ArrayList al = new ArrayList();
        while (str.hasMoreTokens())
            al.add(str.nextToken().trim());

        Object[] txt = al.toArray();
        String s1 = txt[0].toString();
        String s2 = txt[1].toString();

        SubjectId = s1.substring(1, s1.length());
        SubjectName = s2.substring(0, s2.length() - 1);

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

    public void redirectToPreview()
    {
        FileName=SubjectId+" "+SubjectName+" "+Type+".pdf";
        ArrayList<String> arrayList= new ArrayList<>();
        arrayList.add(0, SubjectId);
        arrayList.add(1, SubjectName);
        arrayList.add(2, Type);

        Intent intent= new Intent(MatActivity.this,MatPreviewActivity.class);
        intent.putStringArrayListExtra("SubjectDetails",arrayList);
        startActivity(intent);
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
