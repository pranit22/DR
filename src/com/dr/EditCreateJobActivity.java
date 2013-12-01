package com.dr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dr.objects.CoverLetter;
import com.dr.objects.Job;
import com.dr.objects.JobApplication;
import com.dr.objects.Resume;
import com.dr.objects.Status;
import com.dr.objects.dao.JobApplicationDAO;

import java.util.List;

public class EditCreateJobActivity extends Activity {

    private JobApplicationDAO jobApplicationDAO;
    JobApplication jobApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_create_job);

        jobApplicationDAO = new JobApplicationDAO(this);
        jobApplicationDAO.open();

        Intent intent = getIntent();
        if(intent.hasExtra("jobApplication")) {
            jobApplication = (JobApplication) intent.getSerializableExtra("jobApplication");

            ((EditText)findViewById(R.id.company)).setText(jobApplication.getJob().getCompany());
            ((EditText)findViewById(R.id.title)).setText(jobApplication.getJob().getTitle());
            ((EditText)findViewById(R.id.description)).setText(jobApplication.getJob().getDescription());

            Spinner spinner = (Spinner) findViewById(R.id.status);
            ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
            int position = adapter.getPosition(jobApplication.getStatus().getValue());
            spinner.setSelection(position);

            //((EditText)findViewById(R.id.resume)).setText(Integer.toString(jobApplication.getResume().getId()));
            //((EditText)findViewById(R.id.cover_letter)).setText(Integer.toString(jobApplication.getCoverLetter().getId()));

            Button button = (Button) findViewById(R.id.submit);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jobApplication.getJob().setCompany(((EditText)findViewById(R.id.company)).getText().toString());
                    jobApplication.getJob().setTitle(((EditText)findViewById(R.id.title)).getText().toString());
                    jobApplication.getJob().setDescription(((EditText)findViewById(R.id.description)).getText().toString());
                    jobApplication.setResume(new Resume(1));
                    jobApplication.setCoverLetter(new CoverLetter(2));
                    jobApplication.setStatus(Status.toStatus(((Spinner) findViewById(R.id.status)).getSelectedItem().toString()));

                    jobApplication = jobApplicationDAO.updateJobApplication(jobApplication);
                    String message = "Job " + jobApplication.getJob().getJobId() + " updated successfully!";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), JobDetailsActivity.class);
                    intent.putExtra("jobApplication", jobApplication);
                    startActivity(intent);
                }
            });
        }
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

    public void finishActivity(View view) {
        finish();
    }
}
