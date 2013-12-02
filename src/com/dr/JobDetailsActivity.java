package com.dr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dr.objects.Document;
import com.dr.objects.JobApplication;
import com.dr.objects.dao.DocumentDAO;

public class JobDetailsActivity extends Activity {

    JobApplication jobApplication;
    DocumentDAO documentDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        Intent intent = getIntent();
        jobApplication = (JobApplication) intent.getSerializableExtra("jobApplication");

        documentDAO = new DocumentDAO(this);
        documentDAO.open();

        Document resume = documentDAO.getResumeById(jobApplication.getResume());
        Document coverLetter = documentDAO.getCoverLetterById(jobApplication.getCoverLetter());

        ((TextView) findViewById(R.id.company)).setText(jobApplication.getJob().getCompany());
        ((TextView) findViewById(R.id.title)).setText(jobApplication.getJob().getTitle());
        ((TextView) findViewById(R.id.description)).setText(jobApplication.getJob().getDescription());
        ((TextView) findViewById(R.id.status)).setText(jobApplication.getStatus().getValue());
        ((TextView) findViewById(R.id.resume)).setText(resume.getTitle());
        ((TextView) findViewById(R.id.cover_letter)).setText(coverLetter.getTitle());

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
        intent.putExtra("jobApplication", jobApplication);
        startActivity(intent);
    }

}
