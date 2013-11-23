package com.dr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dr.objects.CoverLetter;
import com.dr.objects.Job;
import com.dr.objects.JobApplication;
import com.dr.objects.Resume;
import com.dr.objects.Status;
import com.dr.objects.dao.JobApplicationDAO;

import java.util.List;

public class EditCreateJobActivity extends Activity {

    List<JobApplication> jobs;
    private JobApplicationDAO jobApplicationDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_create_job);

        jobApplicationDAO = new JobApplicationDAO(this);
        jobApplicationDAO.open();
    }

    public void submitCreateNewJob(View view) {
        Job job = new Job();
        job.setCompany(((EditText)findViewById(R.id.company)).getText().toString());
        job.setTitle(((EditText)findViewById(R.id.title)).getText().toString());
        job.setDescription(((EditText)findViewById(R.id.description)).getText().toString());

        JobApplication jobApplication = new JobApplication();
        jobApplication.setJob(job);
        jobApplication.setResume(new Resume(1));
        jobApplication.setCoverLetter(new CoverLetter(2));
        jobApplication.setStatus(Status.toStatus(((Spinner) findViewById(R.id.status)).getSelectedItem().toString()));

        jobApplication = jobApplicationDAO.addJobApplication(jobApplication);

        String message = "Job " + jobApplication.getJob().getJobId() + " saved successfully!";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, JobApplicationsListActivity.class));
    }
}
