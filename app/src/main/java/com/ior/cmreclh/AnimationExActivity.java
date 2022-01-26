package com.ior.cmreclh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by Abhishek Jha on 27-03-2017.
 */

public class AnimationExActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_ex);

                TextView txt=(TextView)findViewById(R.id.txtHello);
                Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink_anim);
                txt.startAnimation(animation);
    }
}
