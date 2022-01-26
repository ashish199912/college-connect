package com.ior.cmreclh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ior.cmreclh.api.VolleyRegisterApi;

import java.util.Calendar;

import static java.lang.String.valueOf;

/**
 * Created by Abhishek Jha on 20-01-2017.
 */

public class RegisterActivity extends AppCompatActivity {

    Button btn_login;
    LinearLayout llReg;
    TextView txtLogin;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    TextView txt_Name, txt_RollNo, txt_FatherName, txt_EmailId, txt_Phone, txt_Password, txt_RePassword;
    //for gender
    RadioGroup rdi_Gender;
    RadioButton radioGender;
    int Gender_ID;

    String db_Name, db_Phone, db_RollNo, db_FatherName, db_Gender, db_EmailId, db_Password, db_Regulation, db_Dept, db_College, db_RLE, db_StdRN;
    int db_YR, db_Year ,db_Sem, code_Dept;

    //SQdb variables
    SQLdbClass helper;
    SQLiteDatabase SQdb;
    SQLiteStatement SQstmt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        txtLogin=(TextView)findViewById(R.id.txtLogin);
        llReg=(LinearLayout) findViewById(R.id.llReg);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });

    }

    //Assigning values to variable of StudentDetails.java
    public void valueAssign(){
        StudentDetails sd=new StudentDetails(this);
    }


    public void regOnClick(View view)
    {

        txt_Name = (TextView)findViewById(R.id.reg_name);
        txt_RollNo = (TextView)findViewById(R.id.reg_rollno);
        txt_FatherName = (TextView)findViewById(R.id.reg_fname);
        rdi_Gender = (RadioGroup) findViewById(R.id.reg_gender);
        txt_EmailId = (TextView)findViewById(R.id.reg_email);
        txt_Phone = (TextView)findViewById(R.id.reg_phone);
        txt_Password = (TextView)findViewById(R.id.reg_pwd);
        txt_RePassword = (TextView)findViewById(R.id.reg_repwd);

        //for gender
        Gender_ID = rdi_Gender.getCheckedRadioButtonId();
        radioGender= (RadioButton)findViewById(Gender_ID);

        //Validation using logic
        if(TextUtils.isEmpty(txt_Name.getText().toString())) {
            txt_Name.setError("Enter name");
            txt_Name.requestFocus();
        } else if (TextUtils.isEmpty(txt_FatherName.getText().toString())) {
            txt_FatherName.setError("Enter father name");
            txt_FatherName.requestFocus();
        } else if(TextUtils.isEmpty(txt_EmailId.getText().toString())){
            txt_EmailId.setError("Enter email ID");
            txt_EmailId.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(txt_EmailId.getText().toString()).matches()){
            txt_EmailId.setError("Enter valid email ID");
            txt_EmailId.requestFocus();
        } else if(TextUtils.isEmpty(txt_Phone.getText().toString())){
            txt_Phone.setError("Enter phone no.");
            txt_Phone.requestFocus();
        } else if(TextUtils.isEmpty(txt_RollNo.getText().toString())){
            txt_RollNo.setError("Enter roll no.");
            txt_RollNo.requestFocus();
        } else if(TextUtils.isEmpty(txt_Password.getText().toString())){
            txt_Password.setError("Enter password");
            txt_Password.requestFocus();
        } else if(TextUtils.isEmpty(txt_RePassword.getText().toString())){
            txt_RePassword.setError("Re-enter password");
            txt_RePassword.requestFocus();
        } else if( !txt_RePassword.getText().toString().trim().equals(txt_Password.getText().toString().trim()) ){
            txt_RePassword.setError("Password not match");
            txt_RePassword.requestFocus();
        } else {

            db_Name = txt_Name.getText().toString();
            db_FatherName = txt_FatherName.getText().toString();
            db_Gender = radioGender.getText().toString();
            db_EmailId = txt_EmailId.getText().toString();
            db_Phone = txt_Phone.getText().toString();
            db_RollNo = txt_RollNo.getText().toString().toUpperCase();
            db_Password = txt_Password.getText().toString();

            convertToTokens();




            //call the InsertDatabase method

            //insertStudentDB();
        }
    }


    ////conversion from string to ArrayList
    //assigning splitted roll no to variables
    private void convertToTokens() {

        String rn=db_RollNo;
        //char ch[]=rn.toCharArray();
        String s1,s2,s3,s4,s5;
        s1=rn.substring(0,2).trim();
        s2=rn.substring(2,4).trim();
        s3=rn.substring(4,6).trim();
        s4=rn.substring(6,8).trim();
        s5=rn.substring(8,rn.length()).trim();



        db_YR = Integer.parseInt(s1);
        db_College = s2;
        db_RLE = s3;
        code_Dept = Integer.parseInt(s4);
        db_StdRN = s5;

        if(db_College.equalsIgnoreCase("8R"))
            verifyYear();
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage(" Sorry! \n You cannot be registered \n This app is only for the students of CMR Engineering College \n Thank You");
            builder.setCancelable(true);
            builder.setPositiveButton("Ok. I Understood.", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    //Verifying the year
    public void verifyYear()
    {

        int year=db_YR;
        int RN_YR = Integer.parseInt("20"+year);
        Calendar dt= Calendar.getInstance();
        int C_YR = dt.get(Calendar.YEAR);

        if(db_RLE.equalsIgnoreCase("1A")){

            db_Year = C_YR-RN_YR;

            if(db_Year>4){
//                Toast.makeText(this,"Invalid Roll Number", Toast.LENGTH_LONG).show();
                db_Year = 4;
            }
            else {

                if (year == 10 || year == 11 || year == 12) {
                    db_Regulation = "R09";
                } else if (year == 13 || year == 14) {
                    db_Regulation = "R13";
                } else if ((year == 15 )) {
                    db_Regulation = "R15";
                } else if(year == 16 || year > 16) {
                    db_Regulation = "R16";
                }
                getRegulation();
                insertStudentDB();
            }
        }

        else if(db_RLE.equalsIgnoreCase("5A")){

            db_Year = C_YR-RN_YR+1;
            if(db_Year>4){
//                Toast.makeText(this,"Invalid Roll Number", Toast.LENGTH_LONG).show();
                db_Year = 4;
            }
            else {

                if (year == 11 || year == 12 || year == 13) {
                    db_Regulation = "R09";
                } else if (year == 14 || year == 15) {
                    db_Regulation = "R13";
                } else if (year == 16 || year == 17 || year > 17) {
                    db_Regulation = "R15";
                }
                getRegulation();
                insertStudentDB();
            }
        }

    }

    //based on roll number year, regulation will be selected
    private void getRegulation(){
        int year=db_YR;
        int RN_YR = Integer.parseInt("20"+year);
        Calendar dt= Calendar.getInstance();
        int C_YR = dt.get(Calendar.YEAR);


        //calculating Sem based on current month
        int C_Month = dt.get(Calendar.MONTH);

        if(C_Month == 11 || C_Month == 0 || C_Month == 1 || C_Month == 2 || C_Month == 3 || C_Month == 4){
            db_Sem=2;
        } else if (C_Month == 5 || C_Month == 6 || C_Month == 7 || C_Month == 8 || C_Month == 9 || C_Month == 10 ){
            db_Sem=1;
            db_Year = db_Year+1;
        }

        //determining the dept based on roll number dept code
        if(code_Dept == 03) {
            db_Dept="MECH";
        } else if(code_Dept == 04) {
            db_Dept="ECE";
        } else if(code_Dept == 05) {
            db_Dept="CSE";
        }



    }

    //inserting into DB
    private void insertStudentDB()
    {
        /*DoDBReg doDBReg=new DoDBReg();
        doDBReg.execute("");*/
        if (checkNetworkConnection()) {
            try {
                requestQueue = Volley.newRequestQueue(RegisterActivity.this);

                progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setTitle("Please wait");
                progressDialog.setMessage("Registering you...");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                VolleyRegisterApi apiRequest = new VolleyRegisterApi(db_Name, db_FatherName, db_Gender, db_EmailId, db_Phone, db_RollNo, db_Password, db_Regulation, db_Year, db_Sem, code_Dept, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("Response", response);

                        try {

                            if (response.equals("User Already Exist")) {
                                Toast.makeText(RegisterActivity.this, "User already exist \n try logging in", Toast.LENGTH_LONG).show();
                            } else if (response.equals("Registration Successfully")) {
                                Toast.makeText(RegisterActivity.this, "Registration Successfully", Toast.LENGTH_LONG).show();

                                helper = new SQLdbClass(getBaseContext());
                                SQdb = helper.getWritableDatabase();

                                SQstmt = SQdb.compileStatement("insert into StudentDetails values(?,?,?,?,?,?,?,?,?,?,?)");
                                SQstmt.bindString(1, db_RollNo);
                                SQstmt.bindString(2, db_Name);
                                SQstmt.bindString(3, db_Regulation);
                                SQstmt.bindString(4, valueOf(db_Year));
                                SQstmt.bindString(5, valueOf(db_Sem));
                                SQstmt.bindString(6, valueOf(code_Dept));
                                SQstmt.bindString(7, db_Password);
                                SQstmt.bindString(8, db_EmailId);
                                SQstmt.bindString(9, db_Phone);
                                SQstmt.bindString(10, db_FatherName);
                                SQstmt.bindString(11, "user");
                                SQstmt.executeInsert();

                                redirectToMain();

                            } else
                                Toast.makeText(RegisterActivity.this, "Registration failed \n kindly contact admin for support", Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });
                requestQueue.add(apiRequest);
            } catch (Exception e) {
                Log.i("register", e.getMessage());
            }
        } else
            Toast.makeText(RegisterActivity.this, "Check network connection", Toast.LENGTH_SHORT).show();
    }

    public void redirectToMain(){

        LocalDbSubStorage localDbSubStorage=new LocalDbSubStorage();
        localDbSubStorage.checkServer(getBaseContext());

        if (progressDialog.isShowing())
            progressDialog.dismiss();

        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
        finish();
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
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setMessage("Do you want to go to login or quit");
        builder.setCancelable(false);
        builder.setPositiveButton("Goto Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
