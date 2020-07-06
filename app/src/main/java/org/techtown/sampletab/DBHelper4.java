package org.techtown.sampletab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class DBHelper4 extends SQLiteOpenHelper {

    final String TAG = "DBHelper";

    public Context context;

    //Table name and column name
    public static final String TABLE_NAME = "MYSTAR";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String DATE = "date";
    public static final String IMGURL = "imgurl";

    //Database information
    static final String DB_NAME = "example.db";
    static final int DB_VERSION = 1;

    public DBHelper4(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    //    최초 DB가 존재하지 않으면 새로 생성한다.
    @Override
    public void onCreate(SQLiteDatabase db) {

        //변수랑 섞어 놔서 복잡하니 공백을 잘 체크하자. 이것 때문에 에러나서 많은 시간을 허비했다.
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CREATE TABLE " +TABLE_NAME + "(");
        stringBuffer.append(ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuffer.append(NAME + " TEXT, ");
        stringBuffer.append(TITLE + " TEXT, ");
        stringBuffer.append(CONTENT + " TEXT, ");
        stringBuffer.append(DATE + " TEXT, ");
        stringBuffer.append(IMGURL + " TEXT);");

        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append("CREATE TABLE PLACE_LIST (");
        stringBuffer1.append("PID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuffer1.append("PLACE TEXT, ");
        stringBuffer1.append("P_DATE TEXT, ");
        stringBuffer1.append("CNT INTEGER);");

        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("CREATE TABLE LIST (");
        stringBuffer3.append("LID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuffer3.append("PID INTEGER, ");
        stringBuffer3.append("DID INTEGER, ");
        stringBuffer3.append("TODO TEXT, ");
        stringBuffer3.append("PLACE TEXT);");


        Log.d(TAG, stringBuffer.toString());
        db.execSQL(stringBuffer.toString());
        Log.d(TAG, stringBuffer1.toString());
        db.execSQL(stringBuffer1.toString());
        Log.d(TAG, stringBuffer3.toString());
        db.execSQL(stringBuffer3.toString());
        Toast.makeText(context, "DB생성완료", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        try {
            Toast.makeText(context, "onUpgrade", Toast.LENGTH_SHORT).show();
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    DB에 직접 데어터를 추가하고 수정하고 삭제하는 함수들을 모아 둠
     */
    //데이터 추가하고 true/false를 반환한다.
    public boolean insertData(String name, String title, String content, String date, String imgurl){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(TITLE, title);
        values.put(CONTENT, content);
        values.put(DATE, date);
        values.put(IMGURL, imgurl);
        long result = db.insert(TABLE_NAME, null, values);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean insertData1(String place, String date, int cnt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PLACE", place);
        values.put("P_DATE", date);
        values.put("CNT", cnt);
        long result = db.insert("PLACE_LIST", null, values);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean insertData3(int pid, int did, String todo, String place){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PID", pid);
        values.put("DID", did);
        values.put("TODO", todo);
        values.put("PLACE", place);
        long result = db.insert("LIST", null, values);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    //데이터 모두 가져오기
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor corsor = db.rawQuery("select * from " + TABLE_NAME + " order by _id desc", null);
        return corsor;
    }

    //데이터 모두 가져오기
    public Cursor getAllData1(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor corsor = db.rawQuery("select * from " + table, null);
        return corsor;
    }

    //테이블 삭제
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v("title", id);
        return db.delete(TABLE_NAME, "date = ?" , new String[] {id});
    }

    /*
        DB에서 데이터를 모두 가져와서 ResyclerView 어답터에서 사용할 Items 배열에
        순더대로 추가하는 함수. 여기저기에서 불러서 사용해야 할경우를 대비 여기에 넣어 둠
     */
    public void updateItems() {
        Fragment4.items.clear();
        Cursor cursor = getAllData();
        if(cursor.getCount() == 0) {
            Toast.makeText(context, "데이터없음", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String imgurl = cursor.getString(cursor.getColumnIndex("imgurl"));

                Fragment4.items.add(new DiaryViewItem(name, title, content, date, imgurl));
            }
        }
        cursor.close();
    }

    public void updateItems1() {
        Fragment2.items.clear();
        Cursor cursor = getAllData1("PLACE_LIST");
        if(cursor.getCount() == 0) {
            Toast.makeText(context, "데이터없음", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                String place = cursor.getString(cursor.getColumnIndex("PLACE"));
                Fragment2.items.add(place);
            }
        }
        cursor.close();
    }

    public void updateItems3(int pid, int did) {
        Fragmet2_todo.items_todo.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from LIST", null);
        if(cursor.getCount() == 0) {
            Toast.makeText(context, "데이터없음", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                int p = cursor.getInt(cursor.getColumnIndex("PID"));
                int d = cursor.getInt(cursor.getColumnIndex("DID"));
                String todo = cursor.getString(cursor.getColumnIndex("TODO"));
                if(p == pid && d == did)  Fragmet2_todo.items_todo.add(todo);
            }
        }
        cursor.close();
    }

    public String placehold(int pid, int did, String dodo) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from LIST", null);
        if(cursor.getCount() == 0) {
            Toast.makeText(context, "데이터없음", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                int p = cursor.getInt(cursor.getColumnIndex("PID"));
                int d = cursor.getInt(cursor.getColumnIndex("DID"));
                String todo = cursor.getString(cursor.getColumnIndex("TODO"));
                if(p == pid && d == did && dodo.equals(todo))
                {
                    String pl = cursor.getString(cursor.getColumnIndex("PLACE"));
                    cursor.close();
                    return pl;
                }
            }
        }
        cursor.close();
        return "!null";
    }

    public void updateData(int pid, int did, String newIndex, String newplace) {
        SQLiteDatabase db = this.getWritableDatabase();
        int lid = 0;
        Cursor cursor = db.rawQuery("select * from LIST", null);
        if(cursor.getCount() == 0) {
            Toast.makeText(context, "데이터없음", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                int p = cursor.getInt(cursor.getColumnIndex("PID"));
                int d = cursor.getInt(cursor.getColumnIndex("DID"));
                String todo = cursor.getString(cursor.getColumnIndex("TODO"));
                if(p == pid && d == did && newIndex.equals(todo))
                {
                    lid = cursor.getInt(cursor.getColumnIndex("LID"));
                }
            }
        }
        cursor.close();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("UPDATE LIST SET PLACE='" + newplace + "' ");
        stringBuffer.append("WHERE LID="+lid);
        db.execSQL(stringBuffer.toString());
    }
}