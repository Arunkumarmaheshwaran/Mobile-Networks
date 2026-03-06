package com.example.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "school.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "mapping";
    private static final String COL_STAFF = "staff";
    private static final String COL_STUDENT = "student";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_STAFF + " TEXT, " +
                COL_STUDENT + " TEXT)";
        db.execSQL(createTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void insertMapping(String staff, String student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STAFF, staff);
        values.put(COL_STUDENT, student);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public Cursor getStudentsByStaff(String staff) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COL_STUDENT + " FROM " + TABLE_NAME +
                " WHERE LOWER(" + COL_STAFF + ")=LOWER(?)", new String[]{staff});
    }
    public Cursor getStaffByStudent(String student) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COL_STAFF + " FROM " + TABLE_NAME +
                " WHERE LOWER(" + COL_STUDENT + ")=LOWER(?)", new String[]{student});
    }
    public int deletePerson(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, "LOWER(" + COL_STAFF + ")=LOWER(?) OR LOWER(" + COL_STUDENT + ")=LOWER(?)",
                new String[]{name, name});
        db.close();
        return rows;
    }
}
