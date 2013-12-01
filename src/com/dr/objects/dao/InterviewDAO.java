package com.dr.objects.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dr.helpers.DatabaseManager;
import com.dr.helpers.Utilities;
import com.dr.objects.Interview;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class InterviewDAO {
    private static final String TABLE_INTERVIEW = "interview";

    // columns
    private static final String ID = "_id";
    private static final String JOB_ID = "job_id";
    private static final String TIME = "time";
    private static final String LOCATION = "location";
    private static final String INTERVIEWER = "interviewer";
    private static final String REMINDER = "reminder";
    private static String[] allColumns = {ID, JOB_ID, TIME, LOCATION, INTERVIEWER, REMINDER};

    // create table
    public static final String CREATE_TABLE = "" + "CREATE TABLE " + TABLE_INTERVIEW
            + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + JOB_ID + " INTEGER, "
            + TIME + " DATETIME, "
            + LOCATION + " TEXT, "
            + INTERVIEWER + " TEXT, "
            + REMINDER + " DATETIME "
            + ")";

    // Database fields
    private SQLiteDatabase database;
    private DatabaseManager dbHelper;

    public InterviewDAO(Context context) {
        dbHelper = new DatabaseManager(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Interview getInterviewById(int id) {
        String selection = ID + " = " + id;
        Cursor cursor = database.query(TABLE_INTERVIEW, allColumns, selection, null, null, null, null);
        cursor.moveToFirst();
        Interview interview = cursorToInterview(cursor);
        cursor.close();

        return interview;
    }

    public List<Interview> getAllInterviewsByJobId(int jobId) {
        List<Interview> interviews = new ArrayList<Interview>();

        String selection = JOB_ID + " = " + jobId;
        Cursor cursor = database.query(TABLE_INTERVIEW, allColumns, selection, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Interview interview = cursorToInterview(cursor);
            interviews.add(interview);
            cursor.moveToNext();
        }
        cursor.close();
        return interviews;
    }

    public List<Interview> getAllInterviews() {
        List<Interview> interviews = new ArrayList<Interview>();

        Cursor cursor = database.query(TABLE_INTERVIEW, allColumns, null, null, null,
                null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Interview interview = cursorToInterview(cursor);
            interviews.add(interview);
            cursor.moveToNext();
        }
        cursor.close();
        return interviews;
    }


    public Interview addInterview(Interview interview) {
        ContentValues values = new ContentValues();
        values.put(JOB_ID, interview.getJobId());
        values.put(TIME, Utilities.getStringFromCalendar(interview.getTime()));
        values.put(LOCATION, interview.getLocation());
        values.put(INTERVIEWER, interview.getInterviewer());
        if(interview.getReminder() != null) {
            values.put(REMINDER, Utilities.getStringFromCalendar(interview.getReminder()));


            
        }
        
        int insertId = (int) database.insert(TABLE_INTERVIEW, null, values);
        return getInterviewById(insertId);
    }

    public Interview updateInterview(Interview interview) {
        ContentValues values = new ContentValues();
        values.put(JOB_ID, interview.getJobId());
        values.put(TIME, Utilities.getStringFromCalendar(interview.getTime()));
        values.put(LOCATION, interview.getLocation());
        values.put(INTERVIEWER, interview.getInterviewer());
        values.put(REMINDER, Utilities.getStringFromCalendar(interview.getReminder()));

        database.update(TABLE_INTERVIEW, values, ID + " = " + interview.getId(), null);
        return getInterviewById(interview.getId());
    }


    public void deleteInterview(Interview interview) {
        int id = interview.getId();
        database.delete(TABLE_INTERVIEW, ID + " = " + id, null);
    }

    private Interview cursorToInterview(Cursor cursor) {
        Interview interview = new Interview();

        interview.setId(cursor.getInt(0));
        interview.setJobId(cursor.getInt(1));
        interview.setTime(getCalendarFromString(cursor.getString(2)));
        interview.setLocation(cursor.getString(3));
        interview.setInterviewer(cursor.getString(4));
        interview.setReminder(getCalendarFromString(cursor.getString(5)));

        return interview;
    }

    private Calendar getCalendarFromString(String string) {
        Calendar calendar = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(string);
            calendar = new GregorianCalendar();
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar;
    }
}