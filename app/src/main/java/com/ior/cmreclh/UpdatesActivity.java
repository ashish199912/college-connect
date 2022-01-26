package com.ior.cmreclh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.google.android.gms.ads.AdRequest.Builder;
import static com.ior.cmreclh.R.id;
import static com.ior.cmreclh.R.layout;
import static com.ior.cmreclh.R.layout.activity_updates;

/**
 * Created by Abhishek Jha on 20-01-2017.
 */

public class UpdatesActivity extends AppCompatActivity {

    private AdView adView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_updates);


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

     public void JNTUHResults(View view){

        ArrayList<String> al=new ArrayList<>();
        al.add("http://jntuhresults.in/");

        Intent intent=new Intent(UpdatesActivity.this,UpdateContActivity.class);
        intent.putStringArrayListExtra("WebSite", al);
        startActivity(intent);
    }
    public void CollegeMain(View view){

        ArrayList<String> al=new ArrayList<>();
        al.add("http://cmrec.ac.in/");

        Intent intent=new Intent(UpdatesActivity.this,UpdateContActivity.class);
        intent.putStringArrayListExtra("WebSite", al);
        startActivity(intent);
    }

    public void Notifications(View view){

        ArrayList<String> al=new ArrayList<>();
        al.add("https://www.jntufastupdates.com/category/jntu-h/notifications-jntuh/");

        Intent intent=new Intent(UpdatesActivity.this,UpdateContActivity.class);
        intent.putStringArrayListExtra("WebSite", al);
        startActivity(intent);
    }

    public void Timetable(View view){

        ArrayList<String> al=new ArrayList<>();
        al.add("https://www.jntufastupdates.com/category/jntu-h/time-tables-jntuh/");

        Intent intent=new Intent(UpdatesActivity.this,UpdateContActivity.class);
        intent.putStringArrayListExtra("WebSite", al);
        startActivity(intent);
    }

    public void Calenders(View view){

        ArrayList<String> al=new ArrayList<>();
        al.add("https://www.jntufastupdates.com/jntuh-academic-calendars/");

        Intent intent=new Intent(UpdatesActivity.this,UpdateContActivity.class);
        intent.putStringArrayListExtra("WebSite", al);
        startActivity(intent);
    }

    public void CMRECRepo(View view)
    {
        ArrayList<String> al=new ArrayList<>();
        al.add("http://119.235.53.233:55557/index.php");

        Intent intent=new Intent(UpdatesActivity.this,UpdateContActivity.class);
        intent.putStringArrayListExtra("WebSite", al);
        startActivity(intent);
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
