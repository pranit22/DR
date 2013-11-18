package com.dr.objects.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dr.database.DatabaseManager;
import com.dr.objects.CoverLetter;
import com.dr.objects.Job;
import com.dr.objects.JobApplication;
import com.dr.objects.Resume;
import com.dr.objects.Status;

public class JobApplicationDAO
{
    private static final String TABLE_JOB_APPLICATION = "job_application";

    // columns
    private static final String ID = "_id";
    private static final String COMPANY = "company";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String STATUS = "status";
    private static final String RESUME = "resume";
    private static final String COVER_LETTER = "cover_letter";
    private static String[] allColumns = { ID, COMPANY, TITLE, DESCRIPTION, STATUS, RESUME, COVER_LETTER };

    // create table
    public static final String CREATE_TABLE = "" + "CREATE TABLE " + TABLE_JOB_APPLICATION
	    + "(" 
	    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
	    + COMPANY + " TEXT, "
	    + TITLE + " TEXT, "
	    + DESCRIPTION + " TEXT"
	    + STATUS + " TEXT"
	    + RESUME + " INTEGER"
	    + COVER_LETTER + " INTEGER"
	    + ")";

    // Database fields
    private SQLiteDatabase database;
    private DatabaseManager dbHelper;

    public JobApplicationDAO(Context context)
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
    
    public JobApplication getJobApplicationByJobId(int jobId)
    {
	String selection = ID + " = " + jobId;
	Cursor cursor = database.query(TABLE_JOB_APPLICATION, allColumns, selection, null, null, null, null);
	cursor.moveToFirst();
	JobApplication jobApplication = cursorToJobApplication(cursor);
	cursor.close();

	return jobApplication;
    }

    public JobApplication addJobApplication(JobApplication jobApplication)
    {
	ContentValues values = new ContentValues();
	values.put(COMPANY, jobApplication.getJob().getCompany());
	values.put(TITLE, jobApplication.getJob().getTitle());
	values.put(DESCRIPTION, jobApplication.getJob().getDescription());
	values.put(STATUS, jobApplication.getStatus().toString());
	values.put(RESUME, jobApplication.getResume().getId());
	values.put(COVER_LETTER, jobApplication.getCoverLetter().getId());
	
	int insertId = (int) database.insert(TABLE_JOB_APPLICATION, null, values);
	return getJobApplicationByJobId(insertId);
    }
    
    public JobApplication updateJobApplication(JobApplication jobApplication)
    {
	ContentValues values = new ContentValues();
	values.put(COMPANY, jobApplication.getJob().getCompany());
	values.put(TITLE, jobApplication.getJob().getTitle());
	values.put(DESCRIPTION, jobApplication.getJob().getDescription());
	values.put(STATUS, jobApplication.getStatus().toString());
	values.put(RESUME, jobApplication.getResume().getId());
	values.put(COVER_LETTER, jobApplication.getCoverLetter().getId());
	
	database.update(TABLE_JOB_APPLICATION, values, ID + " = " + jobApplication.getJob().getJobId(), null);
	return getJobApplicationByJobId(jobApplication.getJob().getJobId());
    }

    public void deleteJobApplication(JobApplication jobApplication)
    {
	int id = jobApplication.getJob().getJobId();
	database.delete(TABLE_JOB_APPLICATION, ID + " = " + id, null);
    }

    public List<JobApplication> getAllJobApplications()
    {
	List<JobApplication> jobApplications = new ArrayList<JobApplication>();

	Cursor cursor = database.query(TABLE_JOB_APPLICATION, allColumns, null, null, null,
		null, null);

	cursor.moveToFirst();
	while (!cursor.isAfterLast())
	{
	    JobApplication jobApplication = cursorToJobApplication(cursor);
	    jobApplications.add(jobApplication);
	    cursor.moveToNext();
	}
	cursor.close();
	return jobApplications;
    }

    private JobApplication cursorToJobApplication(Cursor cursor)
    {
	Job job = new Job();
	job.setJobId(cursor.getInt(0));
	job.setCompany(cursor.getString(1));
	job.setDescription(cursor.getString(2));
	
	JobApplication jobApplication = new JobApplication();
	jobApplication.setJob(job);
	jobApplication.setStatus(Status.valueOf(cursor.getString(3)));
	jobApplication.setResume(new Resume(cursor.getInt(4)));
	jobApplication.setCoverLetter(new CoverLetter(cursor.getInt(4)));
	
	return jobApplication;
    }
}