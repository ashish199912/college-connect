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
import com.ior.cmreclh.api.VolleyChangePasswordApi;

/**
 * Created by Abhishek Jha on 28-03-2017 as ${PACKAGE_NAME}.
 */

public class ChangePasswordActivity extends AppCompatActivity {

    SQLdbClass helper;
    SQLiteDatabase SQdb;
    Cursor cursor;
    ArrayList<String> al;
    SQLiteStatement SQstmt;

    TextView txtSuccess;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    EditText txtCurrent,txtNew,txtConfirm;
    Button btnUpdate;
    String RollNo,CurrentPassword,NewPassword;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        RollNo = getIntent().getExtras().getString("RollNo");

        txtCurrent=(EditText)findViewById(R.id.pwd_current);
        txtNew=(EditText)findViewById(R.id.pwd_new);
        txtConfirm=(EditText)findViewById(R.id.pwd_confirm);

        btnUpdate=(Button)findViewById(R.id.pwd_btnUpdate);

        txtSuccess=(TextView)findViewById(R.id.pwd_txtSuccess);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

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
    }

    protected void validate()
    {
        if(txtCurrent.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Enter Current Password", Toast.LENGTH_LONG).show();
        }else if(txtNew.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Enter New Password", Toast.LENGTH_LONG).show();
        }else if(txtConfirm.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Confirm New Password", Toast.LENGTH_LONG).show();
        }else if(!txtNew.getText().toString().trim().equals(txtConfirm.getText().toString().trim())){
            Toast.makeText(this,"Password doesn't match", Toast.LENGTH_LONG).show();
        } else {

            CurrentPassword = txtCurrent.getText().toString().trim();
            NewPassword = txtNew.getText().toString().trim();

            if (checkNetworkConnection()) {
                progressDialog = new ProgressDialog(ChangePasswordActivity.this);
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                updatePWD();
            } else
                Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    protected void updatePWD()
    {
        requestQueue = Volley.newRequestQueue(ChangePasswordActivity.this);

        VolleyChangePasswordApi apiRequest = new VolleyChangePasswordApi(RollNo,CurrentPassword,NewPassword, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("response", response);

                try {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (response.equals("Success")){
                        txtSuccess.setText(getResources().getString(R.string.password_changed_success));
                        localStorageData();
                    }
                    else
                        txtSuccess.setText(getResources().getString(R.string.password_changed_failed));

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
            String query="update StudentDetails set Password='" + NewPassword + "' where RollNo='" + RollNo + "' and Password='" + CurrentPassword + "'" ;
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
