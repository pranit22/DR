package com.dr;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.dr.objects.Job;
import com.dr.objects.dao.JobDAO;

public class MainActivity extends Activity
{

    private JobDAO jobDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	jobDAO = new JobDAO(this);
	jobDAO.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
    }

    public void addJob(View v)
    {
	Job job = new Job();
	job.setCompany("Amazon");
	job.setDescription("Some Description");

	job = jobDAO.addJob(job);

//	TextView view = (TextView) findViewById(R.id.textView1);
//	view.setText(job.getJobId() + "-" + job.getCompany() + "+"
//		+ job.getDescription());

    }

}
