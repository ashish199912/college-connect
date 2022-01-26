package com.ior.cmreclh;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import static android.view.View.GONE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static com.google.android.gms.ads.AdRequest.Builder;
import static com.ior.cmreclh.R.id;
import static com.ior.cmreclh.R.id.btnr13;
import static com.ior.cmreclh.R.layout;
import static com.ior.cmreclh.R.layout.view_memo;
import static com.ior.cmreclh.R.string;
import static com.ior.cmreclh.R.string.cmrec_ad_mob_Interstitial;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Mac on 04-08-2017.
 */

public class StudentMemoViewActivity extends AppCompatActivity {

    private AdView adView;
    InterstitialAd interstitialAd = null;

    float TotalMarks = 750,TotalMarks22 = 850, TotalMarks1yr = 1000, ObtainedMarks;
    float percentage;

    SQLdbClass helper;
    SQLiteDatabase SQdb;
    Cursor cursor,cursorInsert,cursorUpdate;
    ArrayList<String> al;
    SQLiteStatement SQstmt;

    Button btnAggregate;
    TextView txtAggregate;

    //semester buttons
    Button btnR13,btn11,btn12,btn21,btn22,btn31,btn32,btn41,btn42;

    //insert buttons
    Button btnInsertR13,btnInsert11,btnInsert12,btnInsert21,btnInsert22,btnInsert31,btnInsert32,btnInsert41,btnInsert42;

    //Total Marks
    //EditText txtTotR13,txtTot11,txtTot12,txtTot21,txtTot22,txtTot31,txtTot32,txtTot41,txtTot42;

    //Obtained Marks
    EditText txtObtR13,txtObt11,txtObt12,txtObt21,txtObt22,txtObt31,txtObt32,txtObt41,txtObt42;

    //Percentage
    TextView pR13,p11,p12,p21,p22,p31,p32,p41,p42;

