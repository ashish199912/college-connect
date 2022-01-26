package com.ior.cmreclh;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ior.cmreclh.api.VolleyEditProfileApi;

import static android.view.View.GONE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;
import static com.google.android.gms.ads.AdRequest.Builder;
import static com.ior.cmreclh.R.id;
import static com.ior.cmreclh.R.id.edt_btnUpdate;
import static com.ior.cmreclh.R.id.edt_txtEmail;
import static com.ior.cmreclh.R.id.edt_txtName;
import static com.ior.cmreclh.R.id.edt_txtPhone;
import static com.ior.cmreclh.R.id.edt_txtSuccess;
import static com.ior.cmreclh.R.layout.edit_profile;

/**
 * Created by Abhishek Jha on 27-03-2017.
 */

public class EditProfileActivity extends AppCompatActivity {

    SQLdbClass helper;
    SQLiteDatabase SQdb;
    Cursor cursor;
    ArrayList<String> al;
    SQLiteStatement SQstmt;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    Button btnUpdate;
    EditText txtName,txtEmail,txtPhone;
    TextView txtSuccess;

    String RollNo,Name,EmailId,Phone;
    private AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edit_profile);


        txtSuccess = (TextView) findViewById(edt_txtSuccess);

        txtName = (EditText) findViewById(edt_txtName);
        txtEmail = (EditText) findViewById(edt_txtEmail);
        txtPhone = (EditText) findViewById(edt_txtPhone);

        btnUpdate = (Button) findViewById(edt_btnUpdate);

        btnUpdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput();
            }
        });

        dataInitializer();

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
    }

    protected void validateInput()
    {
        if(txtName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this,"Enter Name to Proceed", Toast.LENGTH_LONG).show();
        } else if (txtEmail.getText().toString().trim().isEmpty()) {
            Toast.makeText(this,"Enter Email Id to Proceed", Toast.LENGTH_LONG).show();
        } else if (txtPhone.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Enter Phone No. to Proceed", Toast.LENGTH_LONG).show();
        } else
        {
            Name = txtName.getText().toString().trim();
            EmailId = txtEmail.getText().toString().trim();
            Phone = txtPhone.getText().toString().trim();

            if (checkNetworkConnection()) {
                progressDialog = new ProgressDialog(EditProfileActivity.this);
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                updateDB();
            } else
                Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    protected void dataInitializer()
    {
        al=new ArrayList<String>();
        helper=new SQLdbClass(this);
        SQdb=helper.getReadableDatabase();

        try
        {
            cursor=SQdb.rawQuery("select * from StudentDetails",null);
            if(cursor != null)
            {
                if(cursor.moveToFirst())
                {
                    do{

                        txtName.setText(cursor.getString(cursor.getColumnIndex("Name")));
                        txtEmail.setText(cursor.getString(cursor.getColumnIndex("EmailId")));
                        txtPhone.setText(cursor.getString(cursor.getColumnIndex("PhoneNo")));
                        RollNo=cursor.getString(cursor.getColumnIndex("RollNo"));


                    } while (cursor.moveToNext());

                } else {
                    Toast.makeText(this,"Error Loading Data", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        } catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    protected void updateDB()
    {
        requestQueue = Volley.newRequestQueue(EditProfileActivity.this);

        VolleyEditProfileApi apiRequest = new VolleyEditProfileApi(RollNo,Name,EmailId,Phone, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("response", response);
                txtSuccess.setVisibility(View.VISIBLE);

                try {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (response.equals("Success")){
                        txtSuccess.setText(getResources().getString(R.string.profile_update_success));
                        localStorageData();
                    }
                    else
                        txtSuccess.setText(getResources().getString(R.string.profile_update_failed));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        requestQueue.add(apiRequest);
    }


    protected void localStorageData()
    {
        //create database
        helper= new SQLdbClass(getBaseContext());

        //open database connection
        SQdb=helper.getWritableDatabase();



        try {
            String query="update StudentDetails set Name='" + Name + "', EmailId='" + EmailId + "', PhoneNo='" + Phone + "' where RollNo='" + RollNo + "'" ;
            SQstmt=SQdb.compileStatement(query);
            SQstmt.executeUpdateDelete();

        }catch(Exception e)
        {
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();
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

