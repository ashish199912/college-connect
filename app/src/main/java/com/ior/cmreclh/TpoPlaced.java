package com.ior.cmreclh;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.ior.cmreclh.api.VolleyTPOCompanyApi;
import com.ior.cmreclh.api.VolleyTPOPlacedApi;

import org.json.JSONArray;
import org.json.JSONObject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.google.android.gms.ads.AdRequest.Builder;
import static com.ior.cmreclh.R.id;
import static com.ior.cmreclh.R.id.grid_students;
import static com.ior.cmreclh.R.layout;
import static com.ior.cmreclh.R.layout.tpo_placed;
import static com.ior.cmreclh.R.string;
import static com.ior.cmreclh.R.string.cmrec_ad_mob_Interstitial;


/**
 * Created by Abhishek Jha on 26.06.2017.
 */

public class TpoPlaced extends AppCompatActivity
{
    GridView gridView;

    SQLiteStatement SQstmt;
    SQLdbClass helper;
    SQLiteDatabase SQdb;
    Cursor cursor;
    ArrayList<String> al;
    LinearLayout llPlaced,llProgress;

    private AdView adView;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    InterstitialAd interstitialAd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(tpo_placed);


        gridView = (GridView) findViewById(grid_students);
        llProgress = (LinearLayout) findViewById(id.llProgress);
        llPlaced = (LinearLayout) findViewById(id.llPlaced);


        SimpleAdapter adapter = getFromLocal();
        gridView.setAdapter(adapter);

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

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(cmrec_ad_mob_Interstitial));

        AdRequest IadRequest = new Builder().build();
        /*AdRequest IadRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .addTestDevice("894C56D512CCCEB28D02522FAFBD0F53")  // My Galaxy Nexus test phone
                .build();*/
        interstitialAd.loadAd(IadRequest);
    }

    protected void getFromServer()
    {
        /*getDataServer get = new getDataServer();// this is the Asynctask, which is used to process in background to reduce load on app process
        get.execute("");*/

        if (checkNetworkConnection()) {

            progressDialog = new ProgressDialog(TpoPlaced.this);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Fetching placement details \n please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            requestQueue = Volley.newRequestQueue(TpoPlaced.this);

            VolleyTPOPlacedApi apiRequest = new VolleyTPOPlacedApi(new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i("Response", response);

                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("Placement");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonPlaced = (JSONObject) jsonArray.get(i);

                            SQstmt = SQdb.compileStatement("insert into Placements values(?,?,?,?,?)");
                            SQstmt.bindString(1, jsonPlaced.getString("Year"));
                            SQstmt.bindString(2, jsonPlaced.getString("Name"));
                            SQstmt.bindString(3, jsonPlaced.getString("RollNo"));
                            SQstmt.bindString(4, jsonPlaced.getString("Company"));
                            SQstmt.bindString(5, jsonPlaced.getString("Package"));
//                        SQstmt.bindString(6, rs.getString("Image"));
                            SQstmt.executeInsert();

                        }
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        getFromLocal();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });
            requestQueue.add(apiRequest);
        } else
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
    }

    //retrieving data from local db and displaying into the adapter
    protected SimpleAdapter getFromLocal()
    {
        helper=new SQLdbClass(getBaseContext());
        SQdb=helper.getReadableDatabase();

        List<Map<String,String>> data= null;
        data=new ArrayList<Map<String,String>>();
        SimpleAdapter simpleAdapter=null;

        try
        {
            cursor = SQdb.rawQuery("select * from Placements",null);

            if(cursor != null)
            {
                if(cursor.moveToFirst())
                {
                    do {
                        Map<String,String> datanum=new HashMap<String, String>();

                        // datanum.put("Image",cursor.getString(cursor.getColumnIndex("Image")));
                        datanum.put("Name",cursor.getString(cursor.getColumnIndex("Name")));
                        datanum.put("RollNo",cursor.getString(cursor.getColumnIndex("RollNo")));
                        datanum.put("Company",cursor.getString(cursor.getColumnIndex("Company")));
                        datanum.put("Package",cursor.getString(cursor.getColumnIndex("Package")));

                        data.add(datanum);

                    }while (cursor.moveToNext());

                    String from[] = {"Name","RollNo","Company","Package"};
                    int to[] = {R.id.tpo_name,R.id.tpo_rollno,R.id.tpo_comp,R.id.tpo_pack};
                    simpleAdapter = new SimpleAdapter(getBaseContext(), data, R.layout.tpo_grid_list, from, to);
                    gridView.setAdapter(simpleAdapter);
                }else
                {
                    getFromServer();
                    //getFromLocal();
                }
            }

        }catch (Exception e)
        {
            e.getMessage();
        }

        return simpleAdapter;

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
