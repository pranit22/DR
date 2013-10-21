package com.dr.objects.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dr.database.DatabaseManager;
import com.dr.objects.Job;

public class JobDAO
{
    private static final String TABLE_JOB = "job";

    // columns
    private static final String JOB_ID = "_id";
    private static final String COMPANY = "company";
    private static final String DESCRIPTION = "description";
    private static String[] allColumns = { JOB_ID, COMPANY, DESCRIPTION };

    // create table
    public static final String CREATE_TABLE = "" + "CREATE TABLE " + TABLE_JOB
	    + "(" + JOB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COMPANY
	    + " TEXT, " + DESCRIPTION + " TEXT" + ")";

    // Database fields
    private SQLiteDatabase database;
    private DatabaseManager dbHelper;

    public JobDAO(Context context)
    {
	dbHelper = new DatabaseManager(context);
    }

    public void open() throws SQLException
    {
	database = dbHelper.getWritableDatabase();
    }

    public void close()
    {
	dbHelper.close();
    }
    
    public Job getJobByJobId(int jobId)
    {
	String selection = JOB_ID + " = " + jobId;
	Cursor cursor = database.query(TABLE_JOB, allColumns, selection, null, null, null, null);
	cursor.moveToFirst();
	Job newJob = cursorToJob(cursor);
	cursor.close();

	return newJob;
    }

    public Job addJob(Job job)
    {
	ContentValues values = new ContentValues();
	values.put(COMPANY, job.getCompany());
	values.put(DESCRIPTION, job.getDescription());
	int insertId = (int) database.insert(TABLE_JOB, null, values);
	return getJobByJobId(insertId);
    }
    
    public Job updateJob(Job job)
    {
	ContentValues values = new ContentValues();
	values.put(COMPANY, job.getCompany());
	values.put(DESCRIPTION, job.getDescription());
	database.update(TABLE_JOB, values, JOB_ID + " = " + job.getJobId(), null);
	return getJobByJobId(job.getJobId());
    }

    public void deleteJob(Job job)
    {
	int id = job.getJobId();
	database.delete(TABLE_JOB, JOB_ID + " = " + id, null);
    }

    public List<Job> getAllJobs()
    {
	List<Job> jobs = new ArrayList<Job>();

	Cursor cursor = database.query(TABLE_JOB, allColumns, null, null, null,
		null, null);

	cursor.moveToFirst();
	while (!cursor.isAfterLast())
	{
	    Job comment = cursorToJob(cursor);
	    jobs.add(comment);
	    cursor.moveToNext();
	}
	cursor.close();
	return jobs;
    }

    private Job cursorToJob(Cursor cursor)
    {
	Job job = new Job();
	job.setJobId(cursor.getInt(0));
	job.setCompany(cursor.getString(1));
	job.setDescription(cursor.getString(2));
	return job;
    }
}