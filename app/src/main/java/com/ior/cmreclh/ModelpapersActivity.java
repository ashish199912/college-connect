package com.ior.cmreclh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.AdapterView.OnItemClickListener;
import static com.google.android.gms.ads.AdRequest.Builder;
import static com.ior.cmreclh.R.id;
import static com.ior.cmreclh.R.id.model_list_subjects;
import static com.ior.cmreclh.R.layout;
import static com.ior.cmreclh.R.layout.activity_modelpapers;

/**
 * Created by Abhishek Jha on 20-01-2017.
 */

public class ModelpapersActivity extends AppCompatActivity {
    int DeptNo,Year,Sem;
    ListView listView;
    String SubjectId,SubjectName;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_modelpapers);


        listView = (ListView) findViewById(model_list_subjects);

        //retrieving the subject code from list view
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String SubjectContext = listView.getItemAtPosition(position).toString();
                SubjectId = SubjectContext.substring(11, 18);
                SubjectName = SubjectContext.substring(28, SubjectContext.length() - 1);
                //Toast.makeText(MaterialsActivity.this,SubjectId,Toast.LENGTH_SHORT).show();
                //Toast.makeText(MaterialsActivity.this,SubjectName,Toast.LENGTH_SHORT).show();
                subjectOnClick();
            }
        });

        loadSubjects();

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

    public void subjectOnClick()
    {
        ArrayList<String> arrayList= new ArrayList<>();
        arrayList.add(0, SubjectId);
        arrayList.add(1, SubjectName);

        Intent launchactivity= new Intent(ModelpapersActivity.this,MPActivity.class);
        launchactivity.putStringArrayListExtra("SubjectDetails",arrayList);
        startActivity(launchactivity);
    }

    //Handle calls
    public void loadSubjects()
    {
        LocalDbSubStorage localDbSubStorage = new LocalDbSubStorage();
        SimpleAdapter simpleAdapter = localDbSubStorage.checkLocal(getBaseContext());
        listView.setAdapter(simpleAdapter);
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
