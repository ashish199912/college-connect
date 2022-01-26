package com.ior.cmreclh;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Abhishek Jha on 24-03-2017.
 */

public class DeptDetailsActivity {

    public void callDept(int DeptNo,Context context)
    {
        if(DeptNo==5 )
        {
            Intent intent = new Intent(context, DeptCseDetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        if(DeptNo==4)
        {
            Intent intent = new Intent(context, DeptMechDetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        if(DeptNo==3)
        {
            Intent intent = new Intent(context, DeptEceDetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


}
