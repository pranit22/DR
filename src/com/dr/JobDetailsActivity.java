package com.dr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dr.objects.JobApplication;

public class JobDetailsActivity extends Activity {

    JobApplication jobApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        
        Intent intent = getIntent();
        jobApplication = (JobApplication)intent.getSerializableExtra("jobApplication");

        ((TextView)findViewById(R.id.company)).setText(jobApplication.getJob().getCompany());
        ((TextView)findViewById(R.id.title)).setText(jobApplication.getJob().getTitle());
        ((TextView)findViewById(R.id.description)).setText(jobApplication.getJob().getDescription());
        ((TextView)findViewById(R.id.status)).setText(jobApplication.getStatus().getValue());
        ((TextView)findViewById(R.id.resume)).setText(Integer.toString(jobApplication.getResume().getId()));
        ((TextView)findViewById(R.id.cover_letter)).setText(Integer.toString(jobApplication.getCoverLetter().getId()));

    }

    public void navigateToListApplications(View view) {
        startActivity(new Intent(this, JobApplicationsListActivity.class));
    }

    public void navigateToEditApplication(View view) {
        Intent intent = new Intent(this, EditCreateJobActivity.class);
        intent.putExtra("jobApplication", jobApplication);
        startActivity(intent);
    }

    public void navigateToListInterviews(View view) {
        Intent intent = new Intent(this, InterviewsListActivity.class);
        intent.putExtra("jobId", jobApplication.getJob().getJobId());
        startActivity(intent);
    }

}
