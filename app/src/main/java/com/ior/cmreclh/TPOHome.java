package com.ior.cmreclh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import static android.view.View.GONE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static com.google.android.gms.ads.AdRequest.Builder;
import static com.ior.cmreclh.R.id;
import static com.ior.cmreclh.R.layout;
import static com.ior.cmreclh.R.layout.tpo_home;


/**
 * Created by Abhishek Jha on 21.06.2017.
 */

public class TPOHome extends AppCompatActivity
{
    SQLiteStatement SQstmt;
    SQLdbClass helper;
    SQLiteDatabase SQdb;
    Cursor cursor;
    ArrayList<String> al;

    GridView grid_students;
    Button btn3,btn2,btn1;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(tpo_home);


        grid_students = (GridView) findViewById(id.grid_students);
        btn3 = (Button) findViewById(id.btn3);
        btn2 = (Button) findViewById(id.btn2);
        btn1 = (Button) findViewById(id.btn1);

        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                makeText(TPOHome.this, "Content Unavailable \n Try again later", LENGTH_LONG).show();
            }
        });

        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TPOHome.this, TpoCompany.class));
            }
        });

        btn3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TPOHome.this, TpoPlaced.class));
            }
        });


        // getFromLocal();

        adView = (AdView) findViewById(id.adView);
        AdRequest adRequest = new Builder().build();
       /* AdRequest adRequest = new AdRequest.Builder()
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


    /*protected void getFromLocal()
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

                }else
                {
                    getDataServer getDataServer = new getDataServer();
                    getDataServer.execute("");

                }
            }

        }catch (Exception e)
        {
            e.getMessage();
        }

    }
*/


    /*public class getDataServer extends AsyncTask<String,String,String>
    {
        String z="";
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(TPOHome.this, z , Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {

            if(checkNetworkConnection()) {
                try {
                    DBClass dbClass = new DBClass();
                    Connection con = dbClass.connectionclass();

                    String query = "select * from Placements";
                    Statement stmt = con.createStatement();

                    ResultSet rs = stmt.executeQuery(query);

                    while (rs.next()) {
                        SQstmt = SQdb.compileStatement("insert into Placements values(?,?,?,?,?,?)");
                        SQstmt.bindString(1, rs.getString("Year"));
                        SQstmt.bindString(2, rs.getString("Name"));
                        SQstmt.bindString(3, rs.getString("RollNo"));
                        SQstmt.bindString(4, rs.getString("Company"));
                        SQstmt.bindString(5, rs.getString("Package"));
                        SQstmt.bindString(6, rs.getString("Image"));
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
}
