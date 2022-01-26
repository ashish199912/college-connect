package com.ior.cmreclh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Abhishek Jha on 14.10.2017.
 */

public class DefaultUsersDialog extends AppCompatActivity {

    Button btnReg,btnBranch,btnYear,btnSem;
    LinearLayout llReg,llBranch,llYear,llSem;
    Button btnR13,btnR15,btnR16;
    Button btnCSE,btnECE,btnMECH;
    Button btn1yr,btn2yr,btn3yr,btn4yr;
    Button btn1sem,btn2sem;
    Button btnLogin,btnReset;

    String Reg,CC="8R",Regu="1A",Branch,Roll;
    int Year;
    String RollNo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_users);

        btnReg = (Button) findViewById(R.id.btnReg);
        btnBranch = (Button) findViewById(R.id.btnBranch);
        btnYear = (Button) findViewById(R.id.btnYear);
        btnSem = (Button) findViewById(R.id.btnSem);

        llReg = (LinearLayout) findViewById(R.id.llReg);
        llBranch = (LinearLayout) findViewById(R.id.llBranch);
        llYear = (LinearLayout) findViewById(R.id.llYear);
        llSem = (LinearLayout) findViewById(R.id.llSem);

        btnR13 = (Button) findViewById(R.id.btnR13);
        btnR15 = (Button) findViewById(R.id.btnR15);
        btnR16 = (Button) findViewById(R.id.btnR16);

        btnCSE = (Button) findViewById(R.id.btnCSE);
        btnECE = (Button) findViewById(R.id.btnECE);
        btnMECH = (Button) findViewById(R.id.btnMECH);

        btn1yr = (Button) findViewById(R.id.btn1yr);
        btn2yr = (Button) findViewById(R.id.btn2yr);
        btn3yr = (Button) findViewById(R.id.btn3yr);
        btn4yr = (Button) findViewById(R.id.btn4yr);

        btn1sem = (Button) findViewById(R.id.btn1sem);
        btn2sem = (Button) findViewById(R.id.btn2sem);

        btnLogin = (Button) findViewById(R.id.btnLogin);
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

        btnBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int v=llBranch.getVisibility();
                if(v==8) {
                    llBranch.setVisibility(View.VISIBLE);
                } else
                    llBranch.setVisibility(View.GONE);
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
                Reg = "14";
                btnR15.setEnabled(false);
                btnR16.setEnabled(false);
            }
        });

        btnR15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reg = "15";
                btnR13.setEnabled(false);
                btnR16.setEnabled(false);
            }
        });

        btnR16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reg = "16";
                btnR13.setEnabled(false);
                btnR15.setEnabled(false);
            }
        });

        /*==========Selecting Branch===========*/
        btnCSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Branch = "05";
                btnECE.setEnabled(false);
                btnMECH.setEnabled(false);
            }
        });

        btnECE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Branch = "03";
                btnCSE.setEnabled(false);
                btnMECH.setEnabled(false);
            }
        });

        btnMECH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Branch = "04";
                btnCSE.setEnabled(false);
                btnECE.setEnabled(false);
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
                if(Year == 1)
                    Roll = "01";
                else if (Year == 2)
                    Roll = "03";
                else if (Year == 3)
                    Roll = "05";
                else if (Year == 4)
                    Roll = "07";

                RollNo = Reg + CC  + Regu + Branch + Roll;

                btn2sem.setEnabled(false);
            }
        });

        btn2sem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Year == 1)
                    Roll = "02";
                else if (Year == 2)
                    Roll = "04";
                else if (Year == 3)
                    Roll = "06";
                else if (Year == 4)
                    Roll = "08";

                RollNo = Reg + CC  + Regu + Branch + Roll;

                btn1sem.setEnabled(false);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(0, RollNo);


                Intent intent = new Intent(DefaultUsersDialog.this, LoginActivity.class);
                intent.putStringArrayListExtra("RollNoData", arrayList);
                startActivity(intent);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnR15.setEnabled(true);
                btnR15.setEnabled(true);
                btnR16.setEnabled(true);

                btnCSE.setEnabled(true);
                btnECE.setEnabled(true);
                btnMECH.setEnabled(true);

                btn1yr.setEnabled(true);
                btn2yr.setEnabled(true);
                btn3yr.setEnabled(true);
                btn4yr.setEnabled(true);

                btn1sem.setEnabled(true);
                btn2sem.setEnabled(true);
            }
        });
    }


}
