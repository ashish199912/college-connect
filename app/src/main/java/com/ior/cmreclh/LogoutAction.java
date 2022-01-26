package com.ior.cmreclh;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

/**
 * Created by Abhishek Jha on 26-02-2017.
 */

public class LogoutAction {

    // Declaring SQLite database variables
    SQLdbClass helper;
    SQLiteDatabase SQdb;
    Cursor cursor;
    ArrayList<String> al;
    SQLiteStatement SQstmt;

    public boolean deleteDBData(Context context)
    {
        //create database
        helper= new SQLdbClass(context);

        //open database connection
        SQdb=helper.getWritableDatabase();

        SQstmt=SQdb.compileStatement("delete from StudentDetails");
        int i=SQstmt.executeUpdateDelete();

        SQstmt=SQdb.compileStatement("delete from Subjects");
        int j=SQstmt.executeUpdateDelete();

        SQstmt=SQdb.compileStatement("delete from Syllabus");
        int k=SQstmt.executeUpdateDelete();

        SQstmt=SQdb.compileStatement("delete from Placements");
        int l=SQstmt.executeUpdateDelete();

        if(i!=0 || j!=0 || k!=0)
            return true;
        else
            return false;

    }
}
