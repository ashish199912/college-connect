package com.ior.cmreclh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ior.cmreclh.api.VolleyLoginApi;

import org.json.JSONObject;

/**
 * Created by Abhishek Jha on 20-01-2017.
 */

public class LoginActivity extends AppCompatActivity {

//    ProgressBar progressBar;
//    LinearLayout llLogin;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    // Declaring layout button, edit texts
    Button btnLogin,btnRegister,btnForgetPassword;
//    Button btnDefaultLogin;
    EditText Login_Username,Login_Password;

    // Declaring SQLite database variables
    SQLdbClass helper;
    SQLiteDatabase SQdb;
    SQLiteStatement SQstmt;

    //SQLite local variables
    String RollNo,Name,Regulation,Year,Sem,FatherName,Gender,DeptNo,Phone,EmailId,Type;
    String username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Getting values from button, texts and progress bar
        btnLogin = (Button) findViewById(R.id.login_btnLogin);
//        btnDefaultLogin = (Button) findViewById(R.id.login_btnDefaultLogin);
        btnRegister = (Button) findViewById(R.id.login_btnRegister);
        btnForgetPassword = (Button) findViewById(R.id.login_btnForgetPassword);

        Login_Username = (EditText) findViewById(R.id.login_userName);
        Login_Password = (EditText) findViewById(R.id.login_password);

        requestQueue = Volley.newRequestQueue(LoginActivity.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = Login_Username.getText().toString().toUpperCase();
                password = Login_Password.getText().toString();

                if(TextUtils.isEmpty(username)){
                    Login_Username.setError("Enter Roll No.");
                    Login_Username.requestFocus();
                } else if (TextUtils.isEmpty(password)){
                    Login_Password.setError("Enter password");
                    Login_Password.requestFocus();
                } else if (checkNetworkConnection()){

                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Please wait");
                    progressDialog.setMessage("Logging you in...");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    VolleyLoginApi apiRequest = new VolleyLoginApi(username, password, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.i("Response", response);

                            try {

                                JSONObject jsonObject = new JSONObject(response);

                                if (jsonObject.getBoolean("success")) {

                                    RollNo = jsonObject.getString("RollNo");
                                    Name = jsonObject.getString("Name");
                                    Regulation = jsonObject.getString("Regulation");
                                    Year = jsonObject.getString("Year");
                                    Sem = jsonObject.getString("Sem");
                                    FatherName = jsonObject.getString("FatherName");
                                    Gender = jsonObject.getString("Gender");
                                    DeptNo = jsonObject.getString("DeptNo");
                                    Phone = jsonObject.getString("Phone");
                                    EmailId = jsonObject.getString("EmailId");
                                    Type = jsonObject.getString("Type");

                                    localStorageData();
                                    redirectToMain();

                                } else {
                                    if (progressDialog.isShowing())
                                        progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "User details not found", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                                e.printStackTrace();
                            }
                        }

                    });
                    requestQueue.add(apiRequest);

                } else {
                    Toast.makeText(LoginActivity.this, "Check network connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
            }
        });

    }


    public void redirectToMain(){

        LocalDbSubStorage localDbSubStorage=new LocalDbSubStorage();
        localDbSubStorage.checkServer(getBaseContext());

        if (progressDialog.isShowing())
            progressDialog.dismiss();

        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }


    private void localStorageData()
    {
        //create database
        helper= new SQLdbClass(getBaseContext());

        //open database connection
        SQdb=helper.getWritableDatabase();



        try {
            SQstmt=SQdb.compileStatement("insert into StudentDetails values(?,?,?,?,?,?,?,?,?,?,?)");
            SQstmt.bindString(1,RollNo);
            SQstmt.bindString(2,Name);
            SQstmt.bindString(3,Regulation);
            SQstmt.bindString(4, String.valueOf(Year));
            SQstmt.bindString(5, String.valueOf(Sem));
            SQstmt.bindString(6,DeptNo);
            SQstmt.bindString(8,EmailId);
            SQstmt.bindString(9,Phone);
            SQstmt.bindString(10,FatherName);
            SQstmt.bindString(11,Type);
            SQstmt.executeInsert();

        }catch(Exception e)
        {
            Log.i("login", e.getMessage());
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


