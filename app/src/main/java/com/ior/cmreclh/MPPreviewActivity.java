package com.ior.cmreclh;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.ior.cmreclh.api.VolleyModelPaperApi;

/**
 * Created by Abhishek Jha on 21-01-2017.
 */

public class MPPreviewActivity extends AppCompatActivity {

    PDFView pdfView=null;
    String SubjectId,SubjectName,Type,FileName,FileLoc;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    int MP1PageNo = 0, MP2PageNo = 0, MP3PageNo = 0, PageNo = 0;
    public static final String MODEL_PAPER_PAGE_NO = "MATERIAL_PAGE_NO";

    InterstitialAd interstitialAd = null;

    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mp_preview);

        if(ContextCompat.checkSelfPermission(MPPreviewActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MPPreviewActivity.this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_CONSTANT);
        } else if (ContextCompat.checkSelfPermission(MPPreviewActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MPPreviewActivity.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_CONSTANT);
        } else {

            try {
                pdfView = (PDFView) findViewById(R.id.mpPdfView);
            } catch (Exception e) {
                Toast.makeText(this, "PDF Viewer not found", Toast.LENGTH_SHORT).show();
            }

            String Details = getIntent().getExtras().get("SubjectDetails").toString();

            StringTokenizer str = new StringTokenizer(Details, ",");
            ArrayList al = new ArrayList();
            while (str.hasMoreTokens())
                al.add(str.nextToken().trim());

            Object[] txt = al.toArray();
            String s1 = txt[0].toString();
            String s2 = txt[1].toString();
            String s3 = txt[2].toString();

            SubjectId = s1.substring(1, s1.length());
            SubjectName = s2.substring(0, s2.length());
            Type = s3.substring(0, s3.length() - 1);

            FileName = SubjectName + " " + Type + ".pdf";

            try {
                SharedPreferences prefs = getSharedPreferences(MODEL_PAPER_PAGE_NO, MODE_PRIVATE);
                MP1PageNo = prefs.getInt("MP1PageNo", 0); //0 is the default value.
                MP2PageNo = prefs.getInt("MP2PageNo", 0);
                MP3PageNo = prefs.getInt("MP3PageNo", 0);

                if (Type.equals("MP1"))
                    PageNo = MP1PageNo;
                else if (Type.equals("MP2"))
                    PageNo = MP2PageNo;
                else
                    PageNo = MP3PageNo;

            }catch (Exception e)
            {
                Log.i("initially", e.getMessage());
            }

            checkLocalAndPreview();

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


    public void checkLocalAndPreview()
    {

            FileLoc= "/sdcard/Android/data/com.ior.cmreclh/files/"+ FileName;
            //Toast.makeText(this,FileLoc,Toast.LENGTH_SHORT).show();

            File file=new File(FileLoc);
            if(file.exists())
            {
                //Toast.makeText(this,FileLoc+" File Exist",Toast.LENGTH_SHORT).show();
                pdfPreview();

            }
            else
            {
                if(checkNetworkConnection()) {
                    progressDialog = new ProgressDialog(MPPreviewActivity.this);
                    progressDialog.setTitle("Please wait...");
                    progressDialog.setMessage("Fetching material data...");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    getModelPaper();
                } else
                    Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }


    }


    public void pdfPreview()
    {
        try {
            File file = new File(FileLoc);

            pdfView.fromFile(file)
                    .onRender(new OnRenderListener()
                    {
                        @Override
                        public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight)
                        {
                            if (Type.equals("MP1"))
                                pdfView.fitToWidth(MP1PageNo);
                            else if (Type.equals("MP2"))
                                pdfView.fitToWidth(MP2PageNo);
                            else if (Type.equals("MP3"))
                                pdfView.fitToWidth(MP3PageNo);
                        }
                    })
                    .onPageChange(new OnPageChangeListener() {
                        @Override
                        public void onPageChanged(int page, int pageCount) {
                            if (Type.equals("MP1"))
                                MP1PageNo = page;
                            else if (Type.equals("MP2"))
                                MP2PageNo = page;
                            else if (Type.equals("MP3"))
                                MP3PageNo = page;
                        }
                    })
                    .defaultPage(PageNo)
                    .load();

        }catch (Exception e){
            //Toast.makeText(this,"Viewing error", Toast.LENGTH_SHORT).show();
            animationView();
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


    public void animationView()
    {
        LinearLayout NullLayout=(LinearLayout)findViewById(R.id.mpNullLayout);
        NullLayout.setVisibility(View.VISIBLE);
        TextView txt=(TextView)findViewById(R.id.txtNoMPContent);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink_anim);
        txt.startAnimation(animation);
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

    /*public class getModelPaper extends AsyncTask<String,String,String> {
        String z = "";

        @Override
        protected void onPreExecute() {
            llModelPaper.setVisibility(View.GONE);
            llProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            llModelPaper.setVisibility(View.VISIBLE);
            llProgress.setVisibility(View.GONE);
            Toast.makeText(MPPreviewActivity.this, z, Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        protected String doInBackground(String... params) {

            if(checkNetworkConnection())
                {
                    try {
                        //Get the DATABASE connection from DBClass.java
                        DBClass dbClass = new DBClass();
                        Connection con = dbClass.connectionclass();        // Connect to database
                        String query = "select * from ModelPapers where SubjectCode='" + SubjectId + "' and ModelPaperNo='"+Type+"' ";

                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next()) {
                            String FileLocServer = rs.getString("SubjectLocation").trim() + rs.getString("FileName").trim();
                            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse(FileLocServer);
                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setDestinationInExternalFilesDir(getApplicationContext(),null, FileName);
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                            Long reference = downloadManager.enqueue(request);

                            //Toast.makeText(MPPreviewActivity.this, "Please wait while Downloading", Toast.LENGTH_LONG).show();
                            z = "Please wait while Downloading";
                            //finish();

                        }

                    } catch (Exception e) {
                        Toast.makeText(MPPreviewActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    //Toast.makeText(MPPreviewActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
                    z = "No internet connection";
                    //finish();
                }

            return z;

        }
    }*/

    protected void getModelPaper()
    {
        requestQueue = Volley.newRequestQueue(MPPreviewActivity.this);

        VolleyModelPaperApi apiRequest = new VolleyModelPaperApi(SubjectId,Type, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("Response", response);

                try {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    downloadMaterial(response);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        requestQueue.add(apiRequest);

    }

    protected void downloadMaterial(String FileServerLocation)
    {
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(FileServerLocation);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDestinationInExternalFilesDir(getApplicationContext(),null, FileName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        Long reference = downloadManager.enqueue(request);

        Toast.makeText(MPPreviewActivity.this, "Please wait while Downloading", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onDestroy()
    {
        try {
            SharedPreferences.Editor editor = getSharedPreferences(MODEL_PAPER_PAGE_NO, MODE_PRIVATE).edit();
            editor.putInt("MP1PageNo", MP1PageNo);
            editor.putInt("MP2PageNo", MP2PageNo);
            editor.putInt("MP3PageNo", MP3PageNo);
            editor.apply();
        } catch (Exception e) {
            e.getMessage();
        }
        super.onDestroy();
    }
}
