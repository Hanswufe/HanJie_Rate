package com.example.dell.rateapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RateManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public RateManager(Context context) {
        dbHelper = new DBHelper(context,TBNAME,null,1);
        TBNAME = DBHelper.TB_NAME;
    }
    public boolean isExit(){
        if(dbHelper.tabIsExist(TBNAME)){
            return true;
        }else{
            return false;
        }
    }
    public void add(RateItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curname", item.getCurName());
        values.put("currate", item.getCurRate());
        db.insert(TBNAME, null, values);
        db.close();
    }
    public void addAll(List<RateItem> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for(RateItem item : list){
            values.put("curname", item.getCurName());
            values.put("currate", item.getCurRate());
            db.insert(TBNAME, null, values);
            values.clear();
        }
        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[] { String.valueOf(id) });
        db.close();
    }
    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from " + TBNAME);
        db.close();
    }


    public RateItem findById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null,
                "ID=?", new String[]{String.valueOf(id)},
                null, null, null);
        RateItem item = null;
        if (cursor != null && cursor.moveToFirst()) {
            item = new RateItem();
            item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            item.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
            item.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
            cursor.close();
        }
        db.close();
        return item;
    }

    public List<RateItem> listAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        List<RateItem> list = new ArrayList<RateItem>();
        if (cursor != null && cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                RateItem item = new RateItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
                item.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
                list.add(item);
            }
            cursor.close();
        }
        db.close();
        return list;
    }

    public void update(RateItem item) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues value = new ContentValues();
        value.put("curname", item.getCurName());
        value.put("currate", item.getCurRate());
        db.update(TBNAME, value, "ID=?", new String[] { String.valueOf(item.getId()) });
        db.close();
    }

}
