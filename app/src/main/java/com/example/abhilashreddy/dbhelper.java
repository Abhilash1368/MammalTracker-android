package com.example.abhilashreddy.firebase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.util.Locale.getDefault;

/**
 * Created by ABHILASH REDDY on 11/5/2016.
 */
public class dbhelper extends SQLiteOpenHelper {
    public static final String database_name="mydb";
    public static final String table_name="DETAILS";
    public static final String column_lat="LATITUDE";
    public static final String column_long="LONGITUDE";
    public static final String column_id="ID";
    public static final String column_time="timestamp";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + table_name + "( "+column_id+"  INTEGER primary key  ,"+column_lat+ " DOUBLE"+" , " + column_long + " DOUBLE , "+ column_time+" TEXT "+" )";

    public ArrayList<latlong> databasearraylist;
    public latlong latlongobject;
    public dbhelper(Context context){
        super(context ,database_name,null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
       // Log.e("databse",""+);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
      sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+table_name);
        onCreate(sqLiteDatabase);
    }
     public ArrayList<latlong> getarraydata(){
      // dbhelper udbhelper=new dbhelper();
         SQLiteDatabase db=this.getReadableDatabase();
         databasearraylist=new ArrayList<>();
         String q="SELECT * FROM DETAILS";
         Cursor cursor=db.rawQuery(q,null);
         //cursor.moveToFirst();

         if (cursor.moveToFirst()){
            do {
                latlongobject=new latlong();
                latlongobject.setLocallat(cursor.getDouble(cursor.getColumnIndex(column_lat)));
                latlongobject.setLocallongi(cursor.getDouble(cursor.getColumnIndex(column_long)));
                latlongobject.setUnique(cursor.getInt(cursor.getColumnIndex(column_id)));
                latlongobject.setTimestamp(cursor.getString(cursor.getColumnIndex(column_time)));
                databasearraylist.add(latlongobject);
            }while (cursor.moveToNext());

         }
         return databasearraylist;
     }

    }
