package com.ior.cmreclh;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Abhishek Jha on 13-02-2017.
 */

public class StudentDetails
{
    String Name, RollNo,Regulation;
    int Year ,Sem, code_Dept;
     SQLdbClass helper;
    SQLiteDatabase SQdb;
    Cursor cursor;
    ArrayList<String> al;

    public StudentDetails(Context context)
    {
        initialize(context);
    }

    public void initialize(Context context)
    {
        helper=new SQLdbClass(context);
        SQdb=helper.getReadableDatabase();

        try {
            cursor = SQdb.rawQuery("select * from StudentDetails", null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {

                        RollNo = cursor.getString(cursor.getColumnIndex("RollNo"));
                        Name = cursor.getString(cursor.getColumnIndex("Name"));
                        Regulation = cursor.getString(cursor.getColumnIndex("Regulation"));
                        Year = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Year")));
                        Sem = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Sem")));
                        code_Dept = Integer.parseInt(cursor.getString(cursor.getColumnIndex("DeptCode")));

                    } while (cursor.moveToNext());
                }
            }
        }
        catch (Exception e){
            Log.d(TAG, "initialize() returned: " + e.getMessage());
        }
    }

    //Assigning values
    /*public void setName(String Name){
        this.Name=Name;
    }

    public void setPhone(String Phone){
        this.Phone=Phone;
    }

    public void setRollNo(String RollNo){
        this.RollNo=RollNo;
    }

    public void setFatherName(String FatherName){
        this.FatherName=FatherName;
    }

    public void setGender(String Gender){
        this.Gender=Gender;
    }

    public void setEmailId(String EmailId){
        this.EmailId=EmailId;
    }

    public void setRegulation(String Regulation){
        this.Regulation=Regulation;
    }

    public void setDept(String Dept){
        this.Dept=Dept;
    }

    public void setYear(int Year){
        this.Year=Year;
    }

    public void setSem(int Sem){
        this.Sem=Sem;
    }

    public void setCode_Dept(int code_Dept){
        this.code_Dept=code_Dept;
    }
*/
    //Retrieving values
    public String getName(){
        return Name;
    }

    public String getRollNo(){
        return RollNo;
    }

    public String getRegulation(){
        return Regulation;
    }

    public int getYear(){
        return Year;
    }

    public int getSem(){
        return Sem;
    }

    public int getCode_Dept(){
        return code_Dept;
    }

}
