package com.ior.cmreclh;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ior.cmreclh.api.VolleyModelPaperApi;
import com.ior.cmreclh.api.VolleyResetPasswordApi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Abhishek Jha on 08-04-2017.
 */

public class ResetPasswordActivity extends AppCompatActivity {

    Button btnReset;
    EditText txtRollNo;
    ProgressDialog progressDialog;
    TextView txtSuccess;

    RequestQueue requestQueue;

    String RollNo,Date;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_pwd);

        btnReset=(Button)findViewById(R.id.rs_btnReset);
        txtRollNo=(EditText)findViewById(R.id.rs_rollno);
        txtSuccess=(TextView)findViewById(R.id.rs_txtSuccess);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(txtRollNo.getText().toString())) {
                    Toast.makeText(ResetPasswordActivity.this, "Enter Roll Number to continue", Toast.LENGTH_SHORT).show();
                } else
                {

                    RollNo = txtRollNo.getText().toString().trim().toUpperCase();
                    Date = getDate();

                    if (checkNetworkConnection()) {
                        progressDialog = new ProgressDialog(ResetPasswordActivity.this);
                        progressDialog.setTitle("Loading");
                        progressDialog.setMessage("please wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                        sendEmail();
                    } else
                        Toast.makeText(ResetPasswordActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected String getDate()
    {
         return DateFormat.getDateTimeInstance().format(new Date());
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

    protected void sendEmail()
    {
        requestQueue = Volley.newRequestQueue(ResetPasswordActivity.this);

        VolleyResetPasswordApi apiRequest = new VolleyResetPasswordApi(RollNo,Date, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("Response", response);

                try {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.equals("Success"))
                        txtSuccess.setText("Password Change Request Sent \n Please wait for the Email Response");
                     else if (response.equals("Invalid roll number"))
                        txtSuccess.setText("Invalid Roll Number \n try again");
                     else
                        txtSuccess.setText("Unable to process \n try again later");


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        requestQueue.add(apiRequest);
    }
}
