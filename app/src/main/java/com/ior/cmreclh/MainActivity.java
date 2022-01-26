package com.ior.cmreclh;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;
import static com.google.android.gms.ads.AdRequest.Builder;
import static com.google.android.gms.ads.MobileAds.initialize;
import static com.ior.cmreclh.R.id;
import static com.ior.cmreclh.R.id.btnModerPaper;
import static com.ior.cmreclh.R.id.drawer_layout;
import static com.ior.cmreclh.R.id.nav_view;
import static com.ior.cmreclh.R.id.txt_Regulation;
import static com.ior.cmreclh.R.id.txt_Sem;
import static com.ior.cmreclh.R.id.txt_Year;
import static com.ior.cmreclh.R.layout.activity_main;
import static com.ior.cmreclh.R.string.cmrec_app_id;
import static com.ior.cmreclh.R.string.navigation_drawer_close;
import static com.ior.cmreclh.R.string.navigation_drawer_open;
import static java.lang.String.valueOf;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.crash.FirebaseCrash;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView txtReg,txtYear,txtSem;
    SQLdbClass helper;
    SQLiteDatabase SQdb;
    Cursor cursor;
    ArrayList<String> al;

    CardView card_view_syllabus,card_view_materials,card_view_model_papers,card_view_updates;

    String Name,RollNo;
    String Regulation;
    int Year,Sem,DeptNo;
    Button btnSyllabus,btnMaterial,btnModelPaper,btnUpdates;
    String Type;

    Boolean logout = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnSyllabus = (Button) findViewById(id.btnSyllabus);
        btnMaterial = (Button) findViewById(id.btnMaterial);
        btnModelPaper = (Button) findViewById(btnModerPaper);
        btnUpdates = (Button) findViewById(id.btnUpdates);

        card_view_syllabus = (CardView) findViewById(id.card_view_syllabus);
        card_view_materials = (CardView) findViewById(id.card_view_materials);
        card_view_model_papers = (CardView) findViewById(id.card_view_model_papers);
        card_view_updates = (CardView) findViewById(id.card_view_updates);

        card_view_syllabus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                String LocalYear, LocalSem;

                if (Regulation.equals("R13") && Year == 1) {
                    LocalYear = "0";
                    LocalSem = "1";
                } else if (Regulation.equals("R15") && Year == 1) {
                    LocalYear = "0";
                    LocalSem = "1";
                } else {
                    LocalYear = valueOf(Year);
                    LocalSem = valueOf(Sem);
                }

                String SubjectId = Regulation + DeptNo + LocalYear + LocalSem;

                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(0, SubjectId);


                Intent intent = new Intent(MainActivity.this, SyllabusActivity.class);
                intent.putStringArrayListExtra("SubjectDetails", arrayList);
                startActivity(intent);

            }
        });

        card_view_materials.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MaterialsActivity.class));
            }
        });

        card_view_model_papers.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ModelpapersActivity.class));
            }
        });

        card_view_updates.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UpdatesActivity.class));
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(id.fab_settings);

        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegulationSelectorActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(drawer_layout);
        drawer.setStatusBarBackgroundColor(getResources().getColor( R.color.black));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, navigation_drawer_open, navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        txtReg = (TextView) findViewById(txt_Regulation);
        txtYear = (TextView) findViewById(txt_Year);
        txtSem = (TextView) findViewById(txt_Sem);

        dataInitializer();

        //Call functions
        btnSyllabus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String LocalYear, LocalSem;

                if (Regulation.equals("R13") && Year == 1) {
                    LocalYear = "0";
                    LocalSem = "1";
                } else if (Regulation.equals("R15") && Year == 1) {
                    LocalYear = "0";
                    LocalSem = "1";
                } else {
                    LocalYear = valueOf(Year);
                    LocalSem = valueOf(Sem);
                }

                String SubjectId = Regulation + DeptNo + LocalYear + LocalSem;

                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(0, SubjectId);


                Intent intent = new Intent(MainActivity.this, SyllabusActivity.class);
                intent.putStringArrayListExtra("SubjectDetails", arrayList);
                startActivity(intent);
            }
        });

        btnMaterial.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MaterialsActivity.class));
            }
        });

        btnModelPaper.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ModelpapersActivity.class));
            }
        });

        btnUpdates.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UpdatesActivity.class));
            }
        });

    }


    private void dataInitializer()
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

                        RollNo=cursor.getString(cursor.getColumnIndex("RollNo"));
                        Name=cursor.getString(cursor.getColumnIndex("Name"));
                        txtReg.setText(getResources().getString(R.string.setText_regulation) + cursor.getString(cursor.getColumnIndex("Regulation")));
                        txtYear.setText(getResources().getString(R.string.setText_Year) + cursor.getString(cursor.getColumnIndex("Year")));
                        txtSem.setText(getResources().getString(R.string.setText_Sem) + cursor.getString(cursor.getColumnIndex("Sem")));

                        Regulation=cursor.getString(cursor.getColumnIndex("Regulation"));
                        Year=Integer.parseInt(cursor.getString(cursor.getColumnIndex("Year")));
                        Sem=Integer.parseInt(cursor.getString(cursor.getColumnIndex("Sem")));
                        DeptNo=Integer.parseInt(cursor.getString(cursor.getColumnIndex("DeptCode")));
                        Type=cursor.getString(cursor.getColumnIndex("Type"));

                    } while (cursor.moveToNext());

                    //Assigning Name and roll no. to navigation drawer
                    NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
                    View nView=navView.getHeaderView(0);
                    TextView txtName=(TextView)nView.findViewById(R.id.nav_txt_name);
                    TextView txtRollNo=(TextView)nView.findViewById(R.id.nav_txt_rollno);
                    txtName.setText(Name);
                    txtRollNo.setText(RollNo);


                    if(Regulation.equals("R13") && Year==1)
                    {
                        txtSem.setVisibility(View.GONE);
                    }
                    if(Regulation.equals("R15") && Year==1)
                    {
                        txtSem.setVisibility(View.GONE);
                    }

                } else {
                    startActivity(new Intent(this,LoginActivity.class));
                    finish();
                }
            }
        } catch (Exception e)
        {
            // Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            FirebaseCrash.log(e.getMessage());
        }



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.college_details) {
            startActivity(new Intent(this,CollegeDetailsActivity.class));
            return true;
        }
        if (id == R.id.dept_details) {
            DeptDetailsActivity DDA=new DeptDetailsActivity();
            DDA.callDept(DeptNo,getBaseContext());
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bus) {
            // Handle the action
            startActivity(new Intent(this,BusDataActivity.class));
            //finish();
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(this,StudentProfileViewActivity.class));

        } else if (id == R.id.nav_sac) {
            startActivity(new Intent(this, SacHome.class));

        }
        else if (id == R.id.nav_memo) {
            startActivity(new Intent(this,StudentMemoViewActivity.class));

        } else if (id == R.id.nav_placements) {
            startActivity(new Intent(this, TPOHome.class));

        } else if (id == R.id.nav_updates) {
            startActivity(new Intent(this,UpdatesActivity.class));

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this,AboutActivity.class));

        } else if (id == R.id.nav_Logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are you sure you want to LOGOUT?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    logout = true;
                    doLogout();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    logout = false;
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        } else if(id == R.id.invite){
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "CMREC Little Helper");
                String sAux = "\nCMREC Little Helper \n\n";
                sAux = sAux + "A Student Companion Application for Students, by Students. \n";
                sAux = sAux + "Open the below link to download from Google Play Store \n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=com.ior.cmreclh \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "Sharing CMREC Little Helper"));
            } catch(Exception e) {
                Log.i("Invite", e.getMessage());
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void doLogout(){
        if (logout) {
            LogoutAction la = new LogoutAction();
            boolean result = la.deleteDBData(getBaseContext());

            if (result) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else
                Toast.makeText(this, "Logout Failed", Toast.LENGTH_SHORT).show();
        }
    }

}
