package com.ior.cmreclh;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;


import java.util.ArrayList;

/**
 * Created by Abhishek Jha on 24-03-2017.
 */

public class DeptCseDetailsActivity extends AppCompatActivity
{
    Button btn1,btn2,btn3,btn4,btn5;
    LinearLayout ll1,ll2,ll3,ll4,ll5;
    //ListView list_faculty;

    /*SQLiteStatement SQstmt;
    SQLdbClass helper;
    SQLiteDatabase SQdb;
    Cursor cursor;
    ArrayList<String> al;*/

    private AdView adView;

    InterstitialAd interstitialAd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_cse);



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

        //list_faculty = (ListView)findViewById(R.id.list_faculty);

        //getFromLocal();


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
        }
        else
            ll5.setVisibility(View.GONE);
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

    /*protected void getFacultyList()
    {
        DeptCseDetailsActivity.getDataServer get = new DeptCseDetailsActivity.getDataServer();// this is the Asynctask, which is used to process in background to reduce load on app process
        get.execute("");
    }*/

    /*public class getDataServer extends AsyncTask<String,String,String>
    {
        String z="";
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String r) {
            getFromLocal();
        }

        @Override
        protected String doInBackground(String... params) {

            if(checkNetworkConnection()) {
                try {
                    DBClass dbClass = new DBClass();
                    Connection con = dbClass.connectionclass();

                    String query = "select Name,Designation from FacultyListCse";
                    Statement stmt = con.createStatement();

                    ResultSet rs = stmt.executeQuery(query);

                    while (rs.next()) {
                        SQstmt = SQdb.compileStatement("insert into FacultyList values(?,?)");
                        SQstmt.bindString(1, rs.getString("Name"));
                        SQstmt.bindString(2, rs.getString("Designation"));
                        SQstmt.executeInsert();

                        z = "";
                    }


                } catch (Exception e) {
                    e.getMessage();
                }
            }else
            {
                z= "No internet connection";
            }

            return z;
        }
    }*/

    //retrieving data from local db and displaying into the adapter
    /*protected SimpleAdapter getFromLocal()
    {
        helper=new SQLdbClass(getBaseContext());
        SQdb=helper.getReadableDatabase();

        List<Map<String,String>> data= null;
        data=new ArrayList<Map<String,String>>();
        SimpleAdapter simpleAdapter=null;

        try
        {
            cursor = SQdb.rawQuery("select * from FacultyList",null);

            if(cursor != null)
            {
                if(cursor.moveToFirst())
                {
                    do {
                        Map<String,String> datanum=new HashMap<String, String>();

                        // datanum.put("Image",cursor.getString(cursor.getColumnIndex("Image")));
                        datanum.put("Name",cursor.getString(cursor.getColumnIndex("Name")));
                        datanum.put("Designation",cursor.getString(cursor.getColumnIndex("Designation")));

                        data.add(datanum);

                    }while (cursor.moveToNext());

                    String from[] = {"Name","Designation"};
                    int to[] = {R.id.facName,R.id.facDesignation};
                    simpleAdapter = new SimpleAdapter(getBaseContext(), data, R.layout.list_faculty, from, to);
                    list_faculty.setAdapter(simpleAdapter);

                }else
                {
                    getFacultyList();
                    //getFromLocal();
                }
            }

        }catch (Exception e)
        {
            e.getMessage();
        }


        return simpleAdapter;

    }*/


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
