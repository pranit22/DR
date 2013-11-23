package com.dr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dr.objects.CoverLetter;
import com.dr.objects.Job;
import com.dr.objects.JobApplication;
import com.dr.objects.Resume;
import com.dr.objects.Status;
import com.dr.objects.dao.JobApplicationDAO;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private JobApplicationDAO jobApplicationDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jobApplicationDAO = new JobApplicationDAO(this);
        jobApplicationDAO.open();
    }

    public void addJob() {
        Job job = new Job();
        job.setCompany("Amazon");
        job.setTitle("Software Development Engineer");
        job.setDescription("Software Development Engineer, Java, Data Structures at Seattle.");

        JobApplication jobApplication = new JobApplication();
        jobApplication.setJob(job);
        jobApplication.setStatus(Status.APPLIED);
        jobApplication.setResume(new Resume(1));
        jobApplication.setCoverLetter(new CoverLetter(2));

        jobApplication = jobApplicationDAO.addJobApplication(jobApplication);

        Toast.makeText(getApplicationContext(), jobApplication.toString(), Toast.LENGTH_LONG).show();

    }

    public void navigateToListApplications(View view) {
        startActivity(new Intent(this, JobApplicationsListActivity.class));
    }



}


