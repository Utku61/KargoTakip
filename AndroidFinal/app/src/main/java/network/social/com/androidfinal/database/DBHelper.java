package network.social.com.androidfinal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;
import java.util.Date;
import java.util.List;

import network.social.com.androidfinal.model.Gorev;
import network.social.com.androidfinal.model.TaskLocation;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE="KARGOLISTESI2";
    private static final String TABLE = "KUTUCUK2";

    public DBHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + "( koordinatId INTEGER ,koordinatX TEXT,koordinatY TEXT,alimTarih TEXT,verilisTarih TEXT, aliciAd TEXT ,aliciSoyad TEXT,aliciTcNo TEXT,kargoBedel REAL,teslimDurumu TEXT,gorevliId INTEGER" + ")";
        Log.d("DBHelper", "SQL : " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void insertTask(Gorev task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("koordinatId",task.getCoordinateId());
        values.put("aliciAd", task.getCustomerName());
        values.put("aliciSoyad", task.getCustomerLastName());
        values.put("aliciTcNo",task.getCustomerTcNo());
        values.put("kargoBedel",task.getCargoPay());
        values.put("koordinatX",task.getCoordinateX());
        values.put("koordinatY",task.getCoordinateY());
        values.put("alimTarih", String.valueOf(task.getGetTimeCoordinate()));
        values.put("verilisTarih",String.valueOf(task.getReachingTimeCoordinate()));
        values.put("teslimDurumu",task.getDelivery());
        values.put("gorevliId",task.getUserId());
        db.insert(TABLE, null, values);
        db.close();
    }


    public List<Gorev> getCustomerTaskInformation(){
        List<Gorev> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select * from " + TABLE, null);
        while (cursor.moveToNext()) {
            Gorev customerTask = new Gorev();
            customerTask.setCoordinateId(cursor.getInt(0));
            customerTask.setCustomerName(cursor.getString(5));
            customerTask.setCustomerLastName(cursor.getString(6));
            customerTask.setCustomerTcNo(cursor.getString(7));
            customerTask.setCargoPay(cursor.getFloat(8));
            customerTask.setCoordinateX(cursor.getString(1));
            customerTask.setCoordinateY(cursor.getString(2));
            customerTask.setGetTimeCoordinate(cursor.getString(3));
            customerTask.setReachingTimeCoordinate(cursor.getString(4));
            customerTask.setDelivery(cursor.getString(9));
            customerTask.setUserId(cursor.getInt(10));
            tasks.add(customerTask);
        }
        cursor.close();
        db.close();

        return tasks;
    }



    public List<TaskLocation> getCustomerLocationList(){
        List<TaskLocation> location=new ArrayList<TaskLocation>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select koordinatX,koordinatY from "+TABLE+" where teslimDurumu='"+"edilmedi'",null);
        while(cursor.moveToNext()){
           TaskLocation taskLocation=new TaskLocation();
            taskLocation.setCoordinateX(cursor.getString(0));
            taskLocation.setCoordinateY(cursor.getString(1));
            location.add(taskLocation);
        }
        cursor.close();
        db.close();
        return location;
    }

    public void deleteTask(int coordinateId){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Delete from " + TABLE + " where koordinatId=" + coordinateId , null);
        cursor.close();
        db.close();
    }


}
