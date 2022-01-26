package com.ior.cmreclh;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static android.view.View.GONE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;
import static com.google.android.gms.ads.AdRequest.Builder;
import static com.ior.cmreclh.R.id;
import static com.ior.cmreclh.R.id.pr_btnEdit;
import static com.ior.cmreclh.R.id.pr_btnPWD;
import static com.ior.cmreclh.R.id.pr_txtEmail;
import static com.ior.cmreclh.R.id.pr_txtFatherName;
import static com.ior.cmreclh.R.id.pr_txtName;
import static com.ior.cmreclh.R.id.pr_txtPhone;
import static com.ior.cmreclh.R.id.pr_txtRollNo;
import static com.ior.cmreclh.R.layout;
import static com.ior.cmreclh.R.layout.profile_details;

/**
 * Created by Abhishek Jha on 27-03-2017.
 */

public class StudentProfileViewActivity extends AppCompatActivity {

    SQLdbClass helper;
    SQLiteDatabase SQdb;
    Cursor cursor;
    ArrayList<String> al;

    Button btnEdit,btnPWD;
    TextView txtName,txtFatherName,txtEmail,txtPhone,txtRollNo;
    String RollNo;

    private AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(profile_details);


        txtName = (TextView) findViewById(pr_txtName);
        txtFatherName = (TextView) findViewById(pr_txtFatherName);
        txtEmail = (TextView) findViewById(pr_txtEmail);
        txtPhone = (TextView) findViewById(pr_txtPhone);
        txtRollNo = (TextView) findViewById(pr_txtRollNo);

        btnEdit = (Button) findViewById(pr_btnEdit);
        btnPWD = (Button) findViewById(pr_btnPWD);

        btnEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentProfileViewActivity.this, EditProfileActivity.class));
            }
        });

        btnPWD.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentProfileViewActivity.this, ChangePasswordActivity.class);
                intent.putExtra("RollNo", RollNo);
                startActivity(intent);

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

        /*StartAppSDK.init(this, "209189446", false);
        StartAppAd.disableSplash();*/

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

                        RollNo = cursor.getString(cursor.getColumnIndex("RollNo"));
                        txtRollNo.setText( cursor.getString(cursor.getColumnIndex("RollNo")));
                        txtName.setText(cursor.getString(cursor.getColumnIndex("Name")));
                        txtEmail.setText(cursor.getString(cursor.getColumnIndex("EmailId")));
                        txtFatherName.setText(cursor.getString(cursor.getColumnIndex("FatherName")));
                        txtPhone.setText(cursor.getString(cursor.getColumnIndex("PhoneNo")));


                    } while (cursor.moveToNext());

                } else {
                    Toast.makeText(this,"Error Displaying Data", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        } catch (Exception e)
        {
           // Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            finish();
        }
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

        dataInitializer();
    }

    @Override
    public void onDestroy()
    {
        if(adView != null)
            adView.destroy();
        super.onDestroy();
    }

}
