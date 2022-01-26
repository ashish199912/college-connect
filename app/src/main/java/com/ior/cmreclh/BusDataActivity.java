package com.ior.cmreclh;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;



/**
 * Created by Mac on 30-07-2017.
 */

public class BusDataActivity extends AppCompatActivity {
    PDFView pdfView=null;
    private String FileLocation=getResources().getString(R.string.bus_file_location_download);
    private String FileName="TransData.pdf";
    private String FileLocalLoc= getResources().getString(R.string.bus_file_location_local)+FileName;

    private AdView adView;

    InterstitialAd interstitialAd = null;

    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.bus_details);



        try{
            pdfView=(PDFView)findViewById(R.id.busPdfView);
        }catch (Exception e){
            Toast.makeText(this,"PDF Viewer not found", Toast.LENGTH_SHORT).show();
            e.getMessage();
        }

        checkFileStatus();

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

    public void checkFileStatus()
    {

        File file=new File(FileLocalLoc);
        if(file.exists())
        {
            displayData();
        }
        else {
            //Toast.makeText(this, SubjectLocalLoc, Toast.LENGTH_SHORT).show();
            downloadData();
        }
    }

    public void downloadData()
    {
        if (checkNetworkConnection()) {

            String FileLocServer = FileLocation;
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(FileLocServer);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            //request.setDestinationInExternalPublicDir( Environment.DIRECTORY_DOWNLOADS+"/.CMREC Little Helper/",SubjectName);
            request.setDestinationInExternalFilesDir(getApplicationContext(), null, FileName);

            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            downloadManager.enqueue(request);

            Toast.makeText(this, "Please wait while Downloading", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void displayData()
    {
        try {
            File file = new File(FileLocalLoc);
            pdfView.fromFile(file).load();
        }catch (Exception e){
            Toast.makeText(this,"Viewing error", Toast.LENGTH_SHORT).show();
        }
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

    private boolean checkNetworkConnection()
    {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
