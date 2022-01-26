package com.ior.cmreclh;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Abhishek Jha on 26-01-2017.
 */

public class SQLdbClass extends SQLiteOpenHelper {

    public static final String DATABASE="StudentDb.db";
    public static final int VERSION=1;
    /*public static final String Name="Name";
    public static final String RollNo="RollNo";
    public static final String Regulation="Regulation";
    public static final String Year="Year";
    public static final String Sem="Sem";
    public static final String DeptCode="DeptCode";
    public static final String Password="Password";
    public static final String TABLE="StudentDetails";*/

    public SQLdbClass(Context ctx){
        super(ctx,DATABASE,null,VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table StudentDetails(RollNo,Name,Regulation,Year,Sem,DeptCode,Password,EmailId,PhoneNo,FatherName,Type)");
        //db.execSQL("create table " +TABLE+ "(" +RollNo+ "," +Name+ "," +Regulation+ "," +Year+ "," +Sem+ "," +DeptCode+ "," +Password+ ")");
        db.execSQL("create table Subjects(SubjectNo,Subject)");
        db.execSQL("create table Syllabus(SubjectCode,SubjectName,SubjectLoc)");
        db.execSQL("create table Placements(Year,Name,RollNo,Company,Package)");
        db.execSQL("create table Percentage(YearSem,Year,Sem,Total,Obtained,Aggregate,PRIMARY KEY(YearSem))");
        db.execSQL("create table Company(Name,Recruited,Package,Year)");
        db.execSQL("create table FacultyList(Name,Designation)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
