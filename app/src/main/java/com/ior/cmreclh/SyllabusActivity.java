package com.ior.cmreclh;

import android.app.Activity;
import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.ior.cmreclh.api.VolleyMaterialImpApi;
import com.ior.cmreclh.api.VolleySyllabusApi;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by Abhishek Jha on 20-01-2017.
 */

public class SyllabusActivity extends AppCompatActivity {

    String SubjectId,SubjectName,SubjectLoc,SubjectLocalLoc,LocalLoc;
    PDFView pdfView;
    SQLdbClass helper;
    SQLiteDatabase SQdb;
    Cursor cursor;
    SQLiteStatement SQstmt;
    ArrayList al;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    int PageNo = 0;
    public static final String SYLLABUS_PAGE_NO = "SYLLABUS_PAGE_NO";

    LinearLayout llSyllabus, llProgress;

    InterstitialAd interstitialAd = null;

    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_syllabus);
        setContentView(R.layout.activity_syllabus);

        if(ContextCompat.checkSelfPermission(SyllabusActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(SyllabusActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_CONSTANT);
        } else if (ContextCompat.checkSelfPermission(SyllabusActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(SyllabusActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_CONSTANT);
        } else {

            try {
                SharedPreferences prefs = getSharedPreferences(SYLLABUS_PAGE_NO, MODE_PRIVATE);
                PageNo = prefs.getInt("PageNo", 0); //0 is the default value.
            } catch (Exception e) {
                Log.i("initially", e.getMessage());
            }

            try {
                pdfView = (PDFView) findViewById(R.id.sylPdfView);
            } catch (Exception e) {
                Toast.makeText(this, "PDF Viewer not found", Toast.LENGTH_SHORT).show();
                e.getMessage();
            }

            llSyllabus = (LinearLayout) findViewById(R.id.llSyllabus);
            llProgress = (LinearLayout) findViewById(R.id.llProgress);

            LocalLoc = getApplicationContext().getFilesDir().toString();

            String Details = getIntent().getExtras().get("SubjectDetails").toString();

            StringTokenizer str = new StringTokenizer(Details, ",");
            ArrayList al = new ArrayList();
            while (str.hasMoreTokens())
                al.add(str.nextToken().trim());

            Object[] txt = al.toArray();
            String s1 = txt[0].toString();
            SubjectId = s1.substring(1, s1.length() - 1);
            checkLocal();

            interstitialAd = new InterstitialAd(this);
            interstitialAd.setAdUnitId(getString(R.string.cmrec_ad_mob_Interstitial));

            AdRequest IadRequest = new AdRequest.Builder().build();
        /*AdRequest IadRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .addTestDevice("894C56D512CCCEB28D02522FAFBD0F53")  // My Galaxy Nexus test phone
                .build();*/
            interstitialAd.loadAd(IadRequest);

        }
    }

    public void checkFileStatus()
    {

       // SubjectLocalLoc= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/.CMREC Little Helper/"+ SubjectName;
        SubjectLocalLoc= "/sdcard/Android/data/com.ior.cmreclh/files/"+SubjectName;
        File file=new File(SubjectLocalLoc);
        if(file.exists())
        {
            displayData();
        }
        else {
           //Toast.makeText(this, SubjectLocalLoc, Toast.LENGTH_SHORT).show();
            downloadData();
        }
    }

    public void checkLocal()
    {
        progressDialog = new ProgressDialog(SyllabusActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Fetching syllabus details...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        al=new ArrayList<String>();
        helper=new SQLdbClass(this);
        SQdb=helper.getReadableDatabase();

        try {
            cursor = SQdb.rawQuery("select * from Syllabus", null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    SubjectId=cursor.getString(cursor.getColumnIndex("SubjectCode"));
                    SubjectName=cursor.getString(cursor.getColumnIndex("SubjectName"));
                    SubjectLoc=cursor.getString(cursor.getColumnIndex("SubjectLoc"));

                    checkFileStatus();

                }else{

                    if (checkNetworkConnection())
                        getDataFromServer();
                    else
                    {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    /*SyllabusActivity.getDataServer get = new SyllabusActivity.getDataServer();// this is the Asynctask, which is used to process in background to reduce load on app process
                    get.execute("");*/

                }
            }
        }catch (Exception e)
        {
           // Toast.makeText(this,"Check Local Error "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    protected void getDataFromServer()
    {
        requestQueue = Volley.newRequestQueue(SyllabusActivity.this);

        VolleySyllabusApi apiRequest = new VolleySyllabusApi(SubjectId, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("Response", response);

                try {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();

                    JSONObject jsonObject = new JSONObject(response);

                    String s1=SubjectId,s2=jsonObject.getString("SubjectName"),s3=jsonObject.getString("FileLocation");

                    SQstmt=SQdb.compileStatement("insert into Syllabus(SubjectCode,SubjectName,SubjectLoc) values(?,?,?)");
                    SQstmt.bindString(1,SubjectId);
                    SQstmt.bindString(2,jsonObject.getString("SubjectName"));
                    SQstmt.bindString(3,jsonObject.getString("FileLocation"));
                    SQstmt.executeInsert();

                    checkLocal();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        requestQueue.add(apiRequest);
    }

    public void downloadData()
    {
        String FileLocServer = SubjectLoc;
        DownloadManager downloadManager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri= Uri.parse(FileLocServer);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        //request.setDestinationInExternalPublicDir( Environment.DIRECTORY_DOWNLOADS+"/.CMREC Little Helper/",SubjectName);
        request.setDestinationInExternalFilesDir(getApplicationContext(),null,SubjectName);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        Long reference=downloadManager.enqueue(request);

        Toast.makeText(this,"Please wait while Downloading", Toast.LENGTH_LONG).show();
        finish();
    }

    public void displayData()
    {
        try {

            File file = new File(SubjectLocalLoc);
            pdfView.fromFile(file)
                    .onRender(new OnRenderListener()
                    {
                        @Override
                        public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight)
                        {
                            pdfView.fitToWidth(PageNo);
                        }
                    })
                    .onPageChange(new OnPageChangeListener() {
                        @Override
                        public void onPageChanged(int page, int pageCount) {
                            PageNo = page;
                        }
                    })
                    .defaultPage(PageNo)
                    .load();


        }catch (Exception e){
            Toast.makeText(this,"Viewing error", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDestroy()
    {
        SharedPreferences.Editor editor = getSharedPreferences(SYLLABUS_PAGE_NO, MODE_PRIVATE).edit();
        editor.putInt("PageNo", PageNo);
        editor.apply();
        super.onDestroy();
    }

}
