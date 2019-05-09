package com.myapplicationdev.android.practicalquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    //TODO Define the Database properties
    private static final String DATABASE_NAME = "stu.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STUDENT = "Student";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_GPA = "gpa";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO CREATE TABLE Student
        String createTableSql = "CREATE TABLE " + TABLE_STUDENT + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + "TEXT)";
        db.execSQL(createTableSql);
        Log.i("info", "created tables");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);

    }

    public void insertStudent(String name, double gpa) {
        //TODO insert the data into the database
        //Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        //We use ContentValues object to store the values for the db operation
        ContentValues values = new ContentValues();
        //Store the column name as key and the noteContent as value
        values.put(COLUMN_NAME, name);
        //Store the column name as key and the stars as value
        values.put(COLUMN_GPA, gpa);
        //Insert the row into the Table_NOTE
        db.insert(TABLE_STUDENT, null, values);
        //Close the database connection
        db.close();
    }

    public ArrayList<Student> getAllStudent() {
        //TODO return records in Java objects
        ArrayList<Student> students = new ArrayList<>();
        String selectQuery = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_GPA + "FROM" + TABLE_STUDENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //moveToFirst() moves to first row
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                double gpa = cursor.getDouble(2);
                Student student = new Student(id, name, gpa);
                students.add(student);
            } while (cursor.moveToNext());
        }
        //Close connection
        cursor.close();
        db.close();

        return students;
    }

    public ArrayList<String> getStudentContent() {
        //Create an ArrayList that holds String objects
        ArrayList<String> students = new ArrayList<String>();
        //Select all the tasks's description
        String selectQuery = "SELECT " + COLUMN_ID + COLUMN_NAME + COLUMN_GPA + " FROM " + TABLE_STUDENT;

        //Get the instance of database to read
        SQLiteDatabase db = this.getReadableDatabase();
        //Run the SQL query and get back the Cursor object
        Cursor cursor = db.rawQuery(selectQuery, null);

        //moveToFirst() moves to first row
        if (cursor.moveToFirst()) {
            //Loop while moveToNext() points to next row
            //and returns true; moveToNext() returns false
            //when no more next row to move to
            do {
                //Add the task content to the ArrayList object
                //0 in getString(0) return the data in the first
                //column in the Cursor object. getString(1)
                //return second column data and so on.
                //Use getInt(0) if data is an int
                students.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        //Close connection
        cursor.close();
        db.close();

        return students;
    }
}
