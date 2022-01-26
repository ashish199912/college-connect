package com.ior.cmreclh;

import android.app.ProgressDialog;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ior.cmreclh.api.VolleyChangeRegulationApi;
import com.ior.cmreclh.api.VolleyTPOPlacedApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.String.valueOf;

/**
 * Created by Abhishek Jha on 09.02.2018.
 */

public class RegulationSelectorActivity extends AppCompatActivity {

    Button btnReg, btnYear, btnSem;
    LinearLayout llReg, llYear, llSem;
    Button btnR13, btnR15, btnR16;
    Button btn1yr, btn2yr, btn3yr, btn4yr;
    Button btn1sem, btn2sem;
    Button btnProceed, btnReset;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    SQLdbClass helper;
    SQLiteDatabase SQdb;
    Cursor cursor;
    ArrayList<String> al;
    SQLiteStatement SQstmt;

    String Regulation,RollNo;
    int Year, Sem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regulation_selector);

        btnReg = (Button) findViewById(R.id.btnReg);
        btnYear = (Button) findViewById(R.id.btnYear);
        btnSem = (Button) findViewById(R.id.btnSem);

        llReg = (LinearLayout) findViewById(R.id.llReg);
        llYear = (LinearLayout) findViewById(R.id.llYear);
        llSem = (LinearLayout) findViewById(R.id.llSem);

        btnR13 = (Button) findViewById(R.id.btnR13);
        btnR15 = (Button) findViewById(R.id.btnR15);
        btnR16 = (Button) findViewById(R.id.btnR16);

        btn1yr = (Button) findViewById(R.id.btn1yr);
        btn2yr = (Button) findViewById(R.id.btn2yr);
        btn3yr = (Button) findViewById(R.id.btn3yr);
        btn4yr = (Button) findViewById(R.id.btn4yr);

        btn1sem = (Button) findViewById(R.id.btn1sem);
        btn2sem = (Button) findViewById(R.id.btn2sem);

        btnProceed = (Button) findViewById(R.id.btnProceed);
        btnReset = (Button) findViewById(R.id.btnReset);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int v=llReg.getVisibility();
                if(v==8) {
                    llReg.setVisibility(View.VISIBLE);
                } else
                    llReg.setVisibility(View.GONE);
            }
        });

        btnYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int v=llYear.getVisibility();
                if(v==8) {
                    llYear.setVisibility(View.VISIBLE);
                } else
                    llYear.setVisibility(View.GONE);
            }
        });

        btnSem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int v=llSem.getVisibility();
                if(v==8) {
                    llSem.setVisibility(View.VISIBLE);
                } else
                    llSem.setVisibility(View.GONE);
            }
        });

        /*==========Selecting Regulation===========*/
        btnR13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Regulation = "R13";
                btnR15.setEnabled(false);
                btnR16.setEnabled(false);
            }
        });

        btnR15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Regulation = "R15";
                btnR13.setEnabled(false);
                btnR16.setEnabled(false);
            }
        });

        btnR16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Regulation = "R16";
                btnR13.setEnabled(false);
                btnR15.setEnabled(false);
            }
        });


        /*============Selecting Year===================*/
        btn1yr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Year = 1;
                btn2yr.setEnabled(false);
                btn3yr.setEnabled(false);
                btn4yr.setEnabled(false);
            }
        });

        btn2yr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Year = 2;
                btn1yr.setEnabled(false);
                btn3yr.setEnabled(false);
                btn4yr.setEnabled(false);
            }
        });

        btn3yr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Year = 3;
                btn1yr.setEnabled(false);
                btn2yr.setEnabled(false);
                btn4yr.setEnabled(false);
            }
        });

        btn4yr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Year = 4;
                btn1yr.setEnabled(false);
                btn2yr.setEnabled(false);
                btn3yr.setEnabled(false);
            }
        });

        /*===============Selecting Sem====================*/
        btn1sem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sem = 1;
                btn2sem.setEnabled(false);
            }
        });

        btn2sem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sem = 2;
                btn1sem.setEnabled(false);
            }
        });

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(Regulation) || TextUtils.isEmpty(valueOf(Year)) || TextUtils.isEmpty(valueOf(Sem))) {
                    Toast.makeText(RegulationSelectorActivity.this, "First select valid details", Toast.LENGTH_LONG).show();
                } else {
                    getRollNo();
                    dbUpdate();
                }

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnR13.setEnabled(true);
                btnR15.setEnabled(true);
                btnR16.setEnabled(true);

                btn1yr.setEnabled(true);
                btn2yr.setEnabled(true);
                btn3yr.setEnabled(true);
                btn4yr.setEnabled(true);

                btn1sem.setEnabled(true);
                btn2sem.setEnabled(true);
            }
        });
    }


    //updating the user details into db
    /*protected void dbUpdate()
    {
        class doUpdate extends AsyncTask<String,Void,String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                progressDialog = new ProgressDialog(RegulationSelectorActivity.this);
                progressDialog.setMessage("Please wait... \n While Updating...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(String httpResponseMsg)
            {
                super.onPostExecute(httpResponseMsg);
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                Toast.makeText(RegulationSelectorActivity.this, RollNo, Toast.LENGTH_LONG).show();

                //Toast.makeText(RegisterActivity.this, httpResponseMsg, Toast.LENGTH_LONG).show();

                if(httpResponseMsg.equalsIgnoreCase("Updated Successfully")){
                    Toast.makeText(RegulationSelectorActivity.this, "Updated Successfully", Toast.LENGTH_LONG).show();
                    localStorageData();

                    //retrieving and initializing the subjects to local db from server
                    GetSubjects getSubjects = new GetSubjects(getBaseContext());
                    getSubjects.getFromServer();

                    startActivity(new Intent(RegulationSelector.this, MainActivity.class));
                    finish();
                } else  if (httpResponseMsg.equalsIgnoreCase("update failed")){
                    Toast.makeText(RegulationSelector.this, "Updation Failed \n Contact For Support", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(RegulationSelector.this, "Updation Failed \n Contact For Support", Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... strings) {

                hashMap.put("rollno",RollNo);
                hashMap.put("regulation",Regulation);
                hashMap.put("year",valueOf(Year));
                hashMap.put("sem",valueOf(Sem));

                finalResult = httpParse.postRequest(hashMap, HttpUrl);

                return finalResult;
            }
        }

        doUpdate update = new doUpdate();
        update.execute();
    }*/

    protected void dbUpdate()
    {
        if (checkNetworkConnection())
        {
            try
            {
                progressDialog = new ProgressDialog(RegulationSelectorActivity.this);
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Updating details \n please wait...");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();


                requestQueue = Volley.newRequestQueue(RegulationSelectorActivity.this);

                VolleyChangeRegulationApi apiRequest = new VolleyChangeRegulationApi(RollNo,Regulation,valueOf(Year),valueOf(Sem),new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                    Log.i("Response", response);

                    if (response.equals("Updated Successfully")) {
                        localStorageData();

                        SQstmt=SQdb.compileStatement("delete from Subjects");
                        SQstmt.executeUpdateDelete();

                        LocalDbSubStorage localDbSubStorage = new LocalDbSubStorage();
                        localDbSubStorage.checkLocal(getBaseContext());

                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                        Toast.makeText(RegulationSelectorActivity.this, "Details updated successfully", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(RegulationSelectorActivity.this, MainActivity.class));
                        finish();


                    } else {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(RegulationSelectorActivity.this, "Error while updating \n try contacting for support", Toast.LENGTH_SHORT).show();
                    }
                    }

                });
                requestQueue.add(apiRequest);

            } catch (Exception e) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Log.i("regulation", e.getMessage());
            }
        } else
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
    }


    //retrieving roll number from local db
    private void getRollNo()
    {

        al=new ArrayList<String>();
        helper=new SQLdbClass(this);
        SQdb=helper.getReadableDatabase();

        try
        {
            cursor=SQdb.rawQuery("select RollNo from StudentDetails",null);
            if(cursor != null)
            {
                if(cursor.moveToFirst())
                {
                    do{

                        RollNo=cursor.getString(cursor.getColumnIndex("RollNo"));

                    } while (cursor.moveToNext());

                } else {
                    startActivity(new Intent(this,LoginActivity.class));
                    finish();
                }
            }
        } catch (Exception e)
        {
            // Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    //updating student details in local db
    protected void localStorageData()
    {
        //create database
        helper= new SQLdbClass(getBaseContext());

        //open database connection
        SQdb=helper.getWritableDatabase();

        try {
            String query="update StudentDetails set Regulation='" + Regulation + "', Year='" + Year + "', Sem='" + Sem + "' where RollNo='" + RollNo + "'" ;
            SQstmt=SQdb.compileStatement(query);
            int i=SQstmt.executeUpdateDelete();

        }catch(Exception e)
        {
            //Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();
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
