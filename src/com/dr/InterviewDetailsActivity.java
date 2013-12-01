package com.dr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dr.helpers.Utilities;
import com.dr.objects.Interview;
import com.dr.objects.JobApplication;
import com.dr.objects.dao.JobApplicationDAO;

public class InterviewDetailsActivity extends Activity {

    Interview interview;
    JobApplicationDAO jobApplicationDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_details);

        jobApplicationDAO = new JobApplicationDAO(this);
        jobApplicationDAO.open();

        Intent intent = getIntent();
        interview = (Interview) intent.getSerializableExtra("interview");
        ((TextView) findViewById(R.id.date)).setText(Utilities.printDate(interview.getTime()));
        ((TextView) findViewById(R.id.time)).setText(Utilities.printTime(interview.getTime()));
        ((TextView) findViewById(R.id.interviewer)).setText(interview.getInterviewer());
        ((TextView) findViewById(R.id.location)).setText(interview.getLocation());
        String reminder = Utilities.printDateTime(interview.getReminder());
        ((TextView) findViewById(R.id.reminder)).setText(reminder);
    }

    public void navigateToListInterviews(View view) {
        Intent intent = new Intent(this, (Class) getIntent().getSerializableExtra("landingActivity"));
        if (!(boolean)getIntent().getBooleanExtra("allInterviews", true)) {
            intent.putExtra("jobApplication", getJobApplicationFromInterview(interview));
        }
        startActivity(intent);
    }

    public void navigateToEditInterview(View view) {
        Intent intent = new Intent(this, EditCreateInterviewActivity.class);
        intent.putExtra("interview", interview);
        startActivity(intent);
    }


    private JobApplication getJobApplicationFromInterview(Interview interview) {
        return jobApplicationDAO.getJobApplicationByJobId(interview.getJobId());
    }
}