    //Layouts
    LinearLayout llR13,llR16;
    LinearLayout ll1,ll11,ll12,ll21,ll22,ll31,ll32,ll41,ll42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view_memo);


        btnAggregate = (Button) findViewById(id.btnAggregate);
        txtAggregate = (TextView) findViewById(id.txtAggregate);

        llR13 = (LinearLayout) findViewById(id.llR13);
        llR16 = (LinearLayout) findViewById(id.llR16);

        ll1 = (LinearLayout) findViewById(id.ll1);
        ll11 = (LinearLayout) findViewById(id.ll11);
        ll12 = (LinearLayout) findViewById(id.ll12);
        ll21 = (LinearLayout) findViewById(id.ll21);
        ll22 = (LinearLayout) findViewById(id.ll22);
        ll31 = (LinearLayout) findViewById(id.ll31);
        ll32 = (LinearLayout) findViewById(id.ll32);
        ll41 = (LinearLayout) findViewById(id.ll41);
        ll42 = (LinearLayout) findViewById(id.ll42);

        btnR13 = (Button) findViewById(btnr13);
        btn11 = (Button) findViewById(id.btn11);
        btn12 = (Button) findViewById(id.btn12);
        btn21 = (Button) findViewById(id.btn21);
        btn22 = (Button) findViewById(id.btn22);
        btn31 = (Button) findViewById(id.btn31);
        btn32 = (Button) findViewById(id.btn32);
        btn41 = (Button) findViewById(id.btn41);
        btn42 = (Button) findViewById(id.btn42);

        btnInsertR13 = (Button) findViewById(id.btnInsertR13);
        btnInsert11 = (Button) findViewById(id.btnInsert11);
        btnInsert12 = (Button) findViewById(id.btnInsert12);
        btnInsert21 = (Button) findViewById(id.btnInsert21);
        btnInsert22 = (Button) findViewById(id.btnInsert22);
        btnInsert31 = (Button) findViewById(id.btnInsert31);
        btnInsert32 = (Button) findViewById(id.btnInsert32);
        btnInsert41 = (Button) findViewById(id.btnInsert41);
        btnInsert42 = (Button) findViewById(id.btnInsert42);

        txtObtR13 = (EditText) findViewById(id.txtObtR13);
        txtObt11 = (EditText) findViewById(id.txtObt11);
        txtObt12 = (EditText) findViewById(id.txtObt12);
        txtObt21 = (EditText) findViewById(id.txtObt21);
        txtObt22 = (EditText) findViewById(id.txtObt22);
        txtObt31 = (EditText) findViewById(id.txtObt31);
        txtObt32 = (EditText) findViewById(id.txtObt32);
        txtObt41 = (EditText) findViewById(id.txtObt41);
        txtObt42 = (EditText) findViewById(id.txtObt42);

        /*txtTotR13 = (EditText) findViewById(R.id.txtTotR13);
        txtTot11 = (EditText) findViewById(R.id.txtTot11);
        txtTot12 = (EditText) findViewById(R.id.txtTot12);
        txtTot21 = (EditText) findViewById(R.id.txtTot21);
        txtTot22 = (EditText) findViewById(R.id.txtTot22);
        txtTot31 = (EditText) findViewById(R.id.txtTot31);
        txtTot32 = (EditText) findViewById(R.id.txtTot32);
        txtTot41 = (EditText) findViewById(R.id.txtTot41);
        txtTot42 = (EditText) findViewById(R.id.txtTot42);*/

        pR13 = (TextView) findViewById(id.pR13);
        p11 = (TextView) findViewById(id.p11);
        p12 = (TextView) findViewById(id.p12);
        p21 = (TextView) findViewById(id.p21);
        p22 = (TextView) findViewById(id.p22);
        p31 = (TextView) findViewById(id.p31);
        p32 = (TextView) findViewById(id.p32);
        p41 = (TextView) findViewById(id.p41);
        p42 = (TextView) findViewById(id.p42);

        btnAggregate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                    interstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                        }
                    });
                }

                totalAggregate();
            }
        });


        btnR13.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int v = ll1.getVisibility();
                if (v == 8)
                    ll1.setVisibility(VISIBLE);
                else ll1.setVisibility(GONE);

                getLocalSemValue("1", "0", txtObtR13, pR13);
            }
        });

        btn11.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int v = ll11.getVisibility();
                if (v == 8)
                    ll11.setVisibility(VISIBLE);
                else ll11.setVisibility(GONE);

                getLocalSemValue("1", "1", txtObt11, p11);
            }
        });

        btn12.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int v = ll12.getVisibility();
                if (v == 8)
                    ll12.setVisibility(VISIBLE);
                else ll12.setVisibility(GONE);

                getLocalSemValue("1", "2", txtObt12, p12);
            }
        });

        btn21.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int v = ll21.getVisibility();
                if (v == 8)
                    ll21.setVisibility(VISIBLE);
                else ll21.setVisibility(GONE);

                getLocalSemValue("2", "1", txtObt21, p21);
            }
        });

        btn22.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int v = ll22.getVisibility();
                if (v == 8)
                    ll22.setVisibility(VISIBLE);
                else ll22.setVisibility(GONE);

                getLocalSemValue("2", "2", txtObt22, p22);
            }
        });

        btn31.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int v = ll31.getVisibility();
                if (v == 8)
                    ll31.setVisibility(VISIBLE);
                else ll31.setVisibility(GONE);

                getLocalSemValue("3", "1", txtObt31, p31);
            }
        });

        btn32.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int v = ll32.getVisibility();
                if (v == 8)
                    ll32.setVisibility(VISIBLE);
                else ll32.setVisibility(GONE);

                getLocalSemValue("3", "2", txtObt32, p32);
            }
        });

        btn41.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int v = ll41.getVisibility();
                if (v == 8)
                    ll41.setVisibility(VISIBLE);
                else ll41.setVisibility(GONE);

                getLocalSemValue("4", "1", txtObt41, p41);
            }
        });

        btn42.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int v = ll42.getVisibility();
                if (v == 8)
                    ll42.setVisibility(VISIBLE);
                else ll42.setVisibility(GONE);

                getLocalSemValue("4", "2", txtObt42, p42);
            }
        });

        btnInsertR13.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ObtainedMarks = parseInt(txtObtR13.getText().toString().trim());
                    percentage = (ObtainedMarks / TotalMarks1yr) * 100;
                    pR13.setText(valueOf(percentage));
                    insertDB(1, 0, TotalMarks);
                } catch (Exception e) {
                    makeText(StudentMemoViewActivity.this, "try again later", LENGTH_LONG).show();
                }
            }
        });

        btnInsert11.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    ObtainedMarks = parseInt(txtObt11.getText().toString().trim());
                    percentage = (ObtainedMarks / TotalMarks) * 100;
                    p11.setText(valueOf(percentage));
                    insertDB(1, 1, TotalMarks);
                } catch (Exception e) {
                    makeText(StudentMemoViewActivity.this, "try again later", LENGTH_LONG).show();
                }
            }
        });

        btnInsert12.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    ObtainedMarks = parseInt(txtObt12.getText().toString().trim());
                    percentage = (ObtainedMarks / TotalMarks) * 100;
                    p12.setText(valueOf(percentage));
                    insertDB(1, 2, TotalMarks);
                } catch (Exception e) {
                    makeText(StudentMemoViewActivity.this, "try again later", LENGTH_LONG).show();
                }
            }
        });

        btnInsert21.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    ObtainedMarks = parseInt(txtObt21.getText().toString().trim());
                    percentage = (ObtainedMarks / TotalMarks) * 100;
                    p21.setText(valueOf(percentage));
                    insertDB(2, 1, TotalMarks);
                } catch (Exception e) {
                    makeText(StudentMemoViewActivity.this, "try again later", LENGTH_LONG).show();
                }
            }
        });

        btnInsert22.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    ObtainedMarks = parseInt(txtObt22.getText().toString().trim());
                    percentage = (ObtainedMarks / TotalMarks22) * 100;
                    p22.setText(valueOf(percentage));
                    insertDB(2, 2, TotalMarks22);
                } catch (Exception e) {
                    makeText(StudentMemoViewActivity.this, "try again later", LENGTH_LONG).show();
                }
            }
        });

        btnInsert31.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    ObtainedMarks = parseInt(txtObt31.getText().toString().trim());
                    percentage = (ObtainedMarks / TotalMarks) * 100;
                    p31.setText(valueOf(percentage));
                    insertDB(3, 1, TotalMarks);
                } catch (Exception e) {
                    makeText(StudentMemoViewActivity.this, "try again later", LENGTH_LONG).show();
                }
            }
        });

        btnInsert32.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    ObtainedMarks = parseInt(txtObt32.getText().toString().trim());
                    percentage = (ObtainedMarks / TotalMarks) * 100;
                    p32.setText(valueOf(percentage));
                    insertDB(3, 2, TotalMarks);
                } catch (Exception e) {
                    makeText(StudentMemoViewActivity.this, "try again later", LENGTH_LONG).show();
                }
            }
        });

        btnInsert41.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    ObtainedMarks = parseInt(txtObt41.getText().toString().trim());
                    percentage = (ObtainedMarks / TotalMarks) * 100;
                    p41.setText(valueOf(percentage));
                    insertDB(4, 1, TotalMarks);
                } catch (Exception e) {
                    makeText(StudentMemoViewActivity.this, "try again later", LENGTH_LONG).show();
                }
            }
        });

        btnInsert42.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    ObtainedMarks = parseInt(txtObt42.getText().toString().trim());
                    percentage = (ObtainedMarks / TotalMarks) * 100;
                    p42.setText(valueOf(percentage));
                    insertDB(4, 2, TotalMarks);
                } catch (Exception e) {
                    makeText(StudentMemoViewActivity.this, "try again later", LENGTH_LONG).show();
                }
            }
        });

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

    protected void insertDB(int Year, int Sem, float TotalMarksSem)
    {
        if (ObtainedMarks > TotalMarks22 && (Year ==2 && Sem == 2))
        {
            Toast.makeText(this, "Invalid Marks", Toast.LENGTH_LONG).show();
        } else if (ObtainedMarks > TotalMarks1yr && (Year == 1 & Sem == 0))
        {
            Toast.makeText(this, "Invalid Marks", Toast.LENGTH_LONG).show();
        } else if (((ObtainedMarks > TotalMarks) && !(ObtainedMarks > TotalMarks && (Year == 2 && Sem == 2))) || ((ObtainedMarks > TotalMarks) && !(ObtainedMarks > TotalMarks && (Year == 1 && Sem == 0))))
        {
            Toast.makeText(this, "Invalid Marks", Toast.LENGTH_LONG).show();
        } else {
            float Total = TotalMarksSem, Obtained = ObtainedMarks, Percentage = percentage;
            String YearSem = valueOf(Year) + valueOf(Sem);

            al = new ArrayList<String>();
            helper = new SQLdbClass(this);
            SQdb = helper.getReadableDatabase();

            try {
                cursor = SQdb.rawQuery("select * from Percentage where YearSem='" + YearSem + "'", null);
                if (cursor != null) {
                    SQdb = helper.getWritableDatabase();
                    if (cursor.moveToFirst()) {
                        SQstmt = SQdb.compileStatement("update Percentage set Total=" + Total + ", Obtained=" + Obtained + ", Aggregate=" + Percentage + " where YearSem='" + YearSem + "'");
                        int i = SQstmt.executeUpdateDelete();
                        if (i >= 1)
                            Toast.makeText(this, "Updated Successfully", Toast.LENGTH_LONG).show();
                    } else {
                        SQstmt = SQdb.compileStatement("insert into Percentage values(?,?,?,?,?,?)");
                        SQstmt.bindString(1, YearSem);
                        SQstmt.bindString(2, valueOf(Year));
                        SQstmt.bindString(3, valueOf(Sem));
                        SQstmt.bindString(4, valueOf(Total));
                        SQstmt.bindString(5, valueOf(Obtained));
                        SQstmt.bindString(6, valueOf(Percentage));
                        SQstmt.executeInsert();
                    }
                }
            } catch (Exception e) {
                Toast.makeText(this, "Insert/Update Failed. Try Again", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void totalAggregate()
    {
        float TotalAggregate=0;
        int count=0;
        float TotalPercentage;

        al=new ArrayList<String>();
        helper=new SQLdbClass(this);
        SQdb=helper.getReadableDatabase();

        try
        {
            cursor=SQdb.rawQuery("select Aggregate from Percentage",null);
            if(cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        TotalAggregate = TotalAggregate + Float.parseFloat(cursor.getString(cursor.getColumnIndex("Aggregate")));
                        count++;
                    }while (cursor.moveToNext());
                    TotalPercentage = TotalAggregate / count;
                    txtAggregate.setText(valueOf(TotalPercentage));
                }
            }
        }catch (Exception e)
        {
            Toast.makeText(this,"Try again later",Toast.LENGTH_LONG).show();
        }

    }

    protected void initialize(int Year, int Sem, float TotalMarks)
    {
        float SemAggregate,Obtained;

        al=new ArrayList<String>();
        helper=new SQLdbClass(this);
        SQdb=helper.getReadableDatabase();

        try
        {
            cursor=SQdb.rawQuery("select Aggregate from Percentage",null);
            if(cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        Obtained = Float.parseFloat(cursor.getString(cursor.getColumnIndex("Aggregate")));
                    }while (cursor.moveToNext());
                    SemAggregate = Obtained / TotalMarks;
                    txtAggregate.setText(valueOf(SemAggregate));
                }
            }
        }catch (Exception e)
        {
            Toast.makeText(this,"Try again later",Toast.LENGTH_LONG).show();
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

    protected void getLocalSemValue(String Year, String Sem, EditText txtObtained, TextView txtAggregate)
    {
        float LocalObtained,LocalAggregate;
        String YearSem= Year+Sem;

        al=new ArrayList<String>();
        helper=new SQLdbClass(this);
        SQdb=helper.getReadableDatabase();

        try
        {
            cursor=SQdb.rawQuery("select Aggregate,Obtained from Percentage where YearSem='"+YearSem+"'",null);
            if(cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        LocalObtained = Float.parseFloat(cursor.getString(cursor.getColumnIndex("Obtained")));
                        LocalAggregate = Float.parseFloat(cursor.getString(cursor.getColumnIndex("Aggregate")));
                    }while (cursor.moveToNext());

                    txtObtained.setText(valueOf(LocalObtained));
                    txtAggregate.setText(valueOf(LocalAggregate));
                }
            }
        }catch (Exception e)
        {
            Toast.makeText(this,"Try again later",Toast.LENGTH_LONG).show();
        }

    }
}
