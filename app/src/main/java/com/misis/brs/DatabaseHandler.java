package com.misis.brs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Vector;

public class DatabaseHandler {

    private static final String DB_NAME = "BRS";
    private static final int DB_VERSION = 1;

    private static final String TABLE_HW = "homework";
    private static final String TABLE_M = "marks";

    private static final String COLUMN_DEADLINE = "deadline";
    private static final String COLUMN_SEMESTER = "semester";
    private static final String COLUMN_HOMETASK = "hometask";
    private static final String COLUMN_FINISHED = "finished";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_MARK = "mark";
    private static final String COLUMN_MAXMARK = "maxMark";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_SEMESTER_MARK = 1;
    private static final int NUM_COLUMN_TYPE = 2;
    private static final int NUM_COLUMN_MARK = 3;
    private static final int NUM_COLUMN_MAXMARK = 4;
    private static final int NUM_COLUMN_DESCRIPTION = 5;
    private static final int NUM_COLUMN_SEMESTER_HW = 0;
    private static final int NUM_COLUMN_DEADLINE = 1;
    private static final int NUM_COLUMN_HOMETASK = 2;
    private static final int NUM_COLUMN_FINISHED = 3;

    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        OpenHelper openHelper = new OpenHelper(context);
        db = openHelper.getWritableDatabase();
    }

    public Returns addHometask(Homework homework) {
        try {
            if (selectHometaskByDate(homework.getDeadline()) == null) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_SEMESTER, homework.getSemester());
                values.put(COLUMN_DEADLINE, homework.getDeadline());
                values.put(COLUMN_HOMETASK, homework.getHometask());
                values.put(COLUMN_FINISHED, homework.getCheck());
                db.insert(TABLE_HW, null, values);
                return Returns.DONE;
            } else return Returns.DUPLICAT;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return Returns.SQL_ERROR;
        }
    }

    public Homework selectHometaskByDate(long date) {
        Cursor cursor;
        String[] str = new String[]{COLUMN_SEMESTER + ", " + COLUMN_DEADLINE + ", " + COLUMN_HOMETASK + ", " + COLUMN_FINISHED};

        try {
            cursor = db.query(TABLE_HW, str, COLUMN_DEADLINE + " = ?", new String[]{String.valueOf(date)}, null, null, null);

            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
            } else {
                return null;
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }

        return new Homework(cursor.getInt(NUM_COLUMN_SEMESTER_HW), cursor.getLong(NUM_COLUMN_DEADLINE), cursor.getString(NUM_COLUMN_HOMETASK), cursor.getInt(NUM_COLUMN_FINISHED) != 0);
    }

    public Vector<Homework> selectHometaskForSemester(int semester) {
        Cursor cursor;
        String[] str = new String[]{COLUMN_SEMESTER + ", " + COLUMN_DEADLINE + ", " + COLUMN_HOMETASK + ", " + COLUMN_FINISHED};

        try {
            cursor = db.query(TABLE_HW, str, COLUMN_SEMESTER + " = ?", new String[]{String.valueOf(semester)}, null, null, null);

            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
            } else {
                return null;
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
        Vector<Homework> res = new Vector<>();
        while (!cursor.isAfterLast()) {
            res.add(new Homework(cursor.getInt(NUM_COLUMN_SEMESTER_HW), cursor.getLong(NUM_COLUMN_DEADLINE), cursor.getString(NUM_COLUMN_HOMETASK), cursor.getInt(NUM_COLUMN_FINISHED) != 0));
            cursor.moveToNext();
        }
        return res;
    }

    public Returns deleteHometaskByDate(long deadline) {
        try {
            db.delete(TABLE_HW, COLUMN_DEADLINE + " = ?", new String[]{String.valueOf(deadline)});
        } catch (SQLiteException e) {
            e.printStackTrace();
            return Returns.SQL_ERROR;
        }
        return Returns.DONE;
    }

    public Returns updateHometaskByDate(long deadline, String newHometask, boolean check) {
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_HOMETASK, newHometask);
            values.put(COLUMN_FINISHED, check);
            db.update(TABLE_HW, values, COLUMN_DEADLINE + " = ?", new String[]{String.valueOf(deadline)});
        } catch (SQLiteException e) {
            e.printStackTrace();
            return Returns.SQL_ERROR;
        }
        return Returns.DONE;
    }

    public Returns addMark(Mark mark) {
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_SEMESTER, mark.getSemester());
            values.put(COLUMN_TYPE, mark.getType());
            values.put(COLUMN_MARK, mark.getMark());
            values.put(COLUMN_MAXMARK, mark.getMaxMark());
            values.put(COLUMN_DESCRIPTION, mark.getDescription());
            db.insert(TABLE_M, null, values);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return Returns.SQL_ERROR;
        }
        return Returns.DONE;
    }



    public Vector<Mark> selectMark(Integer semester, Integer type) {
        Cursor cursor;
        String[] str = new String[]{COLUMN_ID + ", " + COLUMN_SEMESTER + ", " + COLUMN_TYPE + ", " + COLUMN_MARK + ", " + COLUMN_MAXMARK + ", " + COLUMN_DESCRIPTION};

        try {
            cursor = db.query(TABLE_M, str, COLUMN_SEMESTER + " = ? AND " + COLUMN_TYPE + " = ?", new String[]{String.valueOf(semester), String.valueOf(type)}, null, null, null);

            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
            } else {
                return new Vector<>();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }

        Vector<Mark> res = new Vector<>();
        MarkBuilder mb = new MarkBuilder();
        while (!cursor.isAfterLast()) {
            mb.buildId(cursor.getInt(NUM_COLUMN_ID))
                    .buildSemester(cursor.getInt(NUM_COLUMN_SEMESTER_MARK))
                    .buildType(cursor.getInt(NUM_COLUMN_TYPE))
                    .buildMark(cursor.getInt(NUM_COLUMN_MARK))
                    .buildMaxMark(cursor.getInt(NUM_COLUMN_MAXMARK))
                    .buildDescription(cursor.getString(NUM_COLUMN_DESCRIPTION));
            res.add(mb.build());
            cursor.moveToNext();
        }

        return res;
    }

    public Returns deleteMarkById(Integer id) {
        try {
            db.delete(TABLE_M, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        } catch (SQLiteException e) {
            e.printStackTrace();
            return Returns.SQL_ERROR;
        }
        return Returns.DONE;
    }

    public Returns updateMarkById(Integer id, Mark mark) {
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_SEMESTER, mark.getSemester());
            values.put(COLUMN_TYPE, mark.getType());
            values.put(COLUMN_MARK, mark.getMark());
            values.put(COLUMN_MAXMARK, mark.getMaxMark());
            values.put(COLUMN_DESCRIPTION, mark.getDescription());
            db.update(TABLE_M, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        } catch (SQLiteException e) {
            e.printStackTrace();
            return Returns.SQL_ERROR;
        }
        return Returns.DONE;
    }

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                String createHWTable = "CREATE TABLE IF NOT EXISTS " + TABLE_HW + "(" + COLUMN_DEADLINE + " INTEGER PRIMARY KEY, "
                        + COLUMN_SEMESTER + " INTEGER, " + COLUMN_HOMETASK + " TEXT, " + COLUMN_FINISHED + " BOOLEAN)";
                String createMarksTable = "CREATE TABLE IF NOT EXISTS " + TABLE_M + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_TYPE + " INTEGER, " + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_SEMESTER + " INTEGER, " + COLUMN_MARK
                        + " INTEGER, " + COLUMN_MAXMARK + " INTEGER)";
                db.execSQL(createHWTable);
                db.execSQL(createMarksTable);
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_HW);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_M);
                onCreate(db);
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        }
    }

    protected Returns isAbleToAdd(Mark mark) {
        Vector <Mark> ans = selectMark(mark.getSemester(), mark.getType());
            switch (mark.getType()){
                case 0:
                case 1:
                    int sum = 0;
                    for (int i = 0; i < ans.size(); i++)
                        sum += ans.elementAt(i).getMark();
                    if (sum + mark.getMark() > 20)
                        return Returns.MARKS_SCORE_LIMIT;
                    else break;
                case 2:
                    if (ans.size() >= 4)
                        return Returns.MARKS_TYPE_LIMIT;
                    else break;
                case 3:
                case 4:
                    if (!ans.isEmpty())
                        return Returns.MARKS_TYPE_LIMIT;
                    else break;
                case 5:
                case 6:
                    if (!ans.isEmpty())
                        return Returns.MARKS_TYPE_LIMIT;
                    else break;
            }
            return Returns.DONE;
        }
    }