package com.dr;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dr.objects.CoverLetter;
import com.dr.objects.Job;
import com.dr.objects.JobApplication;
import com.dr.objects.Resume;
import com.dr.objects.dao.JobApplicationDAO;

public class MainActivity extends Activity
{

    private JobApplicationDAO jobDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	jobDAO = new JobApplicationDAO(this);
	jobDAO.open();
    }

    public void addJob(View v)
    {
	Job job = new Job();
	job.setCompany("Amazon");
	job.setTitle("Software Development Engineer");
	job.setDescription("Software Development Engineer, Java, Data Structures at Seattle.");
	
	JobApplication jobApplication = new JobApplication();
	jobApplication.setJob(job);
	jobApplication.setResume(new Resume(1));
	jobApplication.setCoverLetter(new CoverLetter(2));

	jobApplication = jobDAO.addJobApplication(jobApplication);

	Toast.makeText(getApplicationContext(), jobApplication.toString(), Toast.LENGTH_LONG).show();

    }

}
