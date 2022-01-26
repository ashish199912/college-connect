package com.ior.cmreclh;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ior.cmreclh.api.VolleySubjectsApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Abhishek Jha on 15-02-2017.
 */

public class LocalDbSubStorage
{
    int Year,Sem,DeptNo;
    String Regulation;
    //Context publicContext;
    SQLdbClass helper;
    SQLiteDatabase SQdb;
    Cursor cursor;
    ArrayList<String> al;
    SQLiteStatement SQstmt;
    RequestQueue requestQueue;

    public LocalDbSubStorage()
    {
        //publicContext=context;
        //assignValues();
        //checkLocal(context);
        //checkServer();
    }

    public void assignValues(Context context)
    {
        StudentDetails sd=new StudentDetails(context);
        DeptNo=sd.getCode_Dept();
        Year=sd.getYear();
        Sem=sd.getSem();
        Regulation = sd.getRegulation();
    }

    public SimpleAdapter checkLocal(Context context)
    {
        //publicContext=context;
        assignValues(context);
        helper=new SQLdbClass(context);
        SQdb=helper.getReadableDatabase();


        List<Map<String,String>> data= null;
        data=new ArrayList<Map<String,String>>();
        SimpleAdapter simpleAdapter=null;

        try {
            cursor = SQdb.rawQuery("select * from Subjects", null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        Map<String,String> datanum=new HashMap<String, String>();
                        datanum.put("SubjectNo",cursor.getString(cursor.getColumnIndex("SubjectNo")));
                        datanum.put("Subject",cursor.getString(cursor.getColumnIndex("Subject")));
                        data.add(datanum);
                    } while (cursor.moveToNext());

                    String[] fromwhere={"SubjectNo","Subject"};
                    int[] viewswhere={R.id.lblSylSubCode,R.id.lblSylSub};
                    simpleAdapter=new SimpleAdapter(context,data,R.layout.list_syl_sub,fromwhere,viewswhere);

                }else {
                    checkServer(context);
//                    checkLocal(context);
                }
            }
        }
        catch (Exception e){
            Log.d(TAG, "initialize() returned: " + e.getMessage());
        }
        return simpleAdapter;
    }

    //check in server and do the local db storage
    public void checkServer(Context context)
    {
        assignValues(context);
        helper= new SQLdbClass(context);
        SQdb=helper.getWritableDatabase();

        requestQueue = Volley.newRequestQueue(context);

        VolleySubjectsApi apiRequest = new VolleySubjectsApi(Regulation, DeptNo, Year, Sem, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("Response", response);

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Subjects");

                    for(int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsonSubject = (JSONObject) jsonArray.get(i);

                        SQstmt=SQdb.compileStatement("insert into Subjects values(?,?)");
                        SQstmt.bindString(1,jsonSubject.getString("SubjectNo"));
                        SQstmt.bindString(2,jsonSubject.getString("SubjectName"));
                        SQstmt.executeInsert();

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        requestQueue.add(apiRequest);

    }
}